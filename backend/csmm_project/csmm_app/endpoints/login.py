from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import *
import json, secrets, bcrypt, datetime
from csmm_app.endpoints.funciones import *

@csrf_exempt
def login(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Method not supported"}, status=405) 
    '''
    username = requests.GET.get('email')
    if username is None:
        return JsonResponse({"error": "username missing"}, status=400)

    password = requests.GET.get('contraseña')
    if password is None:
        return JsonResponse({"error": "password missing"}, status=400)
    
    '''
    body = json.loads(request.body)

    username = body['email']
    password = body['contraseña']
    usuario = tipo_usuario(username)

    if usuario == 1:
        usuario = Administradores.objects.filter(usuario=username)
        
        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            usuario.update(token=secrets.token_urlsafe(20), ultimo=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), navegador='App', accesos=usuario[0].accesos+1)
            return JsonResponse({"token": usuario[0].token, "tipoUsuario": 1}, safe=False)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 2:
        usuario = Alumnos.objects.filter(usuario=username)

        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            usuario.update(token=secrets.token_urlsafe(20), ultimo=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), navegador='App', accesos=usuario[0].accesos+1)
            return JsonResponse({"token": usuario[0].token, "tipoUsuario": 2}, safe=False)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 3:
        usuario = Familias.objects.filter(usuario=username)

        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            usuario.update(token=secrets.token_urlsafe(20), ultimo=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), navegador='App', accesos=usuario[0].accesos+1)
            return JsonResponse({"token": usuario[0].token, "tipoUsuario": 3}, safe=False)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 4:
        usuario = Profesores.objects.filter(usuario=username)

        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            usuario.update(token=secrets.token_urlsafe(20), ultimo=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), navegador='App', accesos=usuario[0].accesos+1)
            return JsonResponse({"token": usuario[0].token, "tipoUsuario": 4}, safe=False)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 0:
        return JsonResponse({"error": "No such username in the database"}, status=404) 