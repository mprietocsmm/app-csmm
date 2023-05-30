package com.example.myapplication.rest;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.utils.CustomRetryPolicy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Rest {

    private static Rest INSTANCE;

    private String ANDROID_LOCALHOST = "http://10.0.2.2:8000";
    private String PC_LOCALHOST = "http://192.168.82.231:8000";
    private String BASE_URL = ANDROID_LOCALHOST;
    private Context context;
    private RequestQueue queue;

    private Rest(Context context) {
        this.context = context;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public static Rest getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Rest(context);
        }

        return INSTANCE;
    }

    public void health(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL + "/health",
                null,
                onResponse,
                onErrorResponse
        ));
    }

    public void authenticate(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/autenticar",
                body,
                onResponse,
                onErrorResponse
        ).setRetryPolicy(new CustomRetryPolicy()));
    }

    public void login(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/login",
                body,
                onResponse,
                onErrorResponse
        ).setRetryPolicy(new CustomRetryPolicy()));
    }

    public void inicio(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/inicio",
                body,
                onResponse,
                onErrorResponse
        ).setRetryPolicy(new CustomRetryPolicy()));
    }

    public void comunicaciones(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + "/comunicaciones/enviar",
                body,
                onResponse,
                onErrorResponse
        )).setRetryPolicy(new CustomRetryPolicy());
    }

    public void getComunicaciones(Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL + "/comunicaciones/recibidas",
                null,
                onResponse,
                onErrorResponse
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
                String sessionToken = preferences.getString("token", null);
                String tipoUsuario = preferences.getString("tipoUsuario", null);

                HashMap<String, String> myHeaders = new HashMap<>();
                myHeaders.put("token", sessionToken);
                myHeaders.put("tipoUsuario", tipoUsuario);
                return myHeaders;
            }
        });
    }


    public void getCuenta(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/ajustes/cuenta",
                null, 
                onResponse,
                onErrorResponse,
                context
        ));
    }
  
    public void getContactos(Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse, Context context) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonArrayWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/contactos",
                null,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void getAjustes(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/perfil",
                null,
                onResponse,
                onErrorResponse,
                context
         ));
    }

    public void getLlavero(Response.Listener<JSONArray> onResponse, Response.ErrorListener onErrorResponse, Context context) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonArrayWithCustomAuth(
                Request.Method.GET,
                BASE_URL + "/llavero",
                null,
                onResponse,
                onErrorResponse,
                context
        ));
    }

    public void setAjustes(Response.Listener<JSONObject> onResponse, Response.ErrorListener onErrorResponse, JSONObject body) {
        queue = Volley.newRequestQueue(context);
        queue.add(new JsonObjectRequestWithCustomAuth(
                Request.Method.POST,
                BASE_URL + "/perfil",
                body,
                onResponse,
                onErrorResponse,
                context
        ));
    }


    class JsonObjectRequestWithCustomAuth extends JsonObjectRequest {
        private Context context;

        public JsonObjectRequestWithCustomAuth(int method,
                                               String url,
                                               @Nullable JSONObject jsonRequest,
                                               Response.Listener<JSONObject> listener,
                                               @Nullable Response.ErrorListener errorListener,
                                               Context context) {
            super(method, url, jsonRequest, listener, errorListener);
            this.context = context;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            SharedPreferences preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            String sessionToken = preferences.getString("token", null);
            String tipoUsuario = preferences.getString("tipoUsuario", null);

            HashMap<String, String> myHeaders = new HashMap<>();
            myHeaders.put("token", sessionToken);
            myHeaders.put("tipoUsuario", tipoUsuario);
            return myHeaders;
        }
    }

    class JsonArrayWithCustomAuth extends JsonArrayRequest {
        private Context context;

        public JsonArrayWithCustomAuth(int method,
                                                   String url,
                                                   @Nullable JSONArray jsonRequest,
                                                   Response.Listener<JSONArray> listener,
                                                   @Nullable Response.ErrorListener errorListener,
                                                    Context context) {
            super(method, url, jsonRequest, listener, errorListener);
            this.context = context;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            SharedPreferences preferences = context.getSharedPreferences("usuario", Context.MODE_PRIVATE);
            String sessionToken = preferences.getString("token", null);
            String tipoUsuario = preferences.getString("tipoUsuario", null);

            HashMap<String, String> myHeaders = new HashMap<>();
            myHeaders.put("token", sessionToken);
            myHeaders.put("tipoUsuario", tipoUsuario);
            return myHeaders;
        }
    }


}
