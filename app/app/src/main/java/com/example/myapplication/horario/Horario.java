package com.example.myapplication.horario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.horario.recylerView.HorarioAdapter;
import com.example.myapplication.horario.recylerView.HorarioData;
import com.example.myapplication.rest.Rest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Horario extends Fragment {
    private HorarioAdapterTab adapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ArrayList<HorarioData> horario = new ArrayList<>();
    private ArrayList<HorarioData> horarioFiltrado = new ArrayList<>();


    public static Horario newInstance() {
        Horario fragment = new Horario();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horario, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);

        for (int i=0; i<5; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(getDiaSemana(i));
            tabLayout.addTab(tab);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.selectTab(tabLayout.getTabAt(diaSemana()));
        tabLayout.addOnTabSelectedListener(tabSelectedListener);
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            BlankFragment blankFragment = BlankFragment.newInstance();/*
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(tab.getPosition());
            viewPager.registerOnPageChangeCallback(onPageChangeCallback);
             */
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void peticion() {
        Rest rest = Rest.getInstance(getContext());
        rest.horario(
                response -> {
                    ArrayList<HorarioData> lista = new ArrayList<>();

                    for (int i=0; i<response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);

                            HorarioData horarioData = new HorarioData(
                                    object.getString("inicio"),
                                    object.getString("fin"),
                                    object.getString("materia"),
                                    object.getString("profesor"),
                                    object.getInt("dia")
                            );

                            lista.add(horarioData);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    updateHorario(lista);
                },
                error -> {

                }
        );
    }

    ViewPager2.OnPageChangeCallback onPageChangeCallback  =new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            tabLayout.getTabAt(position).select();
        }
    };
    private void updateHorario(List<HorarioData> data) {
        horario.clear();
        if(data!=null)
            horario.addAll(data);
        filtrarHorarioPorDia();
    }

    private void filtrarHorarioPorDia() {
        horarioFiltrado.clear();

        int dia = tabLayout.getSelectedTabPosition();

        for (int i=0; i<horario.size(); i++) {
            if(horario.get(i).getDia() == dia) {
                horarioFiltrado.add(horario.get(i));
            }
        }
    }

    private int diaSemana() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        return calendar.get(Calendar.DAY_OF_WEEK) - 2;
    }

    private String getDiaSemana(int i) {
        String[] diaSemana = {"L", "M", "X", "J", "V"};
        return diaSemana[i];
    }
}
