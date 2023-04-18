package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.rest.Rest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearComunicacion extends AppCompatActivity {
    private Button enviarButton;
    private TextInputEditText destinatarioTextInput, asuntoTextInput, mensajeTextInput;
    private Toolbar toolbar;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comunicacion);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Crear comunicado");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        destinatarioTextInput = findViewById(R.id.destinatarioTextInput);
        asuntoTextInput = findViewById(R.id.asuntoTextInput);
        mensajeTextInput = findViewById(R.id.mensajeTextInput);

        enviarButton = findViewById(R.id.enviarButton);
        enviarButton.setOnClickListener(enviarListener);
    }

    View.OnClickListener enviarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (destinatarioTextInput.getText().length() < 1) {
                destinatarioTextInput.setError("Campo obligatorio");
            } else if (asuntoTextInput.getText().length() < 1) {
                asuntoTextInput.setError("Campo obligatorio");
            } else if (mensajeTextInput.getText().length() < 1) {
                mensajeTextInput.setError("Campo obligatorio");
            } else {
                Rest rest = Rest.getInstance(context);
                SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());

                JSONObject body = new JSONObject();
                try {
                    body.put("tiporemite", sharedPreferences.getString("tipoUsuario", null));
                    body.put("token", sharedPreferences.getString("token", null));
                    body.put("fecha", sdf.format(new Date()));
                    body.put("destinatario", destinatarioTextInput.getText().toString());
                    body.put("asunto", asuntoTextInput.getText().toString());
                    body.put("texto", mensajeTextInput.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                rest.comunicaciones(
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(context, "¡Comunicación enviada con éxito!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        },
                        body
                );
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
