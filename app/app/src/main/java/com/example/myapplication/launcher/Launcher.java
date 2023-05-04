package com.example.myapplication.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.inicio.Inicio;
import com.example.myapplication.login.Login;
import com.example.myapplication.rest.Rest;

import org.json.JSONException;
import org.json.JSONObject;

public class Launcher extends Activity {
    private Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //getApplication().setTheme(R.style.Theme_MyApplication);
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("destinatarios", Context.MODE_PRIVATE);
        sharedPreferences1.edit().clear().apply();
        if (sharedPreferences.getString("token", null) == null) {
            Intent intent = new Intent(getApplication(), Login.class);
            startActivity(intent);
            finish();
        } else {
            Rest rest = Rest.getInstance(this);
            JSONObject body = new JSONObject();
            try {
                body.put("token", sharedPreferences.getString("token", null));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            rest.authenticate(
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Intent intent = new Intent(context, Inicio.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sharedPreferences.edit().clear().apply();
                            Intent intent = new Intent(context, Login.class);
                            context.startActivity(intent);
                            finish();
                        }
                    },
                    body
            );
        }

    }


}
