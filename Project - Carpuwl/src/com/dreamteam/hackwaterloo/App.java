package com.dreamteam.hackwaterloo;

import android.app.Application;
import android.content.Context;

import com.dreamteam.hackwaterloo.volley.MyVolley;

public class App extends Application {
    
    private static App mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        MyVolley.init(getAppContext());
    }
    
    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }
}
