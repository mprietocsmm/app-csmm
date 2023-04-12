package com.example.myapplication.utils;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

public class CustomRetryPolicy implements RetryPolicy {

    private static final int MAX_RETRIES = 1;
    private static final int TIMEOUT_MS = 2500;

    @Override
    public int getCurrentTimeout() {
        return TIMEOUT_MS;
    }

    @Override
    public int getCurrentRetryCount() {
        return MAX_RETRIES;
    }


    @Override
    public void retry(VolleyError error) throws VolleyError {
        // No reintenta automáticamente las solicitudes
        throw error;
    }
}