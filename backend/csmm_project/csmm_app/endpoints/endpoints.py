from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from csmm_app.models import Alumnos, Familias, AlumnosFamilias, AlumnosAsignaturas, Asignaturas, Materias, Horario, Administradores, Profesores, MarcoHorario, CursosEscolares
import json
from csmm_app.endpoints.funciones import *

def health(requests):
    return JsonResponse({"health": "ok"}, safe=False)