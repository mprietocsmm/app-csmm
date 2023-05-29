package com.example.myapplication.ajustes.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.rest.Rest;

import org.json.JSONException;
import org.json.JSONObject;

public class CuentaFragment extends PreferenceFragmentCompat {
    public CuentaFragment() {}
    public Context context = getContext();
    public static CuentaFragment newInstance() {
        return new CuentaFragment();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_cuenta, rootKey);

        Preference preferenceUsuario = getPreferenceManager().findPreference("usuario");
        Preference preferenceNombre = getPreferenceManager().findPreference("nombre_completo");
        Preference preferenceFechaNacimiento = getPreferenceManager().findPreference("fecha_nacimiento");
        Preference preferenceDni = getPreferenceManager().findPreference("dni");
        Preference preferenceAccesos = getPreferenceManager().findPreference("accesos_totales");
        Preference preferenceAsociados = getPreferenceManager().findPreference("alumnos_asociados");

        Rest rest = Rest.getInstance(getContext());

        rest.getCuenta(
                response -> {
                    try {
                        preferenceUsuario.setTitle(response.getString("usuario"));
                        preferenceNombre.setTitle(response.getString("nombre"));
                        preferenceFechaNacimiento.setTitle(response.getString("nacimiento"));
                        preferenceDni.setTitle(response.getString("dni"));
                        preferenceAccesos.setTitle(response.getString("accesos"));
                        preferenceAsociados.setTitle(response.getString("asociados"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
    }


}
