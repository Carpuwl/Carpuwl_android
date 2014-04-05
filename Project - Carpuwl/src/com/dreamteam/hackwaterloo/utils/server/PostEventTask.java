package com.dreamteam.hackwaterloo.utils.server;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.content.Context;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.models.NetworkResponse;
import com.dreamteam.hackwaterloo.utils.NetworkHelperV2;
import com.dreamteam.hackwaterloo.utils.Utils;

public class PostEventTask extends BaseTaskV2 <Void, Void, NetworkResponse, Integer> {
    
    private static final String ENDPOINT = "create_event";
    
    private WeakReference<Context> mContext;
    private Dialog dialog;
    private Event mEvent;
    
    public PostEventTask(Context context, Event event) {
        mContext = new WeakReference<Context>(context);
        this.mEvent = event;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        dialog = Utils.customProgressDialog(mContext.get(), R.string.loading);
        dialog.show();
    }

    @Override
    protected NetworkResponse doInBackground(Void... arg0) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("start_point", mEvent.getStartPoint()));
        params.add(new BasicNameValuePair("end_point", mEvent.getEndPoint()));
        params.add(new BasicNameValuePair("price", String.valueOf(mEvent.getPrice())));
        params.add(new BasicNameValuePair("seats_rem", String.valueOf(mEvent.getSeatsRemaining())));
        params.add(new BasicNameValuePair("depart_date", String.valueOf(mEvent.getDepartDate())));
        params.add(new BasicNameValuePair("eta", String.valueOf(mEvent.getArrivalTime())));
        params.add(new BasicNameValuePair("fb_fk", String.valueOf(AppData.getFacebookForeginKey())));
        params.add(new BasicNameValuePair("description", mEvent.getDescription()));
        
        return NetworkHelperV2.post(ENDPOINT, params);
    }

    @Override
    protected void onPostExecute(NetworkResponse result) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        
        if (onPostExecuteListener != null) {
            onPostExecuteListener.onFinish(result.getStatusCode());
        }
        super.onPostExecute(result);
    }
    
    
}
