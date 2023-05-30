from django.http import JsonResponse
from django.utils import timezone
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import *
from csmm_app.endpoints.funciones import *
import json

@csrf_exempt
def perfil(request):
    if request.method == 'GET':
        try:
            token = request.headers['token']
            tipo = request.headers['tipoUsuario']
        except KeyError:
            return JsonResponse({"Error": "Faltan parámetros"}, status=400)

        if int(tipo) == 3:
            usuario = Familias.objects.filter(token=token)
            ajustes = FamiliasAppAjustes.objects.get(id_usuario__id=usuario[0].id)
            return JsonResponse(
                {
                    "autentifiacion_dos_fases": bool(ajustes.autentificacion_dos_fases),
                    "proteccion_restablecimiento": bool(ajustes.proteccion_restablecimiento),
                    "not_calificaciones_push": bool(ajustes.not_calificaciones_push),
                    "not_comunicaciones_push": bool(ajustes.not_comunicaciones_push),
                    "not_entrevistas_push": bool(ajustes.not_entrevistas_push),
                    "not_extraescolares_push": bool(ajustes.not_extraescolares_push),
                    "not_enfermeria_push": bool(ajustes.not_enfermeria_push),
                    "not_calificaciones_email": bool(ajustes.not_calificaciones_email),
                    "not_comunicaciones_email": bool(ajustes.not_comunicaciones_email),
                    "not_entrevistas_email": bool(ajustes.not_entrevistas_email),
                    "not_extraescolares_email": bool(ajustes.not_extraescolares_email),
                    "not_enfermeria_email": bool(ajustes.not_enfermeria_email)
                },
                status=200
            )
        
        return JsonResponse({}, status=200)
    
    elif request.method == 'POST':
        body = json.loads(request.body)

        try:
            token = request.headers['token']
            tipo = request.headers['tipoUsuario']
        except KeyError:
            return JsonResponse({"error": "Faltan parámetros"}, status=400)
        
        ajustes = FamiliasAppAjustes.objects.filter(id_usuario__id=busqueda_usuario_token_tipo(token, tipo)[0].id).update(
            autentificacion_dos_fases=boolean_to_int(body['autentificacion_dos_fases']),
            proteccion_restablecimiento = boolean_to_int(body['proteccion_restablecimiento']),
            not_comunicaciones_push = boolean_to_int(body['not_comunicaciones_push']),
            not_calificaciones_push = boolean_to_int(body['not_calificaciones_push']),
            not_entrevistas_push = boolean_to_int(body['not_entrevistas_push']),
            not_extraescolares_push = boolean_to_int(body['not_extraescolares_push']),
            not_enfermeria_push = boolean_to_int(body['not_enfermeria_push']),
            not_comunicaciones_email = boolean_to_int(body['not_comunicaciones_email']),
            not_calificaciones_email = boolean_to_int(body['not_calificaciones_email']),
            not_entrevistas_email = boolean_to_int(body['not_entrevistas_email']),
            not_extraescolares_email = boolean_to_int(body['not_extraescolares_email']),
            not_enfermeria_email = boolean_to_int(body['not_enfermeria_email'])
        )
        
        return JsonResponse({"mensaje": "Guardado"}, status=200)