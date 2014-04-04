package com.dreamteam.hackwaterloo;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class App extends Application {
    
    private static App mApp = null;
    private static Resources mResources;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mResources = mApp.getResources();
    }
    
    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }
}
