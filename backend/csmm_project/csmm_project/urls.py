"""csmm_project URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from csmm_app.endpoints import endpoints, comunicaciones, launcher, perfil, login, inicio, ajustes, llavero

urlpatterns = [
    path('admin/', admin.site.urls),
    path('health', endpoints.health),
    path('login', login.login),
    path('inicio', inicio.inicio),
    path('autenticar', launcher.authenticate),
    path('perfil', perfil.perfil),
    path('ajustes/cuenta', ajustes.cuenta),
    path('comunicaciones/<str:modo>', comunicaciones.comunicaciones),
    path('contactos', comunicaciones.get_contactos),
    path('llavero', llavero.llavero),
    path('leido/<int:id_comunicacion>', comunicaciones.leido),
    path('eliminado/<int:id_comunicacion>', comunicaciones.eliminado),
    path('importante/<int:id_comunicacion>', comunicaciones.importante)
]