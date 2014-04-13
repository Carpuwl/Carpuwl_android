
package com.dreamteam.hackwaterloo.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dreamteam.hackwaterloo.Constants;
import com.dreamteam.hackwaterloo.Constants.Endpoint;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {

    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private final Map<String, String> mParams;
    
    public GsonRequest(int method, Endpoint endpoint, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        this(method, endpoint, clazz, listener, errorListener, null);
    }

    public GsonRequest(int method, Endpoint endpoint, Class<T> clazz, Listener<T> listener,
            ErrorListener errorListener, Map<String, String> params) {
        super(method, Constants.BASE_URL + endpoint.getValue(), errorListener);
        this.mClazz = clazz;
        this.mListener = listener;
        mParams = params;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Gson gson = new Gson();
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
            
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

}
