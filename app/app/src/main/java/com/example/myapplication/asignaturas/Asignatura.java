package com.example.myapplication.asignaturas;

public class Asignatura {
    private String materia, tutor;

    public Asignatura(String materia, String tutor) {
        this.materia = materia;
        this.tutor = tutor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
}
