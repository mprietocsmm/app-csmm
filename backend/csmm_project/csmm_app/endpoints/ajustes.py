from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import *
from csmm_app.endpoints.funciones import *
import json

@csrf_exempt
def ajustes(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Método http no soportado"}, status=405)

    body = json.loads(request.body)

    try:
        token = request.headers['token']
        tipo = request.headers['tipoUsuario']
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    ajustes = FamiliasAppAjustes.objects.get(id_usuario__id=busqueda_usuario_token_tipo(token, tipo)[0].id)
    ajustes.autentificacion_dos_fases = int(body['autentificacion_dos_fases'] == True)   
    ajustes.proteccion_restablecimiento = int(body['proteccion_restablecimiento'] == True)
    ajustes.not_comunicaciones_push = int(body['not_comunicaciones_push'] == True)
    ajustes.not_calificaciones_push = int(body['not_calificaciones_push'] == True)
    ajustes.not_entrevistas_push = int(body['not_entrevistas_push'] == True)
    ajustes.not_extraescolares_push = int(body['not_extraescolares_push'] == True)
    ajustes.not_enfermeria_push = int(body['not_enfermeria_push'] == True)
    ajustes.not_comunicaciones_email = int(body['not_comunicaciones_email'] == True)
    ajustes.not_calificaciones_email = int(body['not_calificaciones_email'] == True)
    ajustes.not_entrevistas_email = int(body['not_entrevistas_email'] == True)
    ajustes.not_extraescolares_email = int(body['not_extraescolares_email'] == True)
    ajustes.not_enfermeria_email = int(body['not_enfermeria_email'] == True)
    ajustes.save()

    return JsonResponse({}, status=200)