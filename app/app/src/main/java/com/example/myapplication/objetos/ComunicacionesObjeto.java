package com.example.myapplication.objetos;

import java.io.Serializable;

public class ComunicacionesObjeto implements Serializable {
    private String asunto, mensaje, remitente;

    public ComunicacionesObjeto(String asunto, String mensaje, String remitente) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.remitente = remitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getRemitente() {
        return remitente;
    }
}
