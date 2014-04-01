package com.dreamteam.hackwaterloo.utils.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.dreamteam.hackwaterloo.utils.NetworkHelper;

public class GetUserDataTask extends BaseTask <Void, Void, String> {
    
    private static final String TAG = GetUserDataTask.class.getSimpleName();
    public static final String ENDPOINT = "get_user_data";
    private static final String PARAMETER_NAME = "name";
    
    private String mUserName;
    
    public GetUserDataTask(String userName) {
        mUserName = userName;
    }

    @Override
    protected String doInBackground(Void... params) {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>(1);
        requestParams.add(new BasicNameValuePair(PARAMETER_NAME, mUserName));
        
        String jsonResult = NetworkHelper.get(ENDPOINT, requestParams);
        Log.i(TAG, "[json]: " + jsonResult);
        
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
