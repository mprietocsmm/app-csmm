package com.example.myapplication.objetos;

import java.io.Serializable;

public class Llave implements Serializable {
    private int id;
    private String aplicacion, usuario, email, contraseña;
    private boolean modificable;

    public Llave(int id, String aplicacion, String usuario, String email, String contraseña, boolean modificable) {
        this.id = id;
        this.aplicacion = aplicacion;
        this.usuario = usuario;
        this.email = email;
        this.contraseña = contraseña;
        this.modificable = modificable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public boolean isModificable() {
        return modificable;
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }
}
