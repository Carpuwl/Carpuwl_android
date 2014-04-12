package com.dreamteam.hackwaterloo;

import com.dreamteam.hackwaterloo.volley.MyVolley;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    
    private static App mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MyVolley.init(this);
        mApp = this;
    }
    
    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }
}
