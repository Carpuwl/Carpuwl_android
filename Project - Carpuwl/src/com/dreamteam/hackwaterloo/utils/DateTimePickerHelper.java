package com.dreamteam.hackwaterloo.utils;

import android.support.v4.app.FragmentManager;
import android.text.format.Time;

import com.zenkun.datetimepicker.date.DatePickerDialog;
import com.zenkun.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.zenkun.datetimepicker.time.RadialPickerLayout;
import com.zenkun.datetimepicker.time.TimePickerDialog;
import com.zenkun.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

public class DateTimePickerHelper implements OnDateSetListener, OnTimeSetListener {
    
    private static final String TAG_DATE = "tagDatePicker";
    private static final String TAG_TIME = "tagTimePicker";
    
    private int DEFAULT_MINUTE = 0;
    private int DEFAULT_HOUR_OF_DAY= 12; // Noon
    
    public interface OnDateTimeSelectedListener {
        public void onDateTimeSelected(long timeInMillis);
    }
    
    private OnDateTimeSelectedListener mListener;
    private FragmentManager mFragmentManager;
    private Time mTime;
    
    public void setOnDateTimeSelectedListener(OnDateTimeSelectedListener listener) {
        mListener = listener;
    }
    
    public DateTimePickerHelper(FragmentManager supportFragmentManager) {
        mTime = new Time();
        mTime.setToNow();
        mFragmentManager = supportFragmentManager;
    }

    public void show() {
        DatePickerDialog.newInstance(this, mTime.year, mTime.month, mTime.monthDay)
                .show(mFragmentManager, TAG_DATE);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        TimePickerDialog.newInstance(this, DEFAULT_HOUR_OF_DAY, DEFAULT_MINUTE, Utils.is24Hour())
                .show(mFragmentManager, TAG_TIME);
        mTime.year = year;
        mTime.month = month;
        mTime.monthDay = day;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mTime.hour = hourOfDay;
        mTime.minute = minute;
        if (mListener != null) {
            mListener.onDateTimeSelected(getDateTimeInMillis());
        }
    }
    
    public long getDateTimeInMillis() {
        return mTime.toMillis(false);
    }
}
