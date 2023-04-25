package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.rest.Rest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Destinatario extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private Button botonConfirmar;
    private List<Boolean> checked = new ArrayList<>();
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
        listView.setOnItemClickListener(itemClickListener);

        botonConfirmar = findViewById(R.id.boton_confirmar);
        botonConfirmar.setOnClickListener(confirmarListener);

        if (checked.size() < 1)
            llenarLista();
    }

    private void llenarLista() {
        Rest rest = Rest.getInstance(context);

        rest.getContactos(
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String array[];
                        array = new String[response.length()];

                        for (int i=0; i<response.length(); i++) {
                            try {
                                array[i] = response.getString(i);
                                checked.add(false);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_multiple_choice, array));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                context
        );
    }

    View.OnClickListener confirmarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(context, "OnResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(context, "OnRestart", Toast.LENGTH_SHORT).show();
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            checked.set(position, !checked.get(position));
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
