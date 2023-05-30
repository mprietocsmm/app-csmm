# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class Administradores(models.Model):
    id = models.IntegerField(primary_key=True)
    usuario = models.TextField()
    password = models.TextField()
    nombre = models.TextField()
    apellido1 = models.TextField()
    apellido2 = models.TextField()
    nacimiento = models.DateField(blank=True, null=True)
    dni = models.TextField()
    oa = models.TextField()
    ultimo = models.DateTimeField(blank=True, null=True)
    ip = models.TextField()
    navegador = models.TextField()
    accesos = models.IntegerField()
    token = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'administradores'


class Alumnos(models.Model):
    id = models.IntegerField(primary_key=True)
    usuario = models.TextField()
    password = models.TextField()
    nombre = models.TextField()
    apellido1 = models.TextField()
    apellido2 = models.TextField()
    nacimiento = models.DateField(blank=True, null=True)
    dni = models.TextField()
    oa = models.TextField()
    ultimo = models.DateTimeField(blank=True, null=True)
    ip = models.TextField()
    navegador = models.TextField()
    accesos = models.IntegerField()
    token = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'alumnos'

class Grupos(models.Model):
    id = models.IntegerField(primary_key=True)
    grupo = models.CharField(max_length=250)

    class Meta:
        managed = False
        db_table = 'grupos'


class Materias(models.Model):
    id = models.IntegerField(primary_key=True)
    materia = models.CharField(max_length=250)
    tutor = models.CharField(max_length=250)

    class Meta:
        managed = False
        db_table = 'materias'


class Asignaturas(models.Model):
    id = models.IntegerField(primary_key=True)
    id_grupo = models.ForeignKey('Grupos', models.DO_NOTHING, db_column='id_grupo')
    id_materia = models.ForeignKey('Materias', models.DO_NOTHING, db_column='id_materia')
    cod_asignatura = models.TextField()

    class Meta:
        managed = False
        db_table = 'asignaturas'


class CursosEscolares(models.Model):
    id = models.IntegerField(primary_key=True)
    curso_escolar = models.CharField(max_length=250)

    class Meta:
        managed = False
        db_table = 'cursos_escolares'


class AlumnosAsignaturas(models.Model):
    id = models.IntegerField(primary_key=True)
    id_alumno = models.ForeignKey(Alumnos, models.DO_NOTHING, db_column='id_alumno')
    id_asignatura = models.ForeignKey('Asignaturas', models.DO_NOTHING, db_column='id_asignatura')
    curso_escolar = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'alumnos_asignaturas'


class Familias(models.Model):
    id = models.IntegerField(primary_key=True)
    usuario = models.TextField()
    password = models.TextField()
    nombre = models.TextField()
    apellido1 = models.TextField()
    apellido2 = models.TextField()
    nacimiento = models.DateField(blank=True, null=True)
    dni = models.TextField()
    oa = models.TextField()
    ultimo = models.DateTimeField(blank=True, null=True)
    ip = models.TextField()
    navegador = models.TextField()
    accesos = models.IntegerField()
    fcm_token = models.TextField(blank=True, null=True)
    token = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'familias'

    
class AlumnosFamilias(models.Model):
    id = models.IntegerField(primary_key=True)
    id_alumno = models.ForeignKey(Alumnos, models.DO_NOTHING, db_column='id_alumno')
    id_familia = models.ForeignKey('Familias', models.DO_NOTHING, db_column='id_familia')
    tipo_relacion = models.CharField(max_length=250)

    class Meta:
        managed = False
        db_table = 'alumnos_familias'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=150)

    class Meta:
        managed = False
        db_table = 'auth_group'

class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)

    
class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthGroupPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)



