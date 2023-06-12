package com.example.myapplication.alumnos;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class PerfilAlumno extends AppCompatActivity {
    private TextView nombre, apellidos, usuario, grupo, dni, nacimiento, ultimoAcceso, numAccesos;
    private Toolbar toolbar;
    private Alumno alumno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);
        setUpToolbar();
        nombre = findViewById(R.id.nombre);
        apellidos = findViewById(R.id.apellidos);
        usuario = findViewById(R.id.usuario);
        grupo = findViewById(R.id.grupo);
        dni = findViewById(R.id.dni);
        nacimiento = findViewById(R.id.nacimiento);
        ultimoAcceso = findViewById(R.id.ultimo_acceso);
        numAccesos = findViewById(R.id.accesos);

        alumno = getIntent().getParcelableExtra("alumno", Alumno.class);

        if (alumno != null) {
            nombre.setText(alumno.getNombre());
            apellidos.setText(alumno.getApellido1() + " " + alumno.getApellido2());
            usuario.setText(alumno.getUsuario());
            grupo.setText(alumno.getGrupo());
            dni.setText(alumno.getDni());
            nacimiento.setText(alumno.getNacimiento());
            ultimoAcceso.setText(alumno.getUltimo());
            //numAccesos.setText(alumno.getAccesos());
        }

    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
