package com.example.myapplication.comunicaciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.objetos.ComunicacionesObjeto;

import java.util.List;

public class ComunicacionesAdapter extends RecyclerView.Adapter<ComunicacionesAdapter.ViewHolder> {
    private List<ComunicacionesObjeto> lista;
    private RecyclerItemClick listener;

    public ComunicacionesAdapter(List<ComunicacionesObjeto> lista, RecyclerItemClick listener) {
        this.lista = lista;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comunicacion,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ComunicacionesObjeto item = lista.get(position);
        holder.asuntoTextView.setText(lista.get(position).getAsunto());
        holder.remitenteTextView.setText(lista.get(position).getRemitente());
        holder.mensajeTextView.setText(lista.get(position).getMensaje());
        holder.fechaTextView.setText(lista.get(position).getFecha());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView asuntoTextView, remitenteTextView, mensajeTextView, fechaTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            asuntoTextView = itemView.findViewById(R.id.asuntoTextView);
            remitenteTextView = itemView.findViewById(R.id.remitenteTextView);
            mensajeTextView = itemView.findViewById(R.id.mensajeTextView);
            fechaTextView = itemView.findViewById(R.id.fechaTextView);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(ComunicacionesObjeto item);
    }
}
