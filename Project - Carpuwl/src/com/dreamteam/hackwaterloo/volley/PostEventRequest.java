package com.dreamteam.hackwaterloo.volley;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.Constants.Endpoint;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.models.Feed.SerializedNames;

public class PostEventRequest extends StringRequest {
    
    private Event mEvent;

    public PostEventRequest(int method, Endpoint endpoint, Listener<String> listener,
            ErrorListener errorListener, Event event) {
        super(method, MyVolley.getRequestUrl(endpoint), listener, errorListener);
        mEvent = event;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("fb_fk", String.valueOf(AppData.getFacebookForeginKey())); // TODO: wtf?
        params.put(SerializedNames.PRICE, String.valueOf(mEvent.getPrice()));
        params.put(SerializedNames.SEATS_REMAINING, String.valueOf(mEvent.getSeatsRemaining()));
        params.put(SerializedNames.DESCRIPTION, mEvent.getDescription());
        params.put(SerializedNames.DATE_DEPART, String.valueOf(mEvent.getDateDepart()));
        params.put(SerializedNames.DATE_ARRIVE, String.valueOf(mEvent.getDateArrive()));
        params.put(SerializedNames.LOCATION_START, mEvent.getLocationStart());
        params.put(SerializedNames.LOCATION_END, mEvent.getLocationEnd());
        
        Log.d("ryan", "PARAMETERS: " + params.toString());
        return params;
    }
}
