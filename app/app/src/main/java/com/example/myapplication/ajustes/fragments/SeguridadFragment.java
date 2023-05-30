package com.example.myapplication.ajustes.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.example.myapplication.R;

public class SeguridadFragment extends PreferenceFragmentCompat {
    public SeguridadFragment() {}

    public static CuentaFragment newInstance() {
        return new CuentaFragment();
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preference_seguridad, rootKey);
    }
}
