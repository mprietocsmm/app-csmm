package com.example.myapplication.horario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.horario.recylerView.HorarioData;

import java.util.ArrayList;

public class HorarioAdapterTab extends FragmentStateAdapter {
    private ArrayList<HorarioData> horario;

    public HorarioAdapterTab(FragmentManager manager, ArrayList<HorarioData> lista) {
        super(manager.getPrimaryNavigationFragment());
        horario = lista;
    }



    private ArrayList<HorarioData> filtrarHorarioPorDia(int dia) {
        ArrayList<HorarioData> horarioFiltrado = new ArrayList<>();

        for (int i=0; i<horario.size(); i++) {
            if(horario.get(i).getDia() == dia) {
                horarioFiltrado.add(horario.get(i));
            }
        }

        return horarioFiltrado;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HorarioObjectFragment.newInstance(filtrarHorarioPorDia(position));
    }
}
