package com.dreamteam.hackwaterloo.models;

public final class NetworkResponse {
    
    private static final int UNSET_STATUS_CODE = -1;
    private static final String UNSET_JSON_STRING = "com.dreamteam.hackwaterloo.unsetJson";
    
    private int mHttpStatusCode;
    private String mJsonResult;
    
    public NetworkResponse() {
        mHttpStatusCode = UNSET_STATUS_CODE;
        mJsonResult = UNSET_JSON_STRING;
    }
    
    public void setStatusCode(int httpStatusCode) {
        mHttpStatusCode = httpStatusCode;
    }
    
    public void setJsonResult(String jsonResult) {
        mJsonResult = jsonResult;
    }
    
    public int getStatusCode() {
        return mHttpStatusCode;
    }
    
    public String getJsonResult() {
        return mJsonResult;
    }

    @Override
    public String toString() {
        return "[HTTP Status Code : " + String.valueOf(mHttpStatusCode) + "] + [JSON : " + mJsonResult + "]";
    }
    
    
}
