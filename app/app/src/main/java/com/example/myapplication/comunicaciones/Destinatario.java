package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class Destinatario extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
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
        //ultimo parametro de setAdaptar es la lista de los posibles detinatarios
        listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_multiple_choice));
        llenarLista();
    }

    private void llenarLista() {
        
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
