package com.example.myapplication.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.rest.Rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perfil extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private PerfilAdapter adapter;
    private List<String> listItem;
    private JSONObject body;
    private Button botonGuardar;
    private Switch switchAjustes;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_perfil);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listItem = Arrays.asList(getResources().getStringArray(R.array.ajustes));
        listView = findViewById(R.id.list_view);

        botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(guardarListener);
        llenarLista();
    }

    private void llenarLista() {
        List<Boolean> lista = new ArrayList<>();

        JSONObject body = new JSONObject();
        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        try {
            body.put("token", sharedPreferences.getString("token", null));
            body.put("tipoUsuario", sharedPreferences.getString("tipoUsuario", null));
            System.out.println(body);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Rest rest = Rest.getInstance(this);

        rest.getAjustes(
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            lista.add(response.getBoolean("autentifiacion_dos_fases"));
                            lista.add(response.getBoolean("proteccion_restablecimiento"));
                            lista.add(response.getBoolean("not_calificaciones_push"));
                            lista.add(response.getBoolean("not_comunicaciones_push"));
                            lista.add(response.getBoolean("not_entrevistas_push"));
                            lista.add(response.getBoolean("not_extraescolares_push"));
                            lista.add(response.getBoolean("not_enfermeria_push"));
                            lista.add(response.getBoolean("not_calificaciones_email"));
                            lista.add(response.getBoolean("not_comunicaciones_email"));
                            lista.add(response.getBoolean("not_entrevistas_email"));
                            lista.add(response.getBoolean("not_extraescolares_email"));
                            lista.add(response.getBoolean("not_enfermeria_email"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        adapter = new PerfilAdapter(context, listItem, lista);
                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Perfil.this, "Error: " + error.networkResponse.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                body
        );
    }

    View.OnClickListener guardarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Rest rest = Rest.getInstance(context);
            JSONObject body = new JSONObject();
            rest.setAjustes(
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    },
                    body
            );
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}
