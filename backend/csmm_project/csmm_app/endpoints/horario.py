from django.http import JsonResponse
from django.db import connection
from csmm_app.models import Horario, MarcoHorario, Materias
from csmm_app.endpoints.funciones import *

dias_semana = {
    1: "Lunes",
    2: "Martes",
    3: "Miércoles",
    4: "Jueves",
    5: "Viernes",
    6: "Sábado",
    7: "Domingo"
}

def horario(request):
    if request.method != 'GET':
        return JsonResponse({"error": "Método HTTP no soportado"}, status=405)
    
    try:
        tipo_usuario = request.headers['tipoUsuario']
        token_usuario = request.headers['token']
    except KeyError:
        return JsonResponse({"error": "Faltan parámetros"}, status=400)
    
    usuario = busqueda_usuario_token_tipo(token_usuario, tipo_usuario)

    if int(tipo_usuario) == 4:
        with connection.cursor() as cursor:
            cursor.execute('select `csmm_gestor`.`horario`.`id_horario` AS `id_horario`,`csmm_gestor`.`marco_horario`.`diasemana` AS `diasemana`,`csmm_gestor`.`marco_horario`.`modulo` AS `modulo`,`csmm_gestor`.`marco_horario`.`inicio` AS `inicio`,`csmm_gestor`.`marco_horario`.`fin` AS `fin`,`csmm_gestor`.`profesores`.`id` AS `id_profesor`,`csmm_gestor`.`profesores`.`usuario` AS `usuario`,`csmm_gestor`.`profesores`.`nombre` AS `nombre`,`csmm_gestor`.`profesores`.`apellido1` AS `apellido1`,`csmm_gestor`.`profesores`.`apellido2` AS `apellido2`,`asignaturas_materias_grupos`.`id` AS `id_asignatura`,`asignaturas_materias_grupos`.`materia` AS `materia`,`asignaturas_materias_grupos`.`grupo` AS `grupo`,`asignaturas_materias_grupos`.`cod_asignatura` AS `cod_asignatura`,`csmm_gestor`.`cursos_escolares`.`curso_escolar` AS `curso_escolar` from ((((`csmm_gestor`.`horario` join `csmm_gestor`.`cursos_escolares` on(`csmm_gestor`.`horario`.`cursoescolar` = `csmm_gestor`.`cursos_escolares`.`id`)) join `csmm_gestor`.`asignaturas_materias_grupos` on(`csmm_gestor`.`horario`.`id_asignatura` = `asignaturas_materias_grupos`.`id`)) join `csmm_gestor`.`profesores` on(`csmm_gestor`.`horario`.`id_profesor` = `csmm_gestor`.`profesores`.`id`)) join `csmm_gestor`.`marco_horario` on(`csmm_gestor`.`horario`.`id_horario` = `csmm_gestor`.`marco_horario`.`id_horario`)) WHERE `id_profesor`=' + str(usuario.id))
            query = fetchallasdict(cursor)
        

        lista = []
        for row in query:
            print(row)
            lista.append(
                {
                    "dia": row['diasemana'],
                    "inicio": row['inicio'],
                    "fin": row['fin'],
                    "materia": row['materia'],
                    "profesor": usuario.nombre
                }
            )
        return JsonResponse(lista, status=200, safe=False)
    
    if int(tipo_usuario) == 2:
        with connection.cursor() as cursor:
            cursor.execute('select `csmm_gestor`.`horario`.`id_horario` AS `id_horario`,`csmm_gestor`.`marco_horario`.`diasemana` AS `diasemana`,`csmm_gestor`.`marco_horario`.`modulo` AS `modulo`,`csmm_gestor`.`marco_horario`.`inicio` AS `inicio`,`csmm_gestor`.`marco_horario`.`fin` AS `fin`,`csmm_gestor`.`profesores`.`id` AS `id_profesor`,`csmm_gestor`.`profesores`.`usuario` AS `usuario`,`csmm_gestor`.`profesores`.`nombre` AS `nombre`,`csmm_gestor`.`profesores`.`apellido1` AS `apellido1`,`csmm_gestor`.`profesores`.`apellido2` AS `apellido2`,`asignaturas_materias_grupos`.`id` AS `id_asignatura`,`asignaturas_materias_grupos`.`materia` AS `materia`,`asignaturas_materias_grupos`.`grupo` AS `grupo`,`asignaturas_materias_grupos`.`cod_asignatura` AS `cod_asignatura`,`csmm_gestor`.`cursos_escolares`.`curso_escolar` AS `curso_escolar` from ((((`csmm_gestor`.`horario` join `csmm_gestor`.`cursos_escolares` on(`csmm_gestor`.`horario`.`cursoescolar` = `csmm_gestor`.`cursos_escolares`.`id`)) join `csmm_gestor`.`asignaturas_materias_grupos` on(`csmm_gestor`.`horario`.`id_asignatura` = `asignaturas_materias_grupos`.`id`)) join `csmm_gestor`.`profesores` on(`csmm_gestor`.`horario`.`id_profesor` = `csmm_gestor`.`profesores`.`id`)) join `csmm_gestor`.`marco_horario` on(`csmm_gestor`.`horario`.`id_horario` = `csmm_gestor`.`marco_horario`.`id_horario`)) WHERE `id_profesor`=' + str(usuario.id))
            query = fetchallasdict(cursor)
        '''
        horario = Horario.objects.filter(id_profesor=usuario.id).order_by('diasemana', 'modulo')

        lista_horario = []
        for clase in horario:
            marco_horario = MarcoHorario.objects.get(diasemana=clase.diasemana, modulo=clase.modulo)
            materia = Materias.objects.get(id__cod_asignatura)
            clase_json = {
                "diasemana": clase.diasemana,
                "inicio": marco_horario.inicio,
                "fin": marco_horario.fin,
                "materia":,
                "curso":,
            }
            lista_horario.append(clase_json)
        '''

def fetchallasdict(cursor):
    # all rows from the cursor are returned in the form of dict
    columns = [col[0] for col in cursor.description]
    # returning dictionary
    return [
        dict(zip(columns, row))
        for row in cursor.fetchall()
    ]