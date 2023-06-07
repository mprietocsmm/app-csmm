from django.http import JsonResponse
from csmm_app.models import *
import datetime
from csmm_app.endpoints.funciones import *

def listado_alumnos(request):
    if request.method != 'GET':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)
    
    try:
        token = request.headers['token']
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    if (tipo_usuario_token(token) != 1):
        return JsonResponse({"error": "No autorizado"}, status=401)
    
    alumnos = Alumnos.objects.all().order_by('id')

    lista_alumnos = []
    for alumno in alumnos:
        grupo = Grupos.objects.get(id=AlumnosAsignaturas.objects.filter(id_alumno=alumno.id)[0].id_asignatura.id_grupo.id)
        lista_alumnos.append(
            {
                "id": alumno.id, 
                "usuario": alumno.usuario,
                "nombre": alumno.nombre,
                "apellido1": alumno.apellido1,
                "apellido2": alumno.apellido2,
                "grupo": grupo.grupo,
                "dni": alumno.dni,
                "nacimiento": alumno.nacimiento.strftime("%Y/%m/%d"),
                "ultimo_acceso": alumno.ultimo,
                "accesos": int(alumno.accesos)
            }
        )
    
    return JsonResponse(lista_alumnos, safe=False, status=200)