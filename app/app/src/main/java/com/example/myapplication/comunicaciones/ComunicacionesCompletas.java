package com.example.myapplication.comunicaciones;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.objetos.ComunicacionesObjeto;

public class ComunicacionesCompletas extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView asuntoTextView, remitenteTextView, mensajeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicaciones_completas);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Comunicación");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        asuntoTextView = findViewById(R.id.asuntoTextView);
        remitenteTextView = findViewById(R.id.remitenteTextView);
        mensajeTextView = findViewById(R.id.mensajeTextView);

        ComunicacionesObjeto comunicacion = (ComunicacionesObjeto) getIntent().getSerializableExtra("comunicacion");

        asuntoTextView.setText(comunicacion.getAsunto());
        remitenteTextView.setText(comunicacion.getRemitente());
        mensajeTextView.setText(comunicacion.getMensaje());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
