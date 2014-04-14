package com.dreamteam.hackwaterloo.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.App;

public class Utils {

    private static final String SDF_DAY_OF_WEEK = "EEEE";

    /**
     * @param quantityStringId
     *            The desired resource identifier
     * @param quantity
     *            The number used to get the correct string for the current
     *            language's plural rules.
     * @return The string data associated with the resource, stripped of styled
     *         text information.
     */
    public static String getQuantityString(int quantityStringId, int quantity) {
        return App.getAppContext().getResources().getQuantityString(quantityStringId, quantity);
    }
    
    /**
     * @param stringArrayId The desired resource identifier
     * @return The string array associated with the resource. 
     */
    public static String[] getStringArray(int stringArrayId) {
        return App.getAppContext().getResources().getStringArray(stringArrayId);
    }

    /**
     * @param stringId
     *            The desired resource identifier
     * @return The string data associated with the resource, stripped of styled
     *         text information.
     */
    public static String getString(int stringId) {
        return App.getAppContext().getResources().getString(stringId);
    }

    /**
     * @param colorId
     *            The desired resource identifier
     * @return Returns a single color value in the form 0xAARRGGBB.
     */
    public static int getColor(int colorId) {
        return App.getAppContext().getResources().getColor(colorId);
    }
    
    @SuppressLint("Recycle")
    public static TypedArray obtainTypedArray(int typedArrayId) {
        return App.getAppContext().getResources().obtainTypedArray(typedArrayId);
    }
    
    public static Drawable getDrawable(int drawableId) {
        return App.getAppContext().getResources().getDrawable(drawableId);
    }


    // TODO: convert to DialogFragment instead
    public static Dialog customProgressDialog(Context context, int messageId) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_loading_text_message);
        textView.setText(messageId);
        return dialog;
    }
    
    /**
     * Helper method to grab the date format from the system
     * @param context A context for the App to grab the System's date formatting
     * @return String to be used for SimpleDateFormat
     */
    public static String detectDateFormat(Context context, boolean includeYear) {
        Calendar testDate = Calendar.getInstance();
        testDate.set(Calendar.YEAR, 2013);
        testDate.set(Calendar.MONTH, Calendar.DECEMBER);
        testDate.set(Calendar.DAY_OF_MONTH, 25);

        Format format = android.text.format.DateFormat.getDateFormat(context);
        String testDateFormat = format.format(testDate.getTime());
        String[] parts = testDateFormat.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : parts) {
            if (s.equals("25")) {
                stringBuilder.append("dd ");
            }
            if (s.equals("12")) {
                stringBuilder.append("MMM ");
            }
            if (s.equals("2013") && includeYear) {
                stringBuilder.append("yyyy ");
            }
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
    }
    
    /**
     * Helper method to grab the User's chosen time formatting (from the system)
     * @param context A context for the for the App to grab the System's time formatting
     * @return String representing the time format as a SimpleDateFormat
     */
    public static String detectTimeFormat(Context context) {
        return DateFormat.is24HourFormat(context) ? "HH:mm" : "hh:mm a";
    }

    // TODO: grab the 12/24hr styling from the System on startup
    /**
     * Formats the given time into the appropriate format as a string
     * 
     * @param epochTime
     *            The time to compare against today
     * @return The appropriate formatted date as a string
     */
    @SuppressLint("SimpleDateFormat")
    public static String multiCaseDateFormat(long epochTime) {
        boolean verboseTimeStampNeeded = true;
        Time currentTime = new Time();
        Time eventTime = new Time();
        StringBuilder stringBuilder = new StringBuilder();

        currentTime.set(System.currentTimeMillis());
        eventTime.set(epochTime);

        // Same year
        if (currentTime.year == eventTime.year) {

            // Same hour
            if (currentTime.yearDay == eventTime.yearDay && eventTime.hour <= currentTime.hour + 1) {

                verboseTimeStampNeeded = false;
                stringBuilder.append(eventTime.minute - currentTime.minute)
                        .append(" ")
                        .append(Utils.getQuantityString(R.plurals.time_unit_minute,
                                eventTime.minute - currentTime.minute));

            // Same day
            } else if (currentTime.yearDay == eventTime.yearDay) {
                stringBuilder.append(Utils.getString(R.string.today));

            // Tomorrow
            } else if (currentTime.yearDay == eventTime.yearDay - 1) {
                stringBuilder.append(Utils.getString(R.string.tomorrow));

            // Within a week
            } else if (eventTime.yearDay <= currentTime.yearDay + 6) {
                stringBuilder.append(new SimpleDateFormat(SDF_DAY_OF_WEEK).format(new Date(
                        eventTime.toMillis(false))));

            // Same year
            } else {
                stringBuilder.append(App.getDateFormat()
                        .format(new Date(eventTime.toMillis(false))));
            }

        // Different year
        } else {
            stringBuilder.append(App.getDateFormatVerbose().format(
                    new Date(eventTime.toMillis(false))));
        }

        if (verboseTimeStampNeeded) {
            stringBuilder.append(" ")
                    .append(Utils.getString(R.string.at))
                    .append(" ")
                    .append(App.getTimeFormat().format(new Date(eventTime.toMillis(false))));
        }

        return stringBuilder.toString();
    }
    
    /**
     * @return True if the time format is 24 hour, (Set in the Android's System
     *         Clock settings)
     */
    public static boolean is24Hour() {
        return DateFormat.is24HourFormat(App.getAppContext());
    }
    
    public static double getDoubleFromPriceEditText(EditText editText) {
        String contentString = editText.getText().toString().replaceAll("[^\\d.]", "");
        return TextUtils.isEmpty(contentString) ? 0d : Double.parseDouble(contentString);
    }
    
    public static boolean supportsSdk(int sdkLevel) {
        return  (Build.VERSION.SDK_INT >= sdkLevel); 
    }
}
