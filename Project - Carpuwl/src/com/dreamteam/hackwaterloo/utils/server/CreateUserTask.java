package com.dreamteam.hackwaterloo.utils.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.dreamteam.hackwaterloo.utils.NetworkHelper;

public class CreateUserTask extends BaseTask<Void, Void, String> {
    
    private static final String TAG = CreateUserTask.class.getSimpleName();
    private static final String ENDPOINT = "create_user";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_FB_KEY = "fb_fk";
    
    private final String mUserName;
    private final String mPhone;
    private final String facebookPrivateKey;
    
    public CreateUserTask(String userName, String mPhone, String facebookPrivateKey) {
        mUserName = userName;
        this.mPhone = mPhone;
        this.facebookPrivateKey = facebookPrivateKey;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>(3);
        requestParams.add(new BasicNameValuePair(PARAMETER_NAME, mUserName));
        requestParams.add(new BasicNameValuePair(PARAMETER_PHONE, mPhone));
        requestParams.add(new BasicNameValuePair(PARAMETER_FB_KEY, facebookPrivateKey));
        
        String jsonResult = NetworkHelper.post(ENDPOINT, requestParams);
        Log.i(TAG, "[create user task]: " + jsonResult);
        
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
