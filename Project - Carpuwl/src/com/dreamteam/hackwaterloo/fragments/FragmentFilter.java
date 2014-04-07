package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.Constants.Defaults;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper.OnDateTimeSelectedListener;
import com.dreamteam.hackwaterloo.utils.TextWatcherPrice;
import com.dreamteam.hackwaterloo.utils.Utils;

public class FragmentFilter extends SherlockFragment implements OnClickListener {

    public static final String FRAGMENT_TAG = FragmentFilter.class.getSimpleName();

    private DateTimePickerHelper mDateTimePickerHelper;

    private Spinner mSpinnerDepart;
    private Spinner mSpinnerArrive;
    private Spinner mSpinnerWhen;
    private EditText mEditPrice;
    private SeekBar mSeekBarSeats;
    private Button mButtonWhen;
    private Button mButtonApply;
    private TextView mTextSeats;
    private TextView mTextWhen;

    private CheckBox mCheckBoxSpinnerDepart;
    private CheckBox mCheckBoxSpinnerArrive;
    private CheckBox mCheckBoxSpinnerWhen;
    private CheckBox mCheckBoxEditTextPrice;
    private CheckBox mCheckBoxSeekBarSeats;
    private CheckBox mCheckBoxButtonWhen;
    private CheckBox mCheckBoxButtonApply;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, null, false);

        mSpinnerDepart = (Spinner) rootView.findViewById(R.id.filter_spinner_depart_location);
        mSpinnerArrive = (Spinner) rootView.findViewById(R.id.filter_spinner_arrival_location);
        mSpinnerWhen = (Spinner) rootView.findViewById(R.id.filter_spinner_when);
        mEditPrice = (EditText) rootView.findViewById(R.id.filter_edittext_price);
        mSeekBarSeats = (SeekBar) rootView.findViewById(R.id.filter_seekbar_seats);
        mButtonWhen = (Button) rootView.findViewById(R.id.filter_button_when);
        mButtonApply = (Button) rootView.findViewById(R.id.filter_button_apply);
        mTextSeats = (TextView) rootView.findViewById(R.id.filter_text_content_seats);
        mTextWhen = (TextView) rootView.findViewById(R.id.filter_text_content_when);

        // Set up the spinners
        ArrayAdapter<CharSequence> spinnerAdapterWhen = ArrayAdapter.createFromResource(
                getActivity(), R.array.filter_when_type,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> spinnerAdapterCities = ArrayAdapter.createFromResource(
                getActivity(), R.array.cities, android.R.layout.simple_spinner_item);

        spinnerAdapterWhen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerWhen.setAdapter(spinnerAdapterWhen);
        mSpinnerDepart.setAdapter(spinnerAdapterCities);
        mSpinnerArrive.setAdapter(spinnerAdapterCities);

        mEditPrice.addTextChangedListener(new TextWatcherPrice(mEditPrice));

        mSeekBarSeats.setOnSeekBarChangeListener(new SeekBarInDrawerListener());
        mSeekBarSeats.setOnTouchListener(new SeekBarTouchOverride());
        mSeekBarSeats.setProgress(Defaults.MINIMUM_SEATS);

        mButtonWhen.setOnClickListener(this);

        return rootView;
    }

    // OnTouchListener implementation in order to stop the NavigationDrawer from
    // hijacking touch events that should be registered with the seekbar.
    private class SeekBarTouchOverride implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow Drawer to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow Drawer to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            // Handle seekbar touch events.
            v.onTouchEvent(event);
            return true;
        }
    }

    private class SeekBarInDrawerListener implements OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mTextSeats.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mTextSeats.getText().toString().equals("0")) {
                seekBar.setProgress(Defaults.MINIMUM_SEATS);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_button_when:
                mDateTimePickerHelper = new DateTimePickerHelper(getActivity()
                        .getSupportFragmentManager());
                mDateTimePickerHelper.setOnDateTimeSelectedListener(new DateTimePickerListener());
                mDateTimePickerHelper.show();
        }
    }

    private class DateTimePickerListener implements OnDateTimeSelectedListener {
        @Override
        public void onDateTimeSelected(long timeInMillis) {
            mTextWhen.setText(Utils.multiCaseDateFormat(timeInMillis));
        }
    }
}
