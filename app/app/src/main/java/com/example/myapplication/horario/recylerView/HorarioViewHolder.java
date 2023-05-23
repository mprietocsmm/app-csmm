package com.example.myapplication.horario.recylerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class HorarioViewHolder extends RecyclerView.ViewHolder{

    TextView tvHoraInicial;
    TextView tvHoraFinal;
    TextView tvAsignatura;
    TextView tvProfesor;
    ImageView lineaAbajo, lineaArriba, punto;
    ConstraintLayout mLayout;

    public HorarioViewHolder(View itemView) {
        super(itemView);

        lineaAbajo = itemView.findViewById(R.id.lineaAbajo);
        lineaArriba = itemView.findViewById(R.id.lineaArriba);
        punto = itemView.findViewById(R.id.punto);
        tvHoraInicial = itemView.findViewById(R.id.horaInicial);
        tvHoraFinal = itemView.findViewById(R.id.horaFinal);
        tvAsignatura = itemView.findViewById(R.id.asignatura);
        tvProfesor = itemView.findViewById(R.id.profesor);
        mLayout = itemView.findViewById(R.id.layout);
    }
}
