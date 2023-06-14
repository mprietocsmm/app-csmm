from django.http import JsonResponse
from csmm_app.models import Llavero, Alumnos

def llavero(request):
    if request.method != 'GET':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)
    
    try:
        token = request.headers['token']
        tipo_usuario = int(request.headers['tipoUsuario'])
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    
    if tipo_usuario != 2:
        return JsonResponse({"error": "No tienes acceso"}, status=401)
    
    alumno = Alumnos.objects.get(token=token)
    response = Llavero.objects.filter(id_alumno=alumno.id)

    lista = []
    for clave in response:
        lista.append(
            {
                "id": int(clave.id),
                "aplicacion": clave.aplicacion,
                "usuario": clave.usuario,
                "email": clave.email,
                "contraseña": clave.contraseña,
                "modificable": bool(clave.modificable)
            }
        )

    return JsonResponse(lista, safe=False, status=200)