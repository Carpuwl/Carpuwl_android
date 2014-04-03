package com.dreamteam.hackwaterloo.fragments;

import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.Constants.FragmentTag;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.server.CreateEventTask;
import com.zenkun.datetimepicker.date.DatePickerDialog;
import com.zenkun.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.zenkun.datetimepicker.time.TimePickerDialog;

public class FragmentPostARide extends SherlockFragment implements OnClickListener {

    private Button mButtonSubmit;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private DatePickerDialog mDatePicker;
    private TimePickerDialog mTimePicker;
    private Spinner mSpinnerStart;
    private Spinner mSpinnerEnd;
    private EditText mEditPrice;
    private EditText mEditSeatsRemaining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_a_ride, container, false);
        
        initUi(rootView);
        
        mButtonDatePicker.setOnClickListener(this);
        mButtonTimePicker.setOnClickListener(this);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        mSpinnerStart.setAdapter(adapter);
        mSpinnerEnd.setAdapter(adapter);
        
        mButtonSubmit.setOnClickListener(this);

        return rootView;
    }
    
    private void initUi(View context) {
        mButtonSubmit = (Button) context.findViewById(R.id.post_ride_button_submit_event);
        mButtonDatePicker = (Button) context.findViewById(R.id.post_ride_button_start_date);
        mButtonTimePicker = (Button) context.findViewById(R.id.post_ride_button_end_date);
        mSpinnerStart = (Spinner) context.findViewById(R.id.post_ride_spinner_depart_from);
        mSpinnerEnd = (Spinner) context.findViewById(R.id.post_ride_spinner_arrive_at);
        mEditPrice = (EditText) context.findViewById(R.id.post_ride_edittext_price);
        mEditSeatsRemaining = (EditText) context.findViewById(R.id.post_ride_edittext_seats);
        
        Calendar calendar = Calendar.getInstance();
        
        mDatePicker = DatePickerDialog.newInstance(new OnDateSetListener() {
            
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                Log.d("ryan", "date selected: " + year + " " + month + " " + day);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_ride_button_submit_event:
                Event event = new Event(
                        mSpinnerStart.getSelectedItem().toString(), 
                        mSpinnerEnd.getSelectedItem().toString(), 
                        Float.valueOf(mEditPrice.getText().toString()), 
                        Integer.valueOf(mEditSeatsRemaining.getText().toString()), 
                        System.currentTimeMillis(),
                        System.currentTimeMillis(), 
                        AppData.getFacebookForeginKey(),
                        "This is a description");
                new CreateEventTask(event).executeParallel();
                
            case R.id.post_ride_button_start_date:
                mDatePicker.show(getActivity().getSupportFragmentManager(), FragmentTag.DIALOG_DATE_PICKER);
        }
    }
    
    
    
}
