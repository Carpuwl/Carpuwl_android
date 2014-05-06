package com.dreamteam.hackwaterloo.volley;

import java.util.Map;

import android.util.Log;

import com.android.volley.Request.Method;
import com.dreamteam.hackwaterloo.common.Constants.Endpoint;


public class VolleyHelper {
    
    private static final String TAG = VolleyHelper.class.getSimpleName();
    
    private static final String BASE_URL = "http://justasecading.com/carpuwl/PHP/";
    private static final String PHP_SUFFIX = ".php";

    public static String getRequestUrl(int method, Endpoint endpoint, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + endpoint.getValue() + PHP_SUFFIX);
        
        // If it's a get request, the parameters must be appended to the url
        if (method == Method.GET && params != null && params.size() > 0) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
    
        Log.d(TAG, "Url built: " + urlBuilder.toString());
        return urlBuilder.toString();
    }
    

}
