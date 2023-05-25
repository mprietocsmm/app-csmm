package com.example.myapplication.llavero;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.myapplication.R;
import com.example.myapplication.comunicaciones.ComunicacionesAdapter;
import com.example.myapplication.objetos.ComunicacionesObjeto;
import com.example.myapplication.objetos.Llave;
import com.example.myapplication.rest.Rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Llavero extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Llave> lista = new ArrayList<>();
    private TextView inicioTextView;
    public Llavero() {
        // Required empty public constructor
    }


    public static Llavero newInstance() {
        Llavero fragment = new Llavero();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_llavero, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        inicioTextView = view.findViewById(R.id.textViewInicio);

        Rest rest = Rest.getInstance(getContext());
        rest.getLlavero(
                response -> {
                    for (int i=0; i< response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            Llave llave = new Llave(
                                    object.getInt("id"),
                                    object.getString("aplicacion"),
                                    object.getString("usuario"),
                                    object.getString("email"),
                                    object.getString("contraseña"),
                                    object.getBoolean("modificable"));

                            lista.add(llave);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        LlaveroAdapter adapter = new LlaveroAdapter(lista, new llaveroItemClick() {
                            @Override
                            public void itemClick(Llave llave) {
                                Intent intent = new Intent(getContext(), LlaveCompleta.class);
                                intent.putExtra("llave", llave);
                                startActivity(intent);
                            }
                        });

                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
                        adapter.notifyDataSetChanged();
                    }

                },
                error -> {
                    if (error.networkResponse.statusCode == 401) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        inicioTextView.setVisibility(View.VISIBLE);
                    }
                },
                getContext()
        );
    }

    public interface llaveroItemClick {
        void itemClick(Llave llave);
    }
}

