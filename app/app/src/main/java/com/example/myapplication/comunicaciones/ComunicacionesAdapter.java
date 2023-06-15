package com.example.myapplication.comunicaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.objetos.ComunicacionesObjeto;

import java.util.List;

public class ComunicacionesAdapter extends RecyclerView.Adapter<ComunicacionesAdapter.ViewHolder> {
    private List<ComunicacionesObjeto> lista;
    private RecyclerItemClick listener;
    private Context context;
    public ComunicacionesAdapter(List<ComunicacionesObjeto> lista, RecyclerItemClick listener, Context context) {
        this.lista = lista;
        this.listener = listener;
        this.context = context;
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
        holder.fechaTextView.setText(lista.get(position).getFecha());
        holder.itemView.setOnClickListener(v -> listener.itemClick(item));

        if (lista.get(position).getLeido().equals("null")) {
            holder.item_comunicacion.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_500));
        } else {
            holder.item_comunicacion.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        if (lista.get(position).isImportante()) {
            holder.importante_imagen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_importante));
        }

    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView asuntoTextView, remitenteTextView, fechaTextView;
        public ConstraintLayout item_comunicacion;
        public ImageView importante_imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            asuntoTextView = itemView.findViewById(R.id.asuntoTextView);
            remitenteTextView = itemView.findViewById(R.id.remitenteTextView);
            fechaTextView = itemView.findViewById(R.id.fechaTextView);

            item_comunicacion = itemView.findViewById(R.id.item_comunicacion);

            importante_imagen = itemView.findViewById(R.id.importante_imagen);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(ComunicacionesObjeto item);
    }
}
