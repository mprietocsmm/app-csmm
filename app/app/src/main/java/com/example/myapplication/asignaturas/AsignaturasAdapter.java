package com.example.myapplication.asignaturas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AsignaturasAdapter extends RecyclerView.Adapter<AsignaturasAdapter.ViewHolder> {
    private final ArrayList<Asignatura> lista;

    public AsignaturasAdapter(ArrayList<Asignatura> lista) {
        this.lista = lista;
        System.out.println("tamaño al ser recibida: " + lista.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_asignaturas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Asignatura item = lista.get(position);
        holder.asignaturaTextView.setText(item.getMateria());
        holder.tutorTextView.setText(item.getTutor());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView asignaturaTextView, tutorTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            asignaturaTextView = itemView.findViewById(R.id.item_asignatura);
            tutorTextView = itemView.findViewById(R.id.item_tutor);
        }
    }
}