class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=150)
    first_name = models.CharField(max_length=150)
    last_name = models.CharField(max_length=150)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    id = models.BigAutoField(primary_key=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class Comunicaciones(models.Model):
    idcomunicacion = models.AutoField(primary_key=True)
    tiporemite = models.IntegerField()
    idremite = models.IntegerField()
    fecha = models.DateTimeField(blank=True, null=True)
    asunto = models.TextField()
    texto = models.TextField()

    class Meta:
        managed = False
        db_table = 'comunicaciones'


class ComunicacionesAdjuntos(models.Model):
    idcomunicacionadjunto = models.AutoField(primary_key=True)
    idcomunicacion = models.ForeignKey(Comunicaciones, models.DO_NOTHING, db_column='idcomunicacion')
    adjunto = models.TextField()

    class Meta:
        managed = False
        db_table = 'comunicaciones_adjuntos'


class ComunicacionesDestinos(models.Model):
    idcomunicaciondestino = models.AutoField(primary_key=True)
    idcomunicacion = models.ForeignKey(Comunicaciones, models.DO_NOTHING, db_column='idcomunicacion')
    tipodestino = models.IntegerField()
    iddestino = models.IntegerField()
    leida = models.DateTimeField(blank=True, null=True)
    eliminado = models.DateTimeField(blank=True, null=True)
    email = models.IntegerField()
    idalumnoasociado = models.ForeignKey(Alumnos, models.DO_NOTHING, db_column='idAlumnoAsociado', blank=True, null=True)  # Field name made lowercase.
    importante = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'comunicaciones_destinos'


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.PositiveSmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'



class DjangoMigrations(models.Model):
    id = models.BigAutoField(primary_key=True)
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'


class Documentos(models.Model):
    id_documento = models.AutoField(primary_key=True)
    id_grupo = models.IntegerField()
    documento = models.TextField()
    enlace = models.TextField()
    categoria = models.TextField()
    fecha = models.DateField()

    class Meta:
        managed = False
        db_table = 'documentos'


class DocumentosAlumno(models.Model):
    id_documento = models.AutoField(primary_key=True)
    id_alumno = models.ForeignKey(Alumnos, models.DO_NOTHING, db_column='id_alumno')
    documento = models.TextField()
    enlace = models.TextField()
    categoria = models.TextField()
    fecha = models.DateField()
    token = models.TextField()
    curso_documento = models.TextField()
    curso_alumno = models.TextField()
    ip = models.TextField(blank=True, null=True)
    descarga_id = models.IntegerField()
    descarga_fecha = models.DateTimeField(blank=True, null=True)
    estado = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'documentos_alumno'



class FamiliasAppAjustes(models.Model):
    id = models.AutoField(primary_key=True)
    id_usuario = models.ForeignKey(Familias, models.DO_NOTHING, db_column='id_usuario')
    autentificacion_dos_fases = models.IntegerField()
    proteccion_restablecimiento = models.IntegerField()
    not_comunicaciones_push = models.IntegerField()
    not_calificaciones_push = models.IntegerField()
    not_entrevistas_push = models.IntegerField()
    not_extraescolares_push = models.IntegerField()
    not_enfermeria_push = models.IntegerField()
    not_comunicaciones_email = models.IntegerField()
    not_calificaciones_email = models.IntegerField()
    not_entrevistas_email = models.IntegerField()
    not_extraescolares_email = models.IntegerField()
    not_enfermeria_email = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'familias_app_ajustes'





class Horario(models.Model):
    id_horario = models.AutoField(primary_key=True)
    cursoescolar = models.ForeignKey(CursosEscolares, models.DO_NOTHING, db_column='cursoescolar')
    diasemana = models.IntegerField()
    modulo = models.IntegerField()
    id_asignatura = models.ForeignKey(Asignaturas, models.DO_NOTHING, db_column='id_asignatura')
    id_profesor = models.ForeignKey('Profesores', models.DO_NOTHING, db_column='id_profesor')

    class Meta:
        managed = False
        db_table = 'horario'


class Llavero(models.Model):
    id = models.IntegerField(primary_key=True)
    id_alumno = models.ForeignKey(Alumnos, models.DO_NOTHING, db_column='id_alumno')
    aplicacion = models.TextField()
    usuario = models.TextField()
    email = models.TextField()
    contraseña = models.TextField()
    modificable = models.IntegerField()

    class Meta:
        managed = False
        db_table = 'llavero'


class MarcoHorario(models.Model):
    id_horario = models.AutoField(primary_key=True)
    modulo = models.IntegerField()
    cursoescolar = models.ForeignKey(CursosEscolares, models.DO_NOTHING, db_column='cursoescolar')
    diasemana = models.IntegerField()
    inicio = models.TimeField()
    fin = models.TimeField()

    class Meta:
        managed = False
        db_table = 'marco_horario'


class Profesores(models.Model):
    id = models.IntegerField(primary_key=True)
    usuario = models.TextField()
    password = models.TextField()
    nombre = models.TextField()
    apellido1 = models.TextField()
    apellido2 = models.TextField()
    nacimiento = models.DateField()
    dni = models.TextField()
    oa = models.TextField()
    ultimo = models.DateTimeField(blank=True, null=True)
    ip = models.TextField()
    navegador = models.TextField()
    accesos = models.IntegerField()
    token = models.CharField(max_length=20, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'profesores'

    
class ProfesorAsignaturas(models.Model):
    id = models.IntegerField(primary_key=True)
    id_profesor = models.ForeignKey('Profesores', models.DO_NOTHING, db_column='id_profesor')
    id_asignatura = models.ForeignKey(Asignaturas, models.DO_NOTHING, db_column='id_asignatura')
    curso_escolar = models.ForeignKey(CursosEscolares, models.DO_NOTHING, db_column='curso_escolar')

    class Meta:
        managed = False
        db_table = 'profesor_asignaturas'



