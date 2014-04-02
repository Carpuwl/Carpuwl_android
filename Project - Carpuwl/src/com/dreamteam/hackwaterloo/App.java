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
    
    public static Context context() {
        return mApp.getApplicationContext();
    }
    
    public static String getXmlString(int stringId) {
        return mResources.getString(stringId);
    }
    
    public static int getXmlColor(int colorId) {
        return mResources.getColor(colorId);
    }
}
