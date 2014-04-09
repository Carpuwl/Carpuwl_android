package com.dreamteam.hackwaterloo.utils.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.utils.NetworkHelper;

public class GetMyProfileDataTask extends BaseTask <Void, Void, String> {
    
//    private static final String TAG = GetMyProfileDataTask.class.getSimpleName();
    public static final String ENDPOINT = "user";
    private static final String PARAMETER_NAME = "fb_fk";
    
    @Override
    protected String doInBackground(Void... params) {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>(1);
        requestParams.add(new BasicNameValuePair(PARAMETER_NAME, String.valueOf(AppData.getFacebookForeginKey())));
        String jsonResult = NetworkHelper.get(ENDPOINT, requestParams);
        return jsonResult;
    }

    @Override
    protected void onPostExecute(String result) {
        if (onPostExecuteListener != null) {
            onPostExecuteListener.onFinish(result);
        }
        super.onPostExecute(result);
    }
    
}
