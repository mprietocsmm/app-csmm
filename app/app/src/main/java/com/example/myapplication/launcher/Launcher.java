package com.example.myapplication.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.example.myapplication.inicio.Inicio;
import com.example.myapplication.login.Login;

public class Launcher extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        /*
        if (getSharedPreferences() != null) {
            intent = new Intent(this, Inicio.class);

        } else {
            intent = new Intent(this, Login.class);
        }
        */
        intent = new Intent(this, Inicio.class);
        startActivity(intent);
        finish();
    }

    private String getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("token", null);
    }
}
