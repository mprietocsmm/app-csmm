from django.http import JsonResponse
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import Comunicaciones, ComunicacionesDestinos
from csmm_app.endpoints.funciones import tipo_usuario, busqueda_usuario, hijos, busqueda_usuario_token_tipo
import json


@csrf_exempt
def comunicaciones(request):
    if request.method == 'POST':
        body = json.loads(request.body)

        try:
            tipo_remite = body['tiporemite']
            token_remitente = body['token']
            fecha = body['fecha']
            destinatario = body['destinatario']
            asunto = body['asunto']
            texto = body['texto']
        except KeyError:
            return JsonResponse({"error": "Faltan parámetros"}, status=400)

        remitente = busqueda_usuario_token_tipo(token_remitente, tipo_remite)
        comunicacion = Comunicaciones(tiporemite=tipo_remite, idremite=remitente[0].id, fecha=fecha, asunto=asunto,
                                      texto=texto)
        comunicacion.save()
        tipo_destinatario = tipo_usuario(destinatario)
        destinatario = busqueda_usuario(destinatario, tipo_destinatario)

        if int(tipo_remite) == 3:
            for hijo in hijos(remitente[0].usuario):
                print(hijo.usuario)
                comunicaciones_destino = ComunicacionesDestinos(idcomunicacion=comunicacion,
                                                                tipodestino=tipo_destinatario,
                                                                iddestino=destinatario[0].id, email=0,
                                                                idalumnoasociado=hijo, importante=0)
                comunicaciones_destino.save()

        return JsonResponse({"mensaje": "comunicacion creada"}, status=200)
    elif request.method == 'GET':
        pass
    else:
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)
