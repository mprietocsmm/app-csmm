package com.example.myapplication.asignaturas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.rest.Rest;

import org.json.JSONException;

import java.util.ArrayList;

public class  ListaAsignaturas extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Asignatura> lista = new ArrayList<>();
    public ListaAsignaturas() {}

    public static ListaAsignaturas newInstance() {
        return new ListaAsignaturas();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asignaturas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);

        Rest rest = Rest.getInstance(getContext());

        rest.asignaturasAlumno(
                response -> {
                    for (int i=0; i<response.length(); i++) {
                        try {
                            lista.add(new Asignatura(response.getJSONObject(i).getString("materia"), response.getJSONObject(i).getString("tutor")));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        AsignaturasAdapter adapter = new AsignaturasAdapter(lista);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
                    }
                },
                error -> {

                }
        );


    }
}
