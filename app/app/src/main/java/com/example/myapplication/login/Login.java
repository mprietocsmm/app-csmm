package com.example.myapplication.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.rest.Rest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends Activity {
    private ImageView logo;
    private EditText email, contraseña;
    private Button inicioSesion;
    private Rest rest = Rest.getInstance(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = findViewById(R.id.logo);
        Picasso.get().load(rest.getBASE_URL() + "/static/log_csmm.png").into(logo);

        email = findViewById(R.id.emailEditText);

        contraseña = findViewById(R.id.constraseñaEditText);

        inicioSesion = findViewById(R.id.inicioSesionBoton);
        inicioSesion.setOnClickListener(inicioSesionListener);
    }

    View.OnClickListener inicioSesionListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (email.getText().length() <= 0) {
                email.setError("Campo obligatorio");
            } else if (contraseña.getText().length() <= 0) {
                contraseña.setError("Campo obligatorio");
            } else {
                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText().toString());
                    body.put("contraseña", contraseña.getText().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                rest.login(
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(Login.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        },
                        body
                        );
            }


        }
    };
}