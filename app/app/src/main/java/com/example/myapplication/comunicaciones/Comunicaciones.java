package com.example.myapplication.comunicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.objetos.ComunicacionesObjeto;
import com.example.myapplication.rest.Rest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Comunicaciones extends Fragment {

    private FloatingActionButton desplegable, crearComunicado, crearAsistencia;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private boolean clicked = false;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView inicioTextView;

    public static Comunicaciones newInstance() {
        Comunicaciones fragment = new Comunicaciones();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comunicaciones, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        try {
            llenarRecyclerView();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        shimmerFrameLayout = view.findViewById(R.id.preview);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(onRefreshListener);
        desplegable = view.findViewById(R.id.botonDesplegable);
        desplegable.setOnClickListener(desplegableListener);

        crearComunicado = view.findViewById(R.id.botonNuevoComunicado);
        crearComunicado.setOnClickListener(crearComunicadoListener);

        crearAsistencia = view.findViewById(R.id.botonNuevaAsistencia);
        crearAsistencia.setOnClickListener(crearAsistenciaListener);

        inicioTextView = view.findViewById(R.id.textViewInicio);
    }

    private void llenarRecyclerView() throws JSONException {
        Rest rest = Rest.getInstance(getContext());
        rest.getComunicaciones(
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length() != 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            inicioTextView.setVisibility(View.INVISIBLE);
                            try {
                                List<ComunicacionesObjeto> lista = new ArrayList<>();

                                for (int i=0; i<response.length(); i++) {

                                    ComunicacionesObjeto comunicaciones = new ComunicacionesObjeto(
                                            response.getJSONObject(i).getString("asunto"),
                                            response.getJSONObject(i).getString("mensaje"),
                                            response.getJSONObject(i).getString("remitente"));

                                    lista.add(comunicaciones);

                                }

                                ComunicacionesAdapter adapter = new ComunicacionesAdapter(lista, new ComunicacionesAdapter.RecyclerItemClick() {
                                    @Override
                                    public void itemClick(ComunicacionesObjeto item) {
                                        Intent intent = new Intent(getContext(), ComunicacionesCompletas.class);
                                        intent.putExtra("comunicacion", item);
                                        startActivity(intent);
                                    }
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Fallo de conexión", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                        recyclerView.setVisibility(View.GONE);
                        shimmerFrameLayout.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    View.OnClickListener desplegableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setVisibility(clicked);
            setAnimation(clicked);
            setClickable(clicked);
            clicked = !clicked;
        }
    };

    View.OnClickListener crearComunicadoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), CrearComunicacion.class);
            startActivity(intent);
        }
    };

    View.OnClickListener crearAsistenciaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getContext(), "Crear asistencia", Toast.LENGTH_SHORT).show();
        }
    };

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            crearComunicado.setVisibility(View.VISIBLE);
            crearAsistencia.setVisibility(View.VISIBLE);
        } else {
            crearComunicado.setVisibility(View.GONE);
            crearAsistencia.setVisibility(View.GONE);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            crearComunicado.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim));
            crearAsistencia.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim));
            desplegable.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim));
        } else {
            crearComunicado.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim));
            crearAsistencia.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim));
            desplegable.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim));
        }
    }

    private void setClickable(boolean clicked) {
        if (clicked) {
            crearComunicado.setClickable(false);
            crearAsistencia.setClickable(false);
        } else {
            crearComunicado.setClickable(true);
            crearAsistencia.setClickable(true);
        }
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            try {
                // Hacemos desaparecer el recyclerView
                recyclerView.setVisibility(View.GONE);
                inicioTextView.setVisibility(View.GONE);
                // Hacemos aparecer la preview de carga
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();

                llenarRecyclerView();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

}
