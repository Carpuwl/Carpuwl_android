package com.dreamteam.hackwaterloo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.dreamteam.hackwaterloo.Constants;

/**
 * @author Ryan
 * 
 */
public class NetworkHelper {

    private static final String TAG = NetworkHelper.class.getSimpleName();
    
    private static final boolean DEBUG = true;

    public static final String BASE_URL = "http://s417363377.onlinehome.us/";
    public static final String PHP_SUFFIX = ".php";

    public static String get(String endPoint) {
        return get(endPoint, null);
    }

    public static String get(String endPoint, List<NameValuePair> data) {
        if (DEBUG) {
            Log.i(TAG, "Get request sent to " + BASE_URL + endPoint + PHP_SUFFIX);
        }
        if (data != null) {
            if (DEBUG) {
                Log.i(TAG, "with parameters: " + data.toString());
            }
            endPoint += ("?" + URLEncodedUtils.format(data, Constants.UTF_8));
        }
        
        return executeRestfulApiRequest(new HttpGet(BASE_URL + endPoint + PHP_SUFFIX));
    }

    public static String post(String endPoint, List<NameValuePair> data) {
        HttpPost httpPost = new HttpPost(BASE_URL + endPoint + PHP_SUFFIX);

        if (DEBUG) {
            Log.i(TAG, "Post request sent to " + BASE_URL + endPoint + PHP_SUFFIX);
        }
        if (data != null) {
            try {
                if (DEBUG) {
                    Log.i(TAG, "with parameters: " + data.toString());
                }
                httpPost.setEntity(new UrlEncodedFormEntity(data, Constants.UTF_8));
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Could not encode parameters " + data.toString());
                e.printStackTrace();
            }
        }

        return executeRestfulApiRequest(httpPost);
    }

    private static String executeRestfulApiRequest(HttpRequestBase restfulRequestType) {
        String jsonResult = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse;

        try {
            httpResponse = httpClient.execute(restfulRequestType);
            if (DEBUG) {
                Log.i(TAG, "HTTP Response Code: " + httpResponse.getStatusLine().getStatusCode());
            }
            jsonResult = EntityUtils.toString(httpResponse.getEntity());
            if (DEBUG) {
                Log.i(TAG, "JSON Raw: " + jsonResult);
            }
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
