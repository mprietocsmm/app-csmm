package com.example.myapplication.alumnos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.comunicaciones.ComunicacionesAdapter;
import com.example.myapplication.objetos.ComunicacionesObjeto;

import java.util.ArrayList;

public class ListadoAlumnosAdapter extends RecyclerView.Adapter<ListadoAlumnosAdapter.ViewHolder> {
    private ArrayList<Alumno> listaAlumnos;
    private AlumnoItemClick listener;

    public ListadoAlumnosAdapter(ArrayList<Alumno> listaAlumnos, AlumnoItemClick listener) {
        this.listaAlumnos = listaAlumnos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alumno,parent,false);
        return new ListadoAlumnosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Alumno item = listaAlumnos.get(position);
        holder.nombreTextView.setText(item.getNombre());
        holder.grupoTextView.setText(item.getGrupo());
        holder.usuarioTextView.setText(item.getUsuario());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView, grupoTextView, usuarioTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            grupoTextView = itemView.findViewById(R.id.grupoTextView);
            usuarioTextView = itemView.findViewById(R.id.usuarioTextView);
        }
    }

    public void filterList(ArrayList<Alumno> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        listaAlumnos = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public interface AlumnoItemClick {
        void itemClick(Alumno item);
    }
}
