package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.objetos.DestinatarioObject;
import com.example.myapplication.rest.Rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Destinatario extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private ImageView checkButton;
    private Boolean[] checked;
    private DestinatarioAdapter adapter;
    private ConfirmarListener listener;
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinatario);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Destinatario");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.list_view_destinatario);

        checkButton = findViewById(R.id.tickToolbar);
        checkButton.setOnClickListener(confirmarListener);

        llenarLista();
    }

    private void llenarLista() {
        Rest rest = Rest.getInstance(context);

        rest.getContactos(
                response -> {
                    DestinatarioObject[] array;
                    array = new DestinatarioObject[response.length()];
                    checked = new Boolean[response.length()];
                    for (int i=0; i<response.length(); i++) {
                        try {
                            DestinatarioObject destinatario = new DestinatarioObject(response.getJSONObject(i).getInt("id"), response.getJSONObject(i).getInt("tipoUsuario"), response.getJSONObject(i).getString("nombre"));
                            array[i] = destinatario;

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("destinatarios", Context.MODE_PRIVATE);
                    if (sharedPreferences.getString("destinatarios", null) != null) {
                        try {
                            JSONObject object = new JSONObject(sharedPreferences.getString("destinatarios", null));
                            JSONArray jsonArray = object.getJSONArray("destinatarios");

                            for (int i=0; i<checked.length; i++) {
                                checked[i] = jsonArray.getJSONObject(i).getBoolean("checked");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    } else {
                        // Método para rellenar un
                        Arrays.fill(checked, false);
                        /*
                        for (int i=0; i<checked.length; i++)
                            checked[i] = false;
                         */
                    }
                    adapter = new DestinatarioAdapter(context, array, checked);
                    listener = (ConfirmarListener) adapter;
                    listView.setAdapter(adapter);
                },
                error -> Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show(),
                context
        );
    }

    View.OnClickListener confirmarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Boolean[] checkedArray;
            checkedArray = listener.confirmarListener();
            JSONArray array  = new JSONArray();

            for (int i=0; i<checkedArray.length; i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("nombre", adapter.getItem(i).getNombre());
                    object.put("id", adapter.getItem(i).getId());
                    object.put("tipoUsuario", adapter.getItem(i).getTipoUsuario());
                    object.put("checked", checkedArray[i]);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                array.put(object);
            }

            JSONObject destinatarios = new JSONObject();
            try {
                destinatarios.put("destinatarios", array);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            SharedPreferences sharedPreferences = getSharedPreferences("destinatarios", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("destinatarios", destinatarios.toString()).apply();
            onBackPressed();
            finish();
        }
    };
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
