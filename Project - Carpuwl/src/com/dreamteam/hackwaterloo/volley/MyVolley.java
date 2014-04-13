package com.dreamteam.hackwaterloo.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton implementation for the Volley Networking Library.
 */
public enum MyVolley {
    
    INSTANCE;
    
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
}
