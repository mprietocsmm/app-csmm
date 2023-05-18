from django.http import JsonResponse
from django.utils import timezone
from django.db.models import Q
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import Comunicaciones, ComunicacionesDestinos, TokenFcm
from csmm_app.endpoints.funciones import *
import config
import json
import datetime
import firebase_admin
from firebase_admin import credentials, messaging

firebase_cred = credentials.Certificate(config.SERVICE_ACCOUNT_KEY)
firebase_app = firebase_admin.initialize_app(firebase_cred)

def send_token_push(title, body, token_destinatarios, data):
    message = messaging.MulticastMessage(
        android=messaging.AndroidConfig(
            ttl=datetime.timedelta(seconds=3600),
            priority='high',
            data=data,
            notification=messaging.AndroidNotification(
                title=title,
                body=body,
                #click_action='login',
            ),
        ),
        tokens=token_destinatarios,
    
    )

    response = messaging.send_multicast(message)

    if response.failure_count > 0:
        responses = response.responses
        failed_tokens = []
        for idx, resp in enumerate(responses):
            if not resp.success:
                # The order of responses corresponds to the order of the registration tokens.
                failed_tokens.append(token_destinatarios[idx])
        print('List of tokens that caused failures: {0}'.format(failed_tokens))

def send_all(request):
    tokens = TokenFcm.objects.all()

    tokens_list = []
    for token in tokens:
        tokens_list.append(token.token)

    message = messaging.MulticastMessage(
        android=messaging.AndroidConfig(
            ttl=datetime.timedelta(seconds=3600),
            priority='high',
            notification=messaging.AndroidNotification(
                title='Titulo de la notificacion',
                body='Cuerpo de la notificacion',
                #click_action='login',
                priority='max',
                channel_id='Comunicaciones'
            ),
            data={
                "mensaje": "hoola"
            },
        ),
        tokens=tokens_list,
    )

    response = messaging.send_multicast(message)
    if response.failure_count > 0:
        responses = response.responses
        failed_tokens = []
        for idx, resp in enumerate(responses):
            if not resp.success:
                failed_tokens.append(tokens_list[idx])
        print('List of tokens that caused failures: {0}'.format(failed_tokens))

        for token in failed_tokens:
            TokenFcm.objects.get(token=token).delete()
    return JsonResponse({"mensaje": "ok"}, safe=False, status=200)

@csrf_exempt
def comunicaciones(request, modo):
    if request.method == 'POST':
        body = json.loads(request.body)
        
        try:
            tipo_remite = body['tipoRemite']
            token_remitente = body['token']
            fecha = body['fecha']
            destinatarios = body['destinatarios']
            asunto = body['asunto']
            texto = body['mensaje']
        except KeyError as e:
            return JsonResponse({"error": e}, status=400)

        remitente = busqueda_usuario_token_tipo(token_remitente, tipo_remite)
        comunicacion = Comunicaciones(tiporemite=tipo_remite, idremite=remitente.id, fecha=fecha, asunto=asunto,
                                      texto=texto)
        comunicacion.save()

        token_destinatarios = []
        for destinatario in destinatarios:
            tipo_destinatario = destinatario['tipoUsuario']
            destinatario = busqueda_usuario_id_tipo(destinatario['id'], tipo_destinatario)
            token_destinatarios.append(TokenFcm.objects.get(id_usuario=destinatario.id, tipo=tipo_destinatario).values_list('token', flat=True))

            if int(tipo_remite) == 3:
                for hijo in hijos(remitente.usuario):
                    comunicaciones_destino = ComunicacionesDestinos(idcomunicacion=comunicacion,
                                                                tipodestino=tipo_destinatario,
                                                                iddestino=destinatario.id, email=0,
                                                                idalumnoasociado=hijo, importante=0)
                    comunicaciones_destino.save()
            if int(tipo_remite) == 4:
                pass
        
        #send_token_push(body['asunto'], body['mensaje'], token_destinatarios, {"id_comunicacion": str(comunicaciones_destino.idcomunicaciondestino)})
        send_token_push(body['asunto'], body['mensaje'], token_destinatarios, {"asunto": comunicacion.asunto,"mensaje": comunicacion.texto, "remitente": destinatario.nombre})
        return JsonResponse({"mensaje": "comunicacion creada"}, status=200)
    elif request.method == 'GET':
        try:
            token = request.headers['token']
            tipo = request.headers['tipoUsuario']
        except KeyError:
            return JsonResponse({"error": "Faltan parámetros"}, status=400)

        usuario = busqueda_usuario_token_tipo(token, tipo)

        if modo == 'recibidas':
            comunicaciones = Comunicaciones.objects.filter(idcomunicacion__in=ComunicacionesDestinos.objects.filter(tipodestino=tipo, iddestino=usuario.id).values_list('idcomunicacion', flat=True)).order_by('-fecha')

            response = []
            for comunicacion in comunicaciones:
                remitente = busqueda_usuario_id_tipo(comunicacion.idremite, comunicacion.tiporemite)
                response.append(
                    {   
                        "id": comunicacion.idcomunicacion,
                        "asunto": comunicacion.asunto,
                        "mensaje": comunicacion.texto,
                        "remitente": remitente.nombre + " " + remitente.apellido1 + " " + remitente.apellido2
                    }
                )
            return JsonResponse(response, safe=False, status=200)
        
        elif modo == 'enviadas':
            comunicaciones = Comunicaciones.objects.filter(tiporemite=tipo, idremite=usuario.id)
            
            response = []
            for comunicacion in comunicaciones:
                # Para que funcione distinct() para que no aparezcan repeditos los nombre debido a que por cada alumno asociado se crea una fila, solo hace falta recuperar una
                # Con el values y el distinct funciona
                destinatarios = ComunicacionesDestinos.objects.filter(idcomunicacion__idcomunicacion=comunicacion.idcomunicacion).values('idcomunicacion', 'iddestino', 'tipodestino').distinct()
                destinatarios_list = []

                for destinatario in destinatarios:
                    destinatario = busqueda_usuario_id_tipo(destinatario['iddestino'], destinatario['tipodestino'])
                    destinatarios_list.append(
                        {
                            "nombre": destinatario.nombre
                        }
                    ) 

                response.append(
                    {
                        "asunto": comunicacion.asunto,
                        "mensaje": comunicacion.texto,
                        "destinatarios": destinatarios_list 
                    }
                )

            return JsonResponse(response, safe=False, status=200)
    
    else:
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)

def get_contactos(request):

    try:
        token = request.headers['token']    
        tipo = int(request.headers['tipoUsuario'])
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    response = []
    #response = ['PAS', 'Equipo informático', 'Dirección']

    if tipo == 1:
        pass

    elif tipo == 2:
        pass
    
    elif tipo == 3:
        hijos = hijos_token(token)
        destinatarios = Profesores.objects.filter(Q(id=1) | Q(id=2))
        '''
        for hijo in hijos:
            response.append({"id": hijo.id, "nombre": hijo.nombre, "tipoUsuario": 3})
        '''
        for destinatario in destinatarios:
            response.append({"id":destinatario.id, "nombre": destinatario.nombre, "tipoUsuario": 4})
    elif tipo == 4:
        familia = Familias.objects.all()

        for familiar in familia:
            response.append({"id": familiar.id, "nombre": familiar.nombre, "tipoUsuario": 4})
            
    return JsonResponse(response, status=200, safe=False)