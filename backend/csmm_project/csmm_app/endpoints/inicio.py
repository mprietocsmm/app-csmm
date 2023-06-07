from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import *
from csmm_app.endpoints.funciones import *
import json

@csrf_exempt
def inicio(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)

    body = json.loads(request.body)
    usuario = busqueda_usuario_token(body['token'])
    if body['tipoUsuario'] == '1':
        return JsonResponse({
            "nombre": usuario[0].nombre + ' ' + usuario[0].apellido1 + ' ' + usuario[0].apellido2,
        }, status=200)
    elif body['tipoUsuario'] == '2':
        return JsonResponse({
            "nombre": usuario[0].nombre + ' ' + usuario[0].apellido1 + ' ' + usuario[0].apellido2,
        }, status=200)
    elif body['tipoUsuario'] == '3':
        return JsonResponse({
            "nombre": usuario[0].nombre + ' ' + usuario[0].apellido1 + ' ' + usuario[0].apellido2,
        }, status=200)
    elif body['tipoUsuario'] == '4':
        return JsonResponse({
            "nombre": usuario[0].nombre + ' ' + usuario[0].apellido1 + ' ' + usuario[0].apellido2
        }, status=200)
    