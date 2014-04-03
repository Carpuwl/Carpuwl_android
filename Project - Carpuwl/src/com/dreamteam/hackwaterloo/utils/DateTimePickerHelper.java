package com.dreamteam.hackwaterloo.utils;

import java.util.Calendar;

import android.support.v4.app.FragmentManager;

import com.dreamteam.hackwaterloo.Constants.FragmentTag;
import com.zenkun.datetimepicker.date.DatePickerDialog;
import com.zenkun.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.zenkun.datetimepicker.time.RadialPickerLayout;
import com.zenkun.datetimepicker.time.TimePickerDialog;
import com.zenkun.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

public class DateTimePickerHelper implements OnDateSetListener, OnTimeSetListener {
    
    public interface OnDateTimeSelectedListener {
        public void onDateTimeSelected(long timeInMillis);
    }
    
    private OnDateTimeSelectedListener mListener;
    
    private FragmentManager mFragmentManager;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    
    public void setOnDateTimeSelectedListener(OnDateTimeSelectedListener listener) {
        mListener = listener;
    }
    
    public DateTimePickerHelper(FragmentManager supportFragmentManager) {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        mFragmentManager = supportFragmentManager;
    }

    public void show() {
        DatePickerDialog.newInstance(this, mYear, mMonth, mDay)
                .show(mFragmentManager, FragmentTag.DIALOG_DATE_PICKER);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        TimePickerDialog.newInstance(this, mHour, mMinute, true)
                .show(mFragmentManager, FragmentTag.DIALOG_TIME_PICKER);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (mListener != null) {
            mListener.onDateTimeSelected(getDateTimeInMillis());
        }
    }
    
    public long getDateTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DAY_OF_MONTH, mDay);
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTimeInMillis();
    }
}
