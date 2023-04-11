package com.example.myapplication.rest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Rest {

    private static Rest INSTANCE;

    private String ANDROID_LOCALHOST = "http://10.0.2.2:8000";
    private String PC_LOCALHOST = "http://127.0.0.1:8000";
    private String BASE_URL = ANDROID_LOCALHOST;
    private Context context;
    private RequestQueue queue;

    private Rest(Context context) {
        this.context = context;
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

}
