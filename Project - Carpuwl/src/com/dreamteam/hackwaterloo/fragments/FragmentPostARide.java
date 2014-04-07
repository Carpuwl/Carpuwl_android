package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.Constants.Defaults;
import com.dreamteam.hackwaterloo.Constants.StatusCode;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper.OnDateTimeSelectedListener;
import com.dreamteam.hackwaterloo.utils.TextWatcherPrice;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.dreamteam.hackwaterloo.utils.server.BaseTaskV2.OnPostExecuteListener;
import com.dreamteam.hackwaterloo.utils.server.PostEventTask;

public class FragmentPostARide extends SherlockFragment implements OnClickListener {

    private DateTimePickerHelper mDateTimePickerHelper;

    private LinearLayout mContainerEditPost;
    private LinearLayout mContainerSuccess;
    private Button mButtonSubmit;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private SeekBar mSeekbarSeats;
    private Spinner mSpinnerStart;
    private Spinner mSpinnerEnd;
    private EditText mEditPrice;
    private EditText mEditDescription;
    private TextView mTextSeatsValue;
    private TextView mTextTimeStart;
    private TextView mTextTimeEnd;

    private long mStartTime;
    private long mEndTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_a_ride, container, false);

        initUi(rootView);
        enableSubmitButtonIfDataValid();
        
        return rootView;
    }

    private void initUi(View rootView) {
        mContainerEditPost = (LinearLayout) rootView.findViewById(R.id.post_ride_container_edit_post);
        mContainerSuccess = (LinearLayout) rootView.findViewById(R.id.post_ride_container_successful);
        mButtonSubmit = (Button) rootView.findViewById(R.id.post_ride_button_submit_event);
        mButtonDatePicker = (Button) rootView.findViewById(R.id.post_ride_button_start_date);
        mButtonTimePicker = (Button) rootView.findViewById(R.id.post_ride_button_end_date);
        mSeekbarSeats = (SeekBar) rootView.findViewById(R.id.post_ride_seekbar_seats);
        mSpinnerStart = (Spinner) rootView.findViewById(R.id.post_ride_spinner_depart_from);
        mSpinnerEnd = (Spinner) rootView.findViewById(R.id.post_ride_spinner_arrive_at);
        mEditPrice = (EditText) rootView.findViewById(R.id.post_ride_edittext_price);
        mEditDescription = (EditText) rootView.findViewById(R.id.post_ride_edittext_description);
        mTextSeatsValue = (TextView) rootView.findViewById(R.id.post_ride_text_seats_value);
        mTextTimeStart = (TextView) rootView.findViewById(R.id.post_ride_text_start_date);
        mTextTimeEnd = (TextView) rootView.findViewById(R.id.post_ride_text_end_date);
        
        mButtonDatePicker.setOnClickListener(this);
        mButtonTimePicker.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        
        mSeekbarSeats.setOnSeekBarChangeListener(new SeekbarListener());
        mSeekbarSeats.setProgress(Defaults.MINIMUM_SEATS);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        mSpinnerStart.setAdapter(adapter);
        mSpinnerEnd.setAdapter(adapter);
        mSpinnerStart.setOnItemSelectedListener(new SeekBarButtonEnabler());
        mSpinnerEnd.setOnItemSelectedListener(new SeekBarButtonEnabler());
        
        mEditPrice.addTextChangedListener(new TextWatcherPrice(mEditPrice)); 
        mEditPrice.addTextChangedListener(new TextWatcherPriceButtonEnabler());
    }
    
    private boolean dataIsValid() {
        if (mSpinnerStart.getSelectedItemPosition() == mSpinnerEnd.getSelectedItemPosition()) {
            Log.d("ryan", "failed due to spinners having same position");
        } else if (mSpinnerStart.getSelectedItemPosition() == 0) {
            Log.d("ryan", "failed due to start spinner position == 0");
        } else if (mSpinnerEnd.getSelectedItemPosition() == 0) {
            Log.d("ryan", "failed due to end spinner position == 0");
        } else if (mStartTime > mEndTime) {
            Log.d("ryan", "failed due to start time being later than end time");
        } else if (mStartTime < System.currentTimeMillis()) {
            Log.d("ryan", "failed due to start time being before current time");
        } else if (Utils.getDoubleFromPriceEditText(mEditPrice) <= 1d) {
            Log.d("ryan", "failed due to price being less than one dollar");
        } else if (mTextSeatsValue.getText().toString().equals("0")) {
            Log.d("ryan", "failed due to seats remaining equalling 0");
        } else if (mTextSeatsValue.getText().toString().equals("")) {
            Log.d("ryan", "failed due to seats remaining being empty");
        } else {
            Log.d("ryan", "success");
            return true;
        }
        return false;
        
//        return (
//                mSpinnerStart.getSelectedItemPosition() != mSpinnerEnd.getSelectedItemId() &&
//                mSpinnerStart.getSelectedItemPosition() == 0 &&
//                mSpinnerEnd.getSelectedItemPosition() == 0 &&
//                mStartTime < mEndTime &&
//                mStartTime > System.currentTimeMillis() &&
//                Utils.getDoubleFromPriceEditText(mEditPrice) >= 1d &&
//                !mTextSeatsRemaining.getText().toString().equals("0") &&
//                mTextSeatsRemaining.getText().toString().equals(""));
    }
    
    private void enableSubmitButtonIfDataValid() {
        mButtonSubmit.setEnabled(dataIsValid());
    }
    
    private class TextWatcherPriceButtonEnabler implements TextWatcher {
        
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            enableSubmitButtonIfDataValid();
        }
        
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_ride_button_submit_event:
                Event event = new Event(
                        mSpinnerStart.getSelectedItem().toString(), 
                        mSpinnerEnd.getSelectedItem().toString(), 
                        Utils.getDoubleFromPriceEditText(mEditPrice), 
                        Integer.parseInt(mTextSeatsValue.getText().toString()),
                        mStartTime, 
                        mEndTime,
                        AppData.getFacebookForeginKey(), 
                        mEditDescription.getText().toString());
                PostEventTask postEventTask = new PostEventTask(getActivity(), event);
                postEventTask.setOnPostExecuteListener(new OnPostExecuteListener<Integer>() {
                    @Override
                    public void onFinish(Integer httpStatusCode) {
                        if (httpStatusCode == StatusCode.OK) {
                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                            new CrossFadeViewSwitcher(mContainerEditPost, mContainerSuccess, true).startAnimation();
                        }
                    }
                });
                postEventTask.executeParallel();
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
            mTextSeatsValue.setText(String.valueOf(progress));
            enableSubmitButtonIfDataValid();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mTextSeatsValue.getText().toString().equals("0")) {
                seekBar.setProgress(Defaults.MINIMUM_SEATS);
            }
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
                    mTextTimeStart.setText(Utils.multiCaseDateFormat(timeInMillis));
                    mStartTime = timeInMillis;
                    break;

                case R.id.post_ride_button_end_date:
                    mTextTimeEnd.setText(Utils.multiCaseDateFormat(timeInMillis));
                    mEndTime = timeInMillis;
                    break;

                default:
                    assert false : "Unhandled datepicker for view with id " + mButtonId;
            }
            enableSubmitButtonIfDataValid();
        }
    }
    
    private class SeekBarButtonEnabler implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            enableSubmitButtonIfDataValid();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            enableSubmitButtonIfDataValid();
        }
    }
}
