package com.example.myapplication.comunicaciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Comunicaciones extends Fragment {
    private static final String RECIBIDAS="recibidas", ELIMINADAS="eliminadas", ENVIADAS="enviadas";
    private FloatingActionButton desplegable, crearComunicado, crearAsistencia;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private boolean clicked = false;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView inicioTextView;
    private Spinner filtroSpinner;
    private List<ComunicacionesObjeto> lista = new ArrayList<>();
    private String[] arrayFiltros = new String[] {"Bandeja de entrada", "Enviados", "Borrados"};
    private ComunicacionesAdapter adapter;
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
            llenarRecyclerView(RECIBIDAS);
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        filtroSpinner = view.findViewById(R.id.filtro_dropdown);
        filtroSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrayFiltros));
        filtroSpinner.setOnItemSelectedListener(onItemSelectedListener);
        filtroSpinner.setSelection(0);
    }

    private void llenarRecyclerView(String modo) throws JSONException {
        lista.clear();
        Rest rest = Rest.getInstance(getContext());
        rest.getComunicaciones(
                response -> {
                    if (response.length() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        inicioTextView.setVisibility(View.INVISIBLE);
                        try {


                            for (int i=0; i<response.length(); i++) {

                                ComunicacionesObjeto comunicaciones = new ComunicacionesObjeto(
                                        response.getJSONObject(i).getInt("id"),
                                        response.getJSONObject(i).getString("asunto"),
                                        response.getJSONObject(i).getString("mensaje"),
                                        response.getJSONObject(i).getString("remitente"),
                                        response.getJSONObject(i).getString("fecha"),
                                        response.getJSONObject(i).getString("leido"),
                                        response.getJSONObject(i).getBoolean("importante"));
                                lista.add(comunicaciones);

                            }

                            adapter = new ComunicacionesAdapter(lista, item -> {
                                Intent intent = new Intent(getContext(), ComunicacionesCompletas.class);
                                intent.putExtra("comunicacion", item);
                                startActivity(intent);
                            }, getContext());

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
                    Toast.makeText(getContext(), "Fallo de conexión: " + error.toString(), Toast.LENGTH_LONG).show();
                    swipeRefresh.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                },
                modo
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

                llenarRecyclerView(getModo());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    };

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Rest rest = Rest.getInstance(getContext());

            if (direction == ItemTouchHelper.RIGHT) {
                if (getModo().equals(RECIBIDAS)) {
                    rest.eliminarComunicacion(
                            response -> {
                                lista.remove(viewHolder.getAdapterPosition());
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                Toast.makeText(getContext(), "Comunicación eliminada", Toast.LENGTH_SHORT).show();
                            },
                            error -> {},
                            lista.get(viewHolder.getAdapterPosition()).getId()
                    );
                } else if (getModo().equals(ELIMINADAS)) {
                    rest.restaurarComunicacion(
                            response -> {
                                lista.remove(viewHolder.getAdapterPosition());
                                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                Toast.makeText(getContext(), "Comunicación restaurada", Toast.LENGTH_SHORT).show();
                            },
                            error -> {},
                            lista.get(viewHolder.getAdapterPosition()).getId()
                    );
                }

            } else if (direction == ItemTouchHelper.LEFT) {
                JSONObject body = new JSONObject();

                try {
                    body.put("importante", !lista.get(viewHolder.getAdapterPosition()).isImportante());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                rest.setImportancia(
                        response -> {
                            lista.get(viewHolder.getAdapterPosition()).setImportante(!lista.get(viewHolder.getAdapterPosition()).isImportante());
                            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            },
                        error -> {},
                        body,
                        lista.get(viewHolder.getAdapterPosition()).getId()
                );
            }

        }
    };

    Spinner.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                switch (position) {
                    case 0:
                        llenarRecyclerView(RECIBIDAS);
                        break;
                    case 1:
                        Toast.makeText(getContext(), "Enviados", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        llenarRecyclerView(ELIMINADAS);
                        break;
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private String getModo() {
        switch (filtroSpinner.getSelectedItem().toString()) {
            case "Bandeja de entrada":
                return RECIBIDAS;
            case "Enviados":
                return ENVIADAS;
            case "Borrados":
                return ELIMINADAS;
        }
        return null;
    }
}
