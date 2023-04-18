from django.http import JsonResponse
from csmm_app.models import Alumnos, Familias, Administradores, Profesores
from django.views.decorators.csrf import csrf_exempt
import json

@csrf_exempt
def authenticate(request):
    body = json.loads(request.body)
    token = body['token']
    if tipo_usuario(token) != 0:
        return JsonResponse({"mensaje": "Usuario autenticado"}, safe=False)
    else:
        return JsonResponse({"error": "No se ha podido autenticar el usuario"}, status=401)


def tipo_usuario(token):
    if Administradores.objects.filter(token=token).count() != 0:
        return 1
    elif Alumnos.objects.filter(token=token).count() != 0:
        return 2
    elif Familias.objects.filter(token=token).count() != 0:
        return 3
    elif Profesores.objects.filter(token=token).count() != 0:
        return 4
    else:
        return 0