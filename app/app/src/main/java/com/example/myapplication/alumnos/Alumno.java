package com.example.myapplication.alumnos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Alumno implements Parcelable {
    String usuario, nombre, apellido1, apellido2, nacimiento, dni, ultimo, grupo;
    int accesos, id;

    public Alumno(int id, String usuario, String nombre, String apellido1, String apellido2, String nacimiento, String dni, String ultimo, String grupo, int accesos) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.nacimiento = nacimiento;
        this.dni = dni;
        this.ultimo = ultimo;
        this.accesos = accesos;
        this.grupo = grupo;
    }

    public int getId() {
        return accesos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUltimo() {
        return ultimo;
    }

    public void setUltimo(String ultimo) {
        this.ultimo = ultimo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public int getAccesos() {
        return accesos;
    }

    public void setAccesos(int accesos) {
        this.accesos = accesos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.usuario);
        dest.writeString(this.nombre);
        dest.writeString(this.apellido1);
        dest.writeString(this.apellido2);
        dest.writeString(this.nacimiento);
        dest.writeString(this.dni);
        dest.writeString(this.ultimo);
        dest.writeString(this.grupo);
        dest.writeInt(this.accesos);
        dest.writeInt(this.id);
    }

    public void readFromParcel(Parcel source) {
        this.usuario = source.readString();
        this.nombre = source.readString();
        this.apellido1 = source.readString();
        this.apellido2 = source.readString();
        this.nacimiento = source.readString();
        this.dni = source.readString();
        this.ultimo = source.readString();
        this.grupo = source.readString();
        this.accesos = source.readInt();
        this.id = source.readInt();
    }

    protected Alumno(Parcel in) {
        this.usuario = in.readString();
        this.nombre = in.readString();
        this.apellido1 = in.readString();
        this.apellido2 = in.readString();
        this.nacimiento = in.readString();
        this.dni = in.readString();
        this.ultimo = in.readString();
        this.grupo = in.readString();
        this.accesos = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
