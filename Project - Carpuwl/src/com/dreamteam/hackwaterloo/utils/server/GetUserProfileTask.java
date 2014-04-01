package com.dreamteam.hackwaterloo.utils.server;

import android.os.Bundle;
import android.util.Log;

import com.dreamteam.hackwaterloo.adapters.Feed;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.NetworkHelper;
import com.google.gson.Gson;

public class GetUserProfileTask extends BaseTask <Void, Void, Bundle> {
    
    public static final String ENDPOINT = "get_user_data";

    @Override
    protected Bundle doInBackground(Void... arg0) {
        Gson gson = new Gson();
        String jsonResult = NetworkHelper.get(ENDPOINT);
        Log.i("ryan", jsonResult);
        Event[] event = gson.fromJson(jsonResult, Feed.class).getEvents();
        return null;
    }

}
