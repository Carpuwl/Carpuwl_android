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
    private final int mfacebook_key;
    
    public CreateUserTask(String userName, String mPhone, int mfacebook_key) {
        mUserName = userName;
        this.mPhone = mPhone;
        this.mfacebook_key = mfacebook_key;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>(1);
        requestParams.add(new BasicNameValuePair(PARAMETER_NAME, mUserName));
        requestParams.add(new BasicNameValuePair(PARAMETER_PHONE, mPhone));
        requestParams.add(new BasicNameValuePair(PARAMETER_FB_KEY, Integer.toString(mfacebook_key)));
        
        String jsonResult = NetworkHelper.post(ENDPOINT, requestParams);
        Log.i(TAG, "[create User Task]: " + jsonResult);
        
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
