package com.example.myapplication.llavero;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.objetos.Llave;
import com.google.android.material.textfield.TextInputEditText;

public class LlaveCompleta extends AppCompatActivity {
    private Llave llave;
    private TextView aplicacion, email, usuario;
    private TextInputEditText contraseña;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llave_completa);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Comunicación");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            llave = getIntent().getSerializableExtra("llave", Llave.class);
        }

        aplicacion = findViewById(R.id.aplicacion);
        email = findViewById(R.id.email);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);

        aplicacion.setText(llave.getAplicacion());
        usuario.setText(llave.getUsuario());
        email.setText(llave.getEmail());

        contraseña.setText(llave.getContraseña());
        if (!llave.isModificable()) {
            contraseña.setInputType(InputType.TYPE_NULL);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_llavero_completo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
