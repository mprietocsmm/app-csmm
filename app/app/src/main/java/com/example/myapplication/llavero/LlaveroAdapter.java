package com.example.myapplication.llavero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.comunicaciones.ComunicacionesAdapter;
import com.example.myapplication.objetos.Llave;

import java.util.ArrayList;

public class LlaveroAdapter extends RecyclerView.Adapter<LlaveroAdapter.LlaveroViewHolder> {
    private ArrayList<Llave> lista;
    private Llavero.llaveroItemClick listener;
    public LlaveroAdapter(ArrayList<Llave> lista, Llavero.llaveroItemClick listener) {
        this.lista = lista;
        this.listener = listener;
    }


    @NonNull
    @Override
    public LlaveroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_llavero,parent,false);
        return new LlaveroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LlaveroViewHolder holder, int position) {
        holder.aplicacionTextView.setText(lista.get(position).getAplicacion());
        holder.emailTextView.setText(lista.get(position).getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(lista.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class LlaveroViewHolder extends RecyclerView.ViewHolder {
        public TextView aplicacionTextView, emailTextView;

        public LlaveroViewHolder(@NonNull View itemView) {
            super(itemView);

            aplicacionTextView = itemView.findViewById(R.id.aplicacion);
            emailTextView = itemView.findViewById(R.id.email);
        }
    }
}
