from django.http import JsonResponse
from django.utils import timezone
from django.db.models import Q
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import *
from csmm_app.endpoints.funciones import *
import json, datetime


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
            importante = body['importante']
        except KeyError as e:
            return JsonResponse({"error": e}, status=400)

        remitente = busqueda_usuario_token_tipo(token_remitente, tipo_remite)
        comunicacion = Comunicaciones(tiporemite=tipo_remite, idremite=remitente.id, fecha=fecha, asunto=asunto,
                                      texto=texto)
        comunicacion.save()

        for destinatario in destinatarios:
            tipo_destinatario = destinatario['tipoUsuario']
            destinatario = busqueda_usuario_id_tipo(destinatario['id'], destinatario['tipoUsuario'])
            if int(tipo_remite) == 3:
                for hijo in hijos(remitente.usuario):
                    comunicaciones_destino = ComunicacionesDestinos(idcomunicacion=comunicacion,
                                                                tipodestino=tipo_destinatario,
                                                                iddestino=destinatario.id, email=0,
                                                                idalumnoasociado=hijo, importante=importante)
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

        if usuario == 404:
            return JsonResponse({"error": "Usuario no encontrado"}, status=404)
        
        if modo == 'recibidas':
            comunicaciones = Comunicaciones.objects.filter(idcomunicacion__in=ComunicacionesDestinos.objects.filter(tipodestino=tipo, iddestino=usuario.id).values_list('idcomunicacion', flat=True)).order_by('-fecha')

            response = []
            for comunicacion in comunicaciones:
                remitente = busqueda_usuario_id_tipo(comunicacion.idremite, comunicacion.tiporemite)
                comunicacion_destino = ComunicacionesDestinos.objects.filter(idcomunicacion=comunicacion.idcomunicacion)[0]

                if comunicacion_destino.eliminado == None:
                    response.append(
                        {
                            "id": int(comunicacion.idcomunicacion),
                            "asunto": comunicacion.asunto,
                            "mensaje": comunicacion.texto,
                            "remitente": remitente.nombre + " " + remitente.apellido1 + " " + remitente.apellido2,
                            "fecha": comunicacion.fecha.strftime("%d/%m/%Y %H:%M"),
                            "importante": bool(comunicacion_destino.importante),
                            "leido": comunicacion_destino.leida 
                        }
                    )
           
            return JsonResponse(response, safe=False, status=200)
        
        elif modo == 'enviadas':
            comunicaciones = Comunicaciones.objects.filter(tiporemite=tipo, idremite=usuario.id)
            
            response = []
            for comunicacion in comunicaciones:
                # Para que funcione distinct() para que no aparezcan repetidos los nombre debido a que por cada alumno asociado se crea una fila, solo hace falta recuperar una
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

        elif modo == 'eliminadas':
            comunicaciones = Comunicaciones.objects.filter(idcomunicacion__in=ComunicacionesDestinos.objects.filter(tipodestino=tipo, iddestino=usuario.id).values_list('idcomunicacion', flat=True)).order_by('-fecha')

            response = []
            for comunicacion in comunicaciones:
                remitente = busqueda_usuario_id_tipo(comunicacion.idremite, comunicacion.tiporemite)
                comunicacion_destino = ComunicacionesDestinos.objects.filter(idcomunicacion=comunicacion.idcomunicacion)[0]

                if comunicacion_destino.eliminado != None:
                    response.append(
                        {
                            "id": int(comunicacion.idcomunicacion),
                            "asunto": comunicacion.asunto,
                            "mensaje": comunicacion.texto,
                            "remitente": remitente.nombre + " " + remitente.apellido1 + " " + remitente.apellido2,
                            "fecha": comunicacion.fecha.strftime("%d/%m/%Y %H:%M"),
                            "importante": bool(comunicacion_destino.importante),
                            "leido": comunicacion_destino.leida 
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
        
        # Encontramos los hijos de el padre/madre
        for hijo in hijos:
            # Encontramos las asignaturas que tiene cada hijo
            asignaturas = AlumnosAsignaturas.objects.filter(id_alumno__id=hijo.id)

            # De cada asignatura recuperamos el profesor
            for asignatura in asignaturas:
                response.append({"id": asignatura.id_asignatura.id_materia.tutor.id, "tipoUsuario": 4, "nombre": asignatura.id_asignatura.id_materia.tutor.nombre + " (tutor/ra de " + hijo.nombre + ")"})

    elif tipo == 4:
        familia = Familias.objects.all()

        for familiar in familia:
            response.append({"id": familiar.id, "nombre": familiar.nombre, "tipoUsuario": 4})
            
    return JsonResponse(response, status=200, safe=False)

@csrf_exempt
def leido(request, id_comunicacion):
    if request.method != 'POST':
        return JsonResponse({"error": "Método http no soportado"}, status=405)
    
    try:
        comunicacion_destino = ComunicacionesDestinos.objects.filter(idcomunicacion=id_comunicacion)
    except ComunicacionesDestinos.DoesNotExist as error:
        return JsonResponse({"error": str(error)}, status=404)
    
    comunicacion_destino.update(leida=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))

    return JsonResponse({}, status=200)

@csrf_exempt
def eliminado(request, id_comunicacion):
    # Eliminar comunicacion
    if request.method == 'DELETE':
        try:
            comunicacion_destino = ComunicacionesDestinos.objects.filter(idcomunicacion=id_comunicacion)
        except ComunicacionesDestinos.DoesNotExist as error:
            return JsonResponse({"error": str(error)}, status=404)
        
        comunicacion_destino.update(eliminado=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
        return JsonResponse({}, status=200)

    # Restaurar comunicacion
    elif request.method == 'PATCH':
        try:
            comunicacion_destino = ComunicacionesDestinos.objects.filter(idcomunicacion=id_comunicacion)
        except ComunicacionesDestinos.DoesNotExist as error:
            return JsonResponse({"error": str(error)}, status=404)
    
        comunicacion_destino.update(eliminado=None)

        return JsonResponse({}, status=200)
    
    else:
        return JsonResponse({"error": "Método http no soportado"}, status=405)  


@csrf_exempt
def importante(request, id_comunicacion):
    if request.method != 'POST':
        return JsonResponse({"error": "Método http no soportado"}, status=405)

    body = json.loads(request.body)
    try:
        importante = body['importante']
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    try:
        comunicacion_destino = ComunicacionesDestinos.objects.filter(idcomunicacion=id_comunicacion)
    except ComunicacionesDestinos.DoesNotExist as error:
        return JsonResponse({"error": str(error)}, status=404)
    
    if importante is True:
        comunicacion_destino.update(importante=1)
    elif importante is False:
        comunicacion_destino.update(importante=0)

    return JsonResponse({}, status=200)