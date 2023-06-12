from django.http import JsonResponse
from django.db import connection
from csmm_app.models import Alumnos, AlumnosAsignaturas, Materias, Asignaturas, Vistas
from csmm_app.endpoints.funciones import *

def asignaturas(request):
    if request.method != 'GET':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)
    

    try:
        token = request.headers['token']
        tipo_usuario = int(request.headers['tipoUsuario'])
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    if tipo_usuario != 2:
        return JsonResponse({"error": "No autorizado"}, status=401)
    

    alumno = Alumnos.objects.get(token=token)
    
    # asignaturas = Materias.objects.filter(id=Asignaturas.objects.get(id=AlumnosAsignaturas.objects.get()))
    # asignaturas = Materias.objects.raw('select `csmm_gestor`.`alumnos_asignaturas`.`id` AS `id`,`csmm_gestor`.`alumnos_asignaturas`.`id_alumno` AS `id_alumno`,`csmm_gestor`.`alumnos_asignaturas`.`id_asignatura` AS `id_asignatura`,`csmm_gestor`.`alumnos_asignaturas`.`curso_escolar` AS `curso_escolar`,`csmm_gestor`.`profesor_asignaturas`.`id_profesor` AS `id_profesor` from (`csmm_gestor`.`alumnos_asignaturas` join `csmm_gestor`.`profesor_asignaturas` on (`csmm_gestor`.`alumnos_asignaturas`.`id_asignatura` = `csmm_gestor`.`profesor_asignaturas`.`id_asignatura` and `csmm_gestor`.`alumnos_asignaturas`.`curso_escolar` = `csmm_gestor`.`profesor_asignaturas`.`curso_escolar`)) where alumnos_asignaturas.id_alumno='+str(alumno.id))
    
    with connection.cursor() as cursor:
        cursor.execute(str(Vistas.objects.get(id=0).consulta)+"where alumnos_asignaturas.id_alumno =" + str(alumno.id) + " AND alumnos_asignaturas.curso_escolar=15") 
        query = fetchallasdict(cursor)

        lista = []
        for row in query:
            lista.append(
                {
                    "materia":row['materia'],
                    "tutor": row['tutor']
                }
            )
    print(lista)
    return JsonResponse(lista, safe=False)
