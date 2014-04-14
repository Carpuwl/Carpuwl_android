
package com.dreamteam.hackwaterloo;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.format.DateFormat;

import com.dreamteam.hackwaterloo.utils.Utils;
import com.dreamteam.hackwaterloo.volley.MyVolley;

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
        mDateFormat = new SimpleDateFormat(Utils.detectDateFormat(getAppContext(), false));
        mDateFormatVerbose = new SimpleDateFormat(Utils.detectDateFormat(getAppContext(), true));
        mTimeFormat = new SimpleDateFormat(Utils.detectTimeFormat(getAppContext()));
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

    /**
     * @return True if the time format is 24 hour, (Set in the Android's System
     *         Clock settings)
     */
}
