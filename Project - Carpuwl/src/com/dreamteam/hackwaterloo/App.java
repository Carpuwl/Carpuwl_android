package com.dreamteam.hackwaterloo;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    
    private static App mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }
    
    public static Context context() {
        return mApp.getApplicationContext();
    }
    
    public static int getXmlColor(int colorId) {
        return mApp.getResources().getColor(colorId);
    }
    
    public static String getXmlString(int stringId) {
        return mApp.getResources().getString(stringId);
    }
}
