package com.example.myapplication.horario;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.horario.recylerView.HorarioAdapter;
import com.example.myapplication.horario.recylerView.HorarioData;

import java.util.ArrayList;

public class HorarioObjectFragment extends Fragment {
    public static ArrayList<HorarioData> horario = new ArrayList<>();
    private OnListFragmentInteractionListener interactionListener;
    private static final String KEY = "horario";
    public HorarioObjectFragment() {}

    public static HorarioObjectFragment newInstance(ArrayList<HorarioData> horario) {
        HorarioObjectFragment fragment = new HorarioObjectFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY, horario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            throw new RuntimeException("Horario no recibido");
        }
        horario = getArguments().getParcelableArrayList(KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horario_tab, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new HorarioAdapter(horario, interactionListener, context));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            interactionListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " debe implementar OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

}
