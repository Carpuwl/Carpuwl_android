package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper.OnDateTimeSelectedListener;
import com.dreamteam.hackwaterloo.utils.TextWatcherPrice;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.dreamteam.hackwaterloo.utils.server.CreateEventTask;

public class FragmentPostARide extends SherlockFragment implements OnClickListener {

    private DateTimePickerHelper mDateTimePickerHelper;

    private Button mButtonSubmit;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private SeekBar mSeekbarSeats;
    private Spinner mSpinnerStart;
    private Spinner mSpinnerEnd;
    private EditText mEditPrice;
    private EditText mEditSeatsRemaining;
    private EditText mEditDescription;

    private long mStartTime;
    private long mEndTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_a_ride, container, false);

        initUi(rootView);
        return rootView;
    }

    private void initUi(View context) {
        mButtonSubmit = (Button) context.findViewById(R.id.post_ride_button_submit_event);
        mButtonDatePicker = (Button) context.findViewById(R.id.post_ride_button_start_date);
        mButtonTimePicker = (Button) context.findViewById(R.id.post_ride_button_end_date);
        mSeekbarSeats = (SeekBar) context.findViewById(R.id.post_ride_seekbar_seats);
        mSpinnerStart = (Spinner) context.findViewById(R.id.post_ride_spinner_depart_from);
        mSpinnerEnd = (Spinner) context.findViewById(R.id.post_ride_spinner_arrive_at);
        mEditPrice = (EditText) context.findViewById(R.id.post_ride_edittext_price);
        mEditSeatsRemaining = (EditText) context.findViewById(R.id.post_ride_edittext_seats);
        mEditDescription = (EditText) context.findViewById(R.id.post_ride_edittext_description);
        
        mButtonDatePicker.setOnClickListener(this);
        mButtonTimePicker.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerStart.setAdapter(adapter);
        mSpinnerEnd.setAdapter(adapter);
        
        mSeekbarSeats.setOnSeekBarChangeListener(new SeekbarListener());
        
        mEditPrice.addTextChangedListener(new TextWatcherPrice(mEditPrice));        
    }
    
    private boolean dataIsValid() {
        return (
                mSpinnerStart.getSelectedItemPosition() != mSpinnerEnd.getSelectedItemPosition() && 
                mSpinnerStart.getSelectedItemPosition() != 0 &&
                mSpinnerEnd.getSelectedItemPosition() != 0 &&
                mStartTime < mEndTime &&
                Utils.getFloatFromPriceEditText(mEditPrice) != 0d &&
                !mEditSeatsRemaining.getText().toString().equals("0"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_ride_button_submit_event:
                if (dataIsValid()) {
                    Event event = new Event(
                            mSpinnerStart.getSelectedItem().toString(), 
                            mSpinnerEnd
                            .getSelectedItem().toString(), 
                            Float.valueOf(mEditPrice.getText().toString()), 
                            Integer.valueOf(mEditSeatsRemaining.getText().toString()),
                            mStartTime, 
                            mEndTime,
                            AppData.getFacebookForeginKey(), 
                            mEditDescription.getText().toString());
                    new CreateEventTask(event).executeParallel();
                }
                break;

            case R.id.post_ride_button_start_date:
            case R.id.post_ride_button_end_date:
                mDateTimePickerHelper = new DateTimePickerHelper(getActivity()
                        .getSupportFragmentManager());
                mDateTimePickerHelper.setOnDateTimeSelectedListener(
                        new DateTimePickerListener(view.getId()));
                mDateTimePickerHelper.show();
                break;
        }
    }
    
    private class SeekbarListener implements OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mEditSeatsRemaining.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
        
    }
    
    private class DateTimePickerListener implements OnDateTimeSelectedListener {

        int mButtonId;

        public DateTimePickerListener(int buttonId) {
            mButtonId = buttonId;
        }

        @Override
        public void onDateTimeSelected(long timeInMillis) {
            switch (mButtonId) {
                case R.id.post_ride_button_start_date:
                    mStartTime = timeInMillis;
                    break;

                case R.id.post_ride_button_end_date:
                    mEndTime = timeInMillis;
                    break;

                default:
                    assert false : "Unhandled datepicker for view with id " + mButtonId;
            }
        }
    }
}
