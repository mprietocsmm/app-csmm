package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Comunicaciones extends Fragment {
    private Context context = getContext();

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

        FloatingActionButton button = view.findViewById(R.id.botonNuevaComunicacion);
        button.setOnClickListener(buttonListener);
    }

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), CrearComunicacion.class);
            startActivity(intent);
            // Transicion entre actividades
            //getActivity().overridePendingTransition(R.transition.enter_activity, R.transition.exit_activity);
        }
    };

}
