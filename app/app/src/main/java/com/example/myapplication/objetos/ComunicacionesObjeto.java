package com.example.myapplication.objetos;

import java.io.Serializable;

public class ComunicacionesObjeto implements Serializable {
    private String asunto, mensaje, remitente, fecha, leido;
    private boolean importante;
    private int id;

    public ComunicacionesObjeto(int id, String asunto, String mensaje, String remitente, String fecha, String leido, boolean importante) {
        this.id = id;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.remitente = remitente;
        this.fecha = fecha;
        this.leido = leido;
        this.importante = importante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido = leido;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }
}
