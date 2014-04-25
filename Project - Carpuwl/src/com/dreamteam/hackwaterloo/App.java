
package com.dreamteam.hackwaterloo;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.dreamteam.hackwaterloo.utils.Utils;
import com.dreamteam.hackwaterloo.volley.MyVolley;

/**
 * A singleton for all objects that must be instantiated at Application start
 * time and require a context
 * 
 * @author Ryan
 */
@SuppressLint("SimpleDateFormat")
public class App extends Application {

    private static App mApp = null;
    private static SimpleDateFormat mDateFormat;
    private static SimpleDateFormat mDateFormatVerbose;
    private static SimpleDateFormat mTimeFormat;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        MyVolley.init(getAppContext());
        mDateFormat = new SimpleDateFormat(Utils.detectDateFormat(false));
        mDateFormatVerbose = new SimpleDateFormat(Utils.detectDateFormat(true));
        mTimeFormat = new SimpleDateFormat(Utils.detectTimeFormat());
    }

    /**
     * Nice little utility that allows you to grab an APPLICATION context from
     * anywhere within the Application. Utils.getAppContext(). Be very careful
     * that you need the application context and not an activity context.
     * 
     * @return ApplicationContext
     */
    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    /**
     * @return The SimpleDateFormat representation of the Date Format as defined
     *         in the System's formatting
     */
    public static SimpleDateFormat getDateFormat() {
        return mDateFormat;
    }

    /**
     * @return The SimpleDateFormat representation of the Date Format (with
     *         year) as defined in the System's Date Formatting
     */
    public static SimpleDateFormat getDateFormatVerbose() {
        return mDateFormatVerbose;
    }

    /**
     * @return The SimpleDateFormat representation of the 12 or 24 hour time
     *         format, as defined in the System's time formatting
     */
    public static SimpleDateFormat getTimeFormat() {
        return mTimeFormat;
    }
}
