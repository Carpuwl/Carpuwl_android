package com.dreamteam.hackwaterloo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.format.Time;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.App;

public class Utils {

    private static final String DEFAULT_DATE_FORMAT = "hh':'mm a";
    private static final String SDF_DAY_OF_WEEK = "EEE";
    private static final String SDF_MONTH_AND_DAY = "MM dd";
    private static final String SDF_VERBOSE = "yyyy MM dd";

    /**
     * Convenience method for grabbing plural strings defined in the xml
     * @param quantityStringId The desired resource identifier
     * @param quantity The number used to get the correct string for the current language's plural rules.
     * @return The string data associated with the resource, stripped of styled text information. 
     */
    public static String getQuantityString(int quantityStringId, int quantity) {
        return App.getAppContext().getResources().getQuantityString(quantityStringId, quantity);
    }

    /**
     * @param stringId The desired resource identifier
     * @return The string data associated with the resource, stripped of styled text information.
     */
    public static String getString(int stringId) {
        return App.getAppContext().getResources().getString(stringId);
    }

    /**
     * @param colorId The desired resource identifier
     * @return Returns a single color value in the form 0xAARRGGBB. 
     */
    public static int getColor(int colorId) {
        return App.getAppContext().getResources().getColor(colorId);
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

    // TODO: grab the styling from the System
    /**
     * Formats the given time into the appropriate format as a string
     * @param epochTime The time to compare against today
     * @return The appropriate formatted date as a string
     */
    @SuppressLint("SimpleDateFormat")
    public static String multiCaseDateFormat(long epochTime) {
        Time currentTime = new Time();
        Time eventTime = new Time();
        StringBuilder stringBuilder = new StringBuilder();

        currentTime.set(System.currentTimeMillis());
        eventTime.set(epochTime);

        String formattedEventTime = new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date(
                eventTime.toMillis(false)));

        // Same year
        if (currentTime.year == eventTime.year) {

            // Same day
            if (currentTime.yearDay == eventTime.yearDay && currentTime.hour == eventTime.hour) {

                stringBuilder.append(eventTime.minute - currentTime.minute).append(" ");
                stringBuilder.append(Utils.getQuantityString(R.plurals.time_unit_minute,
                        eventTime.minute - currentTime.minute));

                // Same day
            } else if (currentTime.hour == eventTime.hour) {
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
                stringBuilder.append(new SimpleDateFormat(SDF_MONTH_AND_DAY).format(new Date(
                        eventTime.toMillis(false))));
            }

            // Different year
        } else {
            stringBuilder.append(new SimpleDateFormat(SDF_VERBOSE).format(new Date(eventTime
                    .toMillis(false))));
        }

        stringBuilder
                .append(" ")
                .append(Utils.getString(R.string.at))
                .append(" ")
                .append(formattedEventTime);

        return stringBuilder.toString();
    }

    public static float getFloatFromPriceEditText(EditText editText) {
        String stringcontext = editText.getText().toString();
        stringcontext.replaceAll("$", "");
        return Float.parseFloat(stringcontext);
    }
}
