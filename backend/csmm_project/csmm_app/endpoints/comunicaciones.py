from django.http import JsonResponse
from django.utils import timezone
from django.db.models import Q
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import Comunicaciones, ComunicacionesDestinos
from csmm_app.endpoints.funciones import *
import json


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
        comunicacion = Comunicaciones(tiporemite=tipo_remite, idremite=remitente[0].id, fecha=fecha, asunto=asunto,
                                      texto=texto)
        comunicacion.save()

        for destinatario in destinatarios:
            tipo_destinatario = destinatario['tipoUsuario']
            destinatario = busqueda_usuario_id_tipo(destinatario['id'], destinatario['tipoUsuario'])
            if int(tipo_remite) == 3:
                for hijo in hijos(remitente[0].usuario):
                    comunicaciones_destino = ComunicacionesDestinos(idcomunicacion=comunicacion,
                                                                tipodestino=tipo_destinatario,
                                                                iddestino=destinatario.id, email=0,
                                                                idalumnoasociado=hijo, importante=0)
                    comunicaciones_destino.save()
            if int(tipo_remite) == 4:
                pass

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