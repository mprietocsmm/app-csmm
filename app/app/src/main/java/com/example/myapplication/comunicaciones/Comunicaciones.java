package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Comunicaciones extends Fragment {
    /*
    private Animation rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim);
    private Animation rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
    private Animation fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim);
    private Animation toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);
     */

    private FloatingActionButton desplegable, crearComunicado, crearAsistencia;
    private Button button;
    private boolean clicked = false;

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

        desplegable = view.findViewById(R.id.botonDesplegable);
        desplegable.setOnClickListener(desplegableistener);

        crearComunicado = view.findViewById(R.id.botonNuevoComunicado);
        crearComunicado.setOnClickListener(crearComunicadoListener);

        crearAsistencia = view.findViewById(R.id.botonNuevaAsistencia);
        crearAsistencia.setOnClickListener(crearAsistenciaListener);


    }


    View.OnClickListener desplegableistener = new View.OnClickListener() {
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

}
