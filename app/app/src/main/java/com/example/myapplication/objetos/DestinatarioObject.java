package com.example.myapplication.objetos;

import java.io.Serializable;

public class DestinatarioObject implements Serializable {
    private int id, tipoUsuario;
    private String nombre;

    public DestinatarioObject(int id, int tipoUsuario, String nombre) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
    }

    public DestinatarioObject() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
