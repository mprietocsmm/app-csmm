from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import Alumnos, Familias, AlumnosFamilias, AlumnosAsignaturas, Asignaturas, Materias, Horario, Administradores, Profesores, MarcoHorario, CursosEscolares
import bcrypt, json, time

def health(requests):
    return JsonResponse({"health": "ok"}, safe=False)

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
            return JsonResponse({"message": "Hola, " + usuario[0].nombre + " " + usuario[0].apellido1 + " " + usuario[0].apellido2}, status=200)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 2:
        usuario = Alumnos.objects.filter(usuario=username)

        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            return JsonResponse({"message": "Hola, " + usuario[0].nombre + " " + usuario[0].apellido1 + " " + usuario[0].apellido2}, status=200)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 3:
        usuario = Familias.objects.filter(usuario=username)

        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            return JsonResponse({"message": "Hola, " + usuario[0].nombre + " " + usuario[0].apellido1 + " " + usuario[0].apellido2}, status=200)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 4:
        usuario = Profesores.objects.filter(usuario=username)

        if bcrypt.checkpw(password.encode('utf8'), usuario[0].password.encode('utf8')):
            return JsonResponse({"message": "Hola, " + usuario[0].nombre + " " + usuario[0].apellido1 + " " + usuario[0].apellido2}, status=200)
        else:
            return JsonResponse({"error": "Wrong password"}, status=403)
    elif usuario == 0:
        return JsonResponse({"error": "No such username in the database"}, status=404)
    

@csrf_exempt
def inicio(request):
    if request.method != 'POST':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)

    body = json.loads(request.body)
    usuario = busqueda_usuario(body['usuario'], body['tipoUsuario'])
    if body['tipoUsuario'] == 2:
        return JsonResponse({
            "nombre": usuario[0].nombre + ' ' + usuario[0].apellido1 + ' ' + usuario[0].apellido2,
            "curso": "1º BACH"
        }, safe=False)
    elif body['tipoUsuario'] == 4:
        return JsonResponse({
            "nombre": usuario[0].nombre + ' ' + usuario[0].apellido1 + ' ' + usuario[0].apellido2
        }, safe=False)



def tipo_usuario(nombre):
    if Administradores.objects.filter(usuario=nombre).count() != 0:
        return 1
    elif Alumnos.objects.filter(usuario=nombre).count() != 0:
        return 2
    elif Familias.objects.filter(usuario=nombre).count() != 0:
        return 3
    elif Profesores.objects.filter(usuario=nombre).count() != 0:
        return 4
    else:
        return 0
    

def busqueda_usuario(usuario, tipo):
    if tipo == 1:
        return Administradores.objects.filter(usuario=usuario)
    elif tipo == 2:
        return Alumnos.objects.filter(usuario=usuario)
    elif tipo == 3:
        return Familias.objects.filter(usuario=usuario)
    elif tipo == 4:
        return Profesores.objects.filter(usuario=usuario)