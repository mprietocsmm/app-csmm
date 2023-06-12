package com.example.myapplication.alumnos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.comunicaciones.ComunicacionesAdapter;
import com.example.myapplication.comunicaciones.ComunicacionesCompletas;
import com.example.myapplication.rest.Rest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;

import java.util.ArrayList;

public class ListadoAlumnos extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView inicioTextView;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<Alumno> lista = new ArrayList<>();
    private ListadoAlumnosAdapter adapter;

    public static ListadoAlumnos newInstance() {
        return new ListadoAlumnos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado_alumnos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        shimmerFrameLayout = view.findViewById(R.id.preview);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(onRefreshListener);

        inicioTextView = view.findViewById(R.id.textViewInicio);

        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        llenarRecyclerView();

    }

    private void llenarRecyclerView() {
        Rest rest = Rest.getInstance(getContext());
        rest.listadoAlumnos(
                response -> {

                    if (response.length() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        inicioTextView.setVisibility(View.INVISIBLE);
                        try {
                            lista.clear();

                            for (int i=0; i<response.length(); i++) {
                                lista.add(new Alumno(
                                        response.getJSONObject(i).getInt("id"),
                                        response.getJSONObject(i).getString("usuario"),
                                        response.getJSONObject(i).getString("nombre"),
                                        response.getJSONObject(i).getString("apellido1"),
                                        response.getJSONObject(i).getString("apellido2"),
                                        response.getJSONObject(i).getString("nacimiento"),
                                        response.getJSONObject(i).getString("dni"),
                                        response.getJSONObject(i).getString("ultimo_acceso"),
                                        response.getJSONObject(i).getString("grupo"),
                                        response.getJSONObject(i).getInt("accesos")));
                            }
                            adapter = new ListadoAlumnosAdapter(lista, item -> {
                                Intent intent = new Intent(getContext(), PerfilAlumno.class);
                                intent.putExtra("alumno", item);
                                startActivity(intent);
                            });

                            recyclerView.setVisibility(View.VISIBLE);

                            // Parar la preview de carga de datos y hacerla desaparecer
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);

                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            swipeRefresh.setRefreshing(false);
                            throw new RuntimeException(e);
                        }

                    } else {
                        recyclerView.setVisibility(View.INVISIBLE);
                        inicioTextView.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    }

                    swipeRefresh.setRefreshing(false);
                },
                error -> {
                    switch(error.networkResponse.statusCode) {
                        case 401:
                            swipeRefresh.setRefreshing(false);
                            recyclerView.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.GONE);
                            inicioTextView.setText("No tienes acceso...");
                            inicioTextView.setVisibility(View.VISIBLE);
                            break;
                    }
                }
        );
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            // Hacemos desaparecer el recyclerView
            recyclerView.setVisibility(View.GONE);
            inicioTextView.setVisibility(View.GONE);
            // Hacemos aparecer la preview de carga
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
            // Quitamos el foco del search view y limpiamos el texto
            searchView.setQuery("", false);
            searchView.clearFocus();

            llenarRecyclerView();
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.setFocusable(false);
        return false;
    }

    private void filtrarLista(String query) {
        ArrayList<Alumno> listaFiltrada = new ArrayList<>();

        for (int i=0; i< lista.size(); i++) {
            // Ponemos ambos strings UPPERCASE para que la búsqueda sea ignoreCase
            if (lista.get(i).getNombre().toUpperCase().startsWith(query.toUpperCase()))
                listaFiltrada.add(lista.get(i));
        }

        adapter.filterList(listaFiltrada);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filtrarLista(newText);
        return true;
    }
}