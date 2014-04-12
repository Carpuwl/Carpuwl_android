package com.dreamteam.hackwaterloo.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.dreamteam.hackwaterloo.Constants.Endpoint;

public enum MyVolley {
    
    INSTANCE;
    
    public static final String BASE_URL = "http://s417363377.onlinehome.us/";
    public static final String PHP_SUFFIX = ".php";
    
    private RequestQueue mRequestQueue;
    
    public static void init(Context context) {
        INSTANCE.mRequestQueue = Volley.newRequestQueue(context);
    }
    
    public static RequestQueue getRequestQueue() {
        if (INSTANCE.mRequestQueue != null) {
            return INSTANCE.mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
    
    public static String getRequestUrl(Endpoint endpoint) {
        return BASE_URL + endpoint.getValue() + PHP_SUFFIX;
    }
    
}
