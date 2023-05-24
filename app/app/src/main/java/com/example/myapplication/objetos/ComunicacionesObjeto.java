package com.example.myapplication.objetos;

import java.io.Serializable;

public class ComunicacionesObjeto implements Serializable {
    private String asunto, mensaje, remitente, fecha;

    public ComunicacionesObjeto(String asunto, String mensaje, String remitente, String fecha) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.remitente = remitente;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }
}
