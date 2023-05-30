package com.example.myapplication.ajustes.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;

public class RecursosAdicionalesFragment extends PreferenceFragmentCompat {
    public RecursosAdicionalesFragment() {}

    public static RecursosAdicionalesFragment newInstance() {
        return new RecursosAdicionalesFragment();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_recursos_adicionales, rootKey);

        Preference preferenceVersion = getPreferenceManager().findPreference("version");
        Preference preferenceInformeFallos = getPreferenceManager().findPreference("informe_fallos");
        Preference preferenceCondicionesUso = getPreferenceManager().findPreference("condiciones_de_uso");
        Preference preferencePoliticaPrivacidad = getPreferenceManager().findPreference("politica_privacidad");
        Preference preferenceAvisosLegales = getPreferenceManager().findPreference("avisos_legales");

        preferenceVersion.setSummary(BuildConfig.VERSION_NAME);

        preferenceInformeFallos.setOnPreferenceClickListener(preference -> {
            return true;
        });

        preferenceCondicionesUso.setOnPreferenceClickListener(preference -> {
            Uri uri = Uri.parse("https://www.santamariadelmar.es/?page_id=19781");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        });

        preferencePoliticaPrivacidad.setOnPreferenceClickListener(preference -> {
            Uri uri = Uri.parse("https://www.santamariadelmar.es/?page_id=19782");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return false;
        });

        preferenceAvisosLegales.setOnPreferenceClickListener(preference -> {
            Uri uri = Uri.parse("https://www.santamariadelmar.es/?page_id=19781");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        });
    }


}
