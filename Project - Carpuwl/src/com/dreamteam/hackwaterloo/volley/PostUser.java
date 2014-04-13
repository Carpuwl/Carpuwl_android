
package com.dreamteam.hackwaterloo.volley;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.dreamteam.hackwaterloo.Constants.Endpoint;

public class PostUser extends StringRequest {

    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_FB_KEY = "fb_fk";

    StringRequest request;

    private Map<String, String> mParameters;

    public PostUser(String name, String phone, String facebookPrivateKey,
            ErrorListener errorListener) {
        super(Method.POST, VolleyHelper.getRequestUrl(Method.POST, Endpoint.USER, null), null,
                errorListener);

        mParameters = new HashMap<String, String>();
        mParameters.put(PARAMETER_NAME, name);
        mParameters.put(PARAMETER_PHONE, phone);
        mParameters.put(PARAMETER_FB_KEY, facebookPrivateKey);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParameters;
    }

    @Override
    protected void deliverResponse(String response) {
        // Success. No implementation needed
    }
}
