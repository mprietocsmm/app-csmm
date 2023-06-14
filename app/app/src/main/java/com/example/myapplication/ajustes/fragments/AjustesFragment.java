package com.example.myapplication.ajustes.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.myapplication.R;

public class AjustesFragment extends PreferenceFragmentCompat {
    public AjustesFragment() {}

    public static AjustesFragment newInstance() {
        return new AjustesFragment();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_ajustes, rootKey);

        // Declaración de las preferencias
        Preference preferenceCuenta = getPreferenceManager().findPreference("cuenta");
        Preference preferenceWebGestor = getPreferenceManager().findPreference("gestor");
        Preference preferenceSeguridad = getPreferenceManager().findPreference("seguridad");
        Preference preferenceNotificaciones = getPreferenceManager().findPreference("notificaciones");
        Preference preferenceRecursosAdicionales = getPreferenceManager().findPreference("recursos_adicionales");


        // Listeners
        preferenceCuenta.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });

        preferenceWebGestor.setOnPreferenceClickListener(preference -> {
            Uri uri = Uri.parse("https://colegio.santamariadelmar.org/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        });

        preferenceSeguridad.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });

        preferenceNotificaciones.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });

        preferenceRecursosAdicionales.setOnPreferenceClickListener(preference -> {
            cambiarFragment(preference.getKey());
            return true;
        });
    }

    private void cambiarFragment(String key) {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment)fragment;
        NavController navController = navHostFragment.getNavController();

        switch (key) {
            case "cuenta":
                navController.navigate(R.id.cuentaFragment);
                break;
            case "seguridad":
                navController.navigate(R.id.seguridadFragment);
                break;
            case "notificaciones":
                navController.navigate(R.id.notificacionesFragment);
                break;
            case "recursos_adicionales":
                navController.navigate(R.id.recursosAdicionalesFragment);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
