package com.example.myapplication.horario.recylerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.horario.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioViewHolder> {
    private ArrayList<HorarioData> horario = new ArrayList<>();
    private final OnListFragmentInteractionListener interactionListener;
    private Context context;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public HorarioAdapter(ArrayList<HorarioData> horario, OnListFragmentInteractionListener interactionListener, Context context) {
        this.horario = horario;
        this.interactionListener = interactionListener;
        this.context = context;
    }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_horario,
                parent, false);
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorarioViewHolder holder, int position) {
        HorarioData horarioData = horario.get(position);

        if(holder.getAdapterPosition() == 0) {
            holder.lineaArriba.setVisibility(View.INVISIBLE);
        }
        if(horarioData.getProfesor().equals("")) {
            holder.tvAsignatura.setPadding(0, 40, 0, 0);
        } else {
            holder.tvAsignatura.setPadding(0, 0, 0, 0);
        }

        // Seteamos los textos
        holder.tvHoraInicial.setText(horarioData.getHoraInicio());
        holder.tvHoraFinal.setText(horarioData.getHoraFinal());
        holder.tvAsignatura.setText(horarioData.getAsignatura());
        holder.tvProfesor.setText(horarioData.getProfesor());
        setAnimation(holder.itemView, holder.getAdapterPosition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactionListener.onListFragmentInteraction(horarioData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return horario.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
