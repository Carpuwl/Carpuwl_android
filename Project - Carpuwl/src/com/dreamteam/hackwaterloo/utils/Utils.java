package com.dreamteam.hackwaterloo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;

public class Utils {
    
    public static Dialog customProgressDialog (Context context, int messageId) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_loading_text_message);
        textView.setText(messageId);
        return dialog;
    }
    
    public static String multiCaseDateFormat(long epochTime) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(epochTime);
        int eventYear = calendar.get(Calendar.YEAR);
        int eventDay = calendar.get(Calendar.DAY_OF_YEAR);
        
        long timeRemaining = ((epochTime - System.currentTimeMillis()) / 1000L); 
        
        if ((epochTime - System.currentTimeMillis()) / 1000L < 3600) {
            return String.format("%s min", timeRemaining / 60);
        } else if (currentYear == eventYear && currentDay == eventDay) {
            return String.format("%s hr, %s min", timeRemaining / 3600, timeRemaining % 60);
        } else if (currentYear == eventYear && (eventDay - currentDay) <= 6) {
            return String.format("%s day(s), %s hr", eventDay - currentDay, (timeRemaining % 3600));
        } else if (currentYear == eventYear + 1 && currentDay - (365 - eventDay) <= 6) {
            return String.format("%s day(s), %s hr", eventDay - currentDay, (timeRemaining % 3600));
        } else {
            return new SimpleDateFormat("MMM dd, h a").format(epochTime); 
        }
    }
}
