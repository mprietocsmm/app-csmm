from django.http import JsonResponse
from csmm_app.endpoints.funciones import *
import json, datetime

def cuenta(request):
    if request.method != 'GET':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)
    
    try:
        token = request.headers['token']
        tipo_usuario = request.headers['tipoUsuario']
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    usuario = busqueda_usuario_token_tipo(token, tipo_usuario) 
    return JsonResponse(
        {
            "usuario": usuario.usuario,
            "nombre": usuario.nombre + ' ' + usuario.apellido1 + ' ' + usuario.apellido2,
            #"nacimiento": usuario.nacimiento.strftime("%m/%d/%Y"),
            "nacimiento": usuario.nacimiento,
            "dni": usuario.dni,
            "accesos": usuario.accesos,
            "asociados": "Ninguno"
        }, 
        safe=False, status=200)
