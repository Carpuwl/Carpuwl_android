
package com.dreamteam.hackwaterloo.fragments;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.common.Constants.Defaults;
import com.dreamteam.hackwaterloo.common.Constants.Endpoint;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.models.Feed.SerializedNames;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper.OnDateTimeSelectedListener;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.dreamteam.hackwaterloo.volley.GsonRequest;
import com.dreamteam.hackwaterloo.volley.MyVolley;

public class FragmentPostARide extends SherlockFragment implements OnClickListener {

    public static final String TAG = FragmentPostARide.class.getSimpleName();
    private static final String KEY_DONE_POSTING = "keyDonePosting";

    private DateTimePickerHelper mDateTimePickerHelper;

    private ViewStub mViewStubPosting;
    private ViewStub mViewStubSuccess;
    private LinearLayout mContainerEditPost;
    private LinearLayout mContainerSuccess;
    private Button mButtonSubmit;
    private Button mButtonDatePicker;
    private Button mButtonTimePicker;
    private Button mButtonViewPost;
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
    private boolean mDonePosting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_a_ride, container, false);

        mViewStubSuccess = (ViewStub) rootView.findViewById(R.id.post_ride_viewstub_success);
        if (savedInstanceState != null && savedInstanceState.getBoolean(KEY_DONE_POSTING, false)) {
            initSuccessPost();
            mDonePosting = true;
        } else {
            mEditPost(rootView);
            enableSubmitButtonIfDataValid();
        }

        return rootView;
    }
    
    private void initSuccessPost() {
        mContainerSuccess = (LinearLayout) mViewStubSuccess.inflate();
        mButtonViewPost = (Button) mContainerSuccess
                .findViewById(R.id.post_success_button_view_post);
        
        mButtonViewPost.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    private void mEditPost(View rootView) {
        mViewStubPosting = (ViewStub) rootView.findViewById(R.id.post_ride_viewstub_posting);
        mContainerEditPost = (LinearLayout) mViewStubPosting.inflate();
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
                R.array.cities, R.layout.spinner_selected_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSpinnerStart.setAdapter(adapter);
        mSpinnerEnd.setAdapter(adapter);
        mSpinnerStart.setOnItemSelectedListener(new SeekBarButtonEnabler());
        mSpinnerEnd.setOnItemSelectedListener(new SeekBarButtonEnabler());

        mEditPrice.addTextChangedListener(new TextWatcherPriceButtonEnabler());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_DONE_POSTING, mDonePosting);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
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
        } else if (Integer.parseInt(mEditPrice.getText().toString()) < Defaults.MINIMUM_PRICE) {
            Log.d("ryan", "failed due to price being less than one dollar");
        } else if (mTextSeatsValue.getText().toString().equals("0")) {
            Log.d("ryan", "failed due to seats remaining equalling 0");
        } else {
            Log.d("ryan", "success");
            return true;
        }
        return false;
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
                postRequest();
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

    private void postRequest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("fb_fk", String.valueOf(AppData.getFacebookForeginKey())); // TODO: wtf?
        params.put(SerializedNames.PRICE, mEditPrice.getText().toString());
        params.put(SerializedNames.SEATS_REMAINING, mTextSeatsValue.getText().toString());
        params.put(SerializedNames.DESCRIPTION, mEditDescription.getText().toString());
        params.put(SerializedNames.DATE_DEPART, String.valueOf(mStartTime));
        params.put(SerializedNames.DATE_ARRIVE, String.valueOf(mEndTime));
        params.put(SerializedNames.LOCATION_START, mSpinnerStart.getSelectedItem().toString());
        params.put(SerializedNames.LOCATION_END, mSpinnerEnd.getSelectedItem().toString());
        
        Log.d("ryan", "PARAMETERS: " + params.toString());
        
        GsonRequest<Event> request = new GsonRequest<Event>(
                Method.POST, 
                Endpoint.EVENT, 
                Event.class, 
                new Listener<Event>() {
                    @Override
                    public void onResponse(Event response) {
                        Log.d("ryan", response.toString());
                        Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                        initSuccessPost();
                        new CrossFadeViewSwitcher(mContainerEditPost, mContainerSuccess, true)
                                .startAnimation();
                        mDonePosting = true;
                    }
                }, 
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Could not post", Toast.LENGTH_SHORT).show();
                        Log.d("ryan", error.toString());
                    }
                },
                params);

        MyVolley.getRequestQueue().add(request);
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
