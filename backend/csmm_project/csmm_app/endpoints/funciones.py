from csmm_app.models import Administradores, Alumnos, Familias, Profesores


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
    

def busqueda_usuario_token(token):
    if Administradores.objects.filter(token=token).count() != 0:
        return Administradores.objects.filter(token=token)
    elif Alumnos.objects.filter(token=token).count() != 0:
        return Alumnos.objects.filter(token=token)
    elif Familias.objects.filter(token=token).count() != 0:
        return Familias.objects.filter(token=token)
    elif Profesores.objects.filter(token=token).count() != 0:
        return Profesores.objects.filter(token=token)
    
def busqueda_usuario_token_tipo(token, tipo):
    tipo = int(tipo)
    if tipo == 1:
        return Administradores.objects.filter(token=token)
    elif tipo == 2:
        return Alumnos.objects.filter(token=token)
    elif tipo == 3:
        return Familias.objects.filter(token=token)
    elif tipo == 4:
        return Profesores.objects.filter(token=token)
    
def hijos(nombre):
    padre = Familias.objects.filter(usuario=nombre)
    if padre.count() != 0:
        return Alumnos.objects.filter(alumnosfamilias__id_familia__in=padre)
    
def hijos_token(token):
    padre = Familias.objects.filter(token=token)
    if padre.count() != 0:
        return Alumnos.objects.filter(alumnosfamilias__id_familia__in=padre)

    
def busqueda_usuario_id_tipo(id, tipo):
    tipo = int(tipo)
    id = int(id)
    if tipo == 1:
        return Administradores.objects.get(id=id)
    elif tipo == 2:
        return Alumnos.objects.get(id=id)
    elif tipo == 3:
        return Familias.objects.get(id=id)
    elif tipo == 4:
        return Profesores.objects.get(id=id)