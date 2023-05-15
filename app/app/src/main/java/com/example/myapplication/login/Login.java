package com.example.myapplication.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.inicio.Inicio;
import com.example.myapplication.rest.Rest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private ImageView logo;
    private TextInputLayout usuarioLayout, contraseñaLayout;
    private TextInputEditText usuario, contraseña;
    private Button inicioSesion;
    private Rest rest = Rest.getInstance(this);
    private Context context = this;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.blue));
        logo = findViewById(R.id.logo);
        Picasso.get().load(rest.getBASE_URL() + "/static/log_csmm.png").into(logo);

        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        //getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        usuario = findViewById(R.id.usuarioTextInput);
        usuarioLayout = findViewById(R.id.usuarioTextInputLayout);

        contraseña = findViewById(R.id.contraseñaTextInput);
        contraseñaLayout = findViewById(R.id.contraseñaTextInputLayout);
        inicioSesion = findViewById(R.id.botonMaterialButton);
        inicioSesion.setOnClickListener(inicioSesionListener);
        askNotificationPermission();
    }

    View.OnClickListener inicioSesionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            usuarioLayout.setHelperText("");
            contraseñaLayout.setHelperText("");
            if (usuario.getText().length() <= 0) {
                usuarioLayout.setError("");
                usuarioLayout.setHelperText("Campo obligatorio");
            } else if (contraseña.getText().length() <= 0) {
                contraseñaLayout.setError("");
                contraseñaLayout.setHelperText("Campo obligatorio");
            } else {
                JSONObject body = new JSONObject();
                try {
                    body.put("email", usuario.getText().toString());
                    body.put("contraseña", contraseña.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                rest.login(
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    SharedPreferences sharedPreferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("token", response.getString("token")).apply();
                                    sharedPreferences.edit().putString("tipoUsuario", response.getString("tipoUsuario")).apply();
                                    Intent intent = new Intent(context, Inicio.class);
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.networkResponse.statusCode == 403) {
                                    contraseñaLayout.setError("");
                                    contraseñaLayout.setHelperText("Contraseña incorrecta");
                                } else if (error.networkResponse.statusCode == 404) {
                                    usuarioLayout.setError("");
                                    usuarioLayout.setHelperText("Usuario no registrado");
                                }
                            }
                        },
                        body
                        );
            }


        }
    };
}
