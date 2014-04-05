package com.dreamteam.hackwaterloo.utils.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.NetworkHelper;

public class PostEventTask extends BaseTask <Void, Void, String> {
    
    private static final String ENDPOINT = "create_event";
    
    private Event mEvent;
    
    public PostEventTask(Event event) {
        this.mEvent = event;
    }
    
    
    @Override
    protected String doInBackground(Void... arg0) {
        String jsonResult;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("start_point", mEvent.getStartPoint()));
        params.add(new BasicNameValuePair("end_point", mEvent.getEndPoint()));
        params.add(new BasicNameValuePair("price", String.valueOf(mEvent.getPrice())));
        params.add(new BasicNameValuePair("seats_rem", String.valueOf(mEvent.getSeatsRemaining())));
        params.add(new BasicNameValuePair("depart_date", String.valueOf(mEvent.getDepartDate())));
        params.add(new BasicNameValuePair("eta", String.valueOf(mEvent.getArrivalTime())));
        params.add(new BasicNameValuePair("fb_fk", String.valueOf(AppData.getFacebookForeginKey())));
        params.add(new BasicNameValuePair("description", mEvent.getDescription()));
        
        jsonResult = NetworkHelper.post(ENDPOINT, params);
        
        return jsonResult;
    }
}
