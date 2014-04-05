package com.dreamteam.hackwaterloo.utils.server;

import com.dreamteam.hackwaterloo.adapters.Feed;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.NetworkHelper;
import com.google.gson.Gson;

public class GetEventsTask extends BaseTask <Void, Void, Event[]> {
    
    private String ENDPOINT = "feed";

    @Override
    protected Event[] doInBackground(Void... arg0) {
        Gson gson = new Gson();
        String jsonResult = NetworkHelper.get(ENDPOINT);
        Event[] events = gson.fromJson(jsonResult, Feed.class).getEvents();
        
        return events;
    }

    @Override
    protected void onPostExecute(Event[] result) {
        if (onPostExecuteListener != null) {
            onPostExecuteListener.onFinish(result);
        }
        super.onPostExecute(result);
    }
    
}
