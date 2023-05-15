package com.example.myapplication.launcher;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import android.Manifest;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.inicio.Inicio;
import com.example.myapplication.login.Login;
import com.example.myapplication.rest.Rest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

public class Launcher extends AppCompatActivity {
    private Context context = this;
    private final String CHANNEL_ID = "default";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //getApplication().setTheme(R.style.Theme_MyApplication);
        super.onCreate(savedInstanceState);

        createNotificationChannel();

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
