package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.Constants.Defaults;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper.OnDateTimeSelectedListener;
import com.dreamteam.hackwaterloo.utils.TextWatcherPrice;
import com.dreamteam.hackwaterloo.utils.Utils;

public class FragmentFilter extends SherlockFragment implements OnClickListener {

    private static final String LOG_TAG = FragmentFilter.class.getSimpleName();
    public static final String FRAGMENT_TAG = FragmentFilter.class.getSimpleName();
    private static final String KEY_A_CHECKBOX_IS_CHECKED = "keyACheckBoxIsChecked";

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

    // Static (non-dynamic) TextViews
    private TextView mTextTitlePrice;
    private TextView mTextContentPrice;
    private TextView mTextTitleSeats;
    private TextView mTextTitleWhen;

    private CheckBox mCheckBoxSpinnerDepart;
    private CheckBox mCheckBoxSpinnerArrive;
    private CheckBox mCheckBoxPrice;
    private CheckBox mCheckBoxSeats;
    private CheckBox mCheckBoxWhen;

    private long mTimeWhen = 0L;
    private boolean mACheckBoxIsTicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, null, false);

        initViewReferences(rootView);
        initCheckboxes(rootView);
        initSpinners(rootView);

        mEditPrice.addTextChangedListener(new TextWatcherPrice(mEditPrice));

        mSeekBarSeats.setOnSeekBarChangeListener(new FilterSeekBarListener());
        mSeekBarSeats.setOnTouchListener(new SeekBarTouchOverride());
        mSeekBarSeats.setProgress(Defaults.MINIMUM_SEATS);

        mButtonWhen.setOnClickListener(this);

        // First time this view is inflated
        if (savedInstanceState == null) {
            disableAllCheckBoxes();
        } else {
            mACheckBoxIsTicked = savedInstanceState.getBoolean(KEY_A_CHECKBOX_IS_CHECKED, false);
        }

        enableButtonIfDataValid();

        return rootView;
    }

    private void initViewReferences(View rootView) {
        // Non CheckBox dynamic UI widgets
        mSpinnerDepart = (Spinner) rootView.findViewById(R.id.filter_spinner_depart_location);
        mSpinnerArrive = (Spinner) rootView.findViewById(R.id.filter_spinner_arrival_location);
        mSpinnerWhen = (Spinner) rootView.findViewById(R.id.filter_spinner_when);
        mEditPrice = (EditText) rootView.findViewById(R.id.filter_edittext_price);
        mSeekBarSeats = (SeekBar) rootView.findViewById(R.id.filter_seekbar_seats);
        mButtonWhen = (Button) rootView.findViewById(R.id.filter_button_when);
        mButtonApply = (Button) rootView.findViewById(R.id.filter_button_apply);
        mTextSeats = (TextView) rootView.findViewById(R.id.filter_text_content_seats);
        mTextWhen = (TextView) rootView.findViewById(R.id.filter_text_content_when);

        // Non dynamic TextViews
        mTextTitlePrice = (TextView) rootView.findViewById(R.id.filter_text_title_price);
        mTextContentPrice = (TextView) rootView
                .findViewById(R.id.filter_text_price_currency_symbol);
        mTextTitleSeats = (TextView) rootView.findViewById(R.id.filter_text_title_seats);
        mTextTitleWhen = (TextView) rootView.findViewById(R.id.filter_text_title_when);

        // CheckBoxes
        mCheckBoxSpinnerDepart = (CheckBox) rootView
                .findViewById(R.id.filter_checkbox_depart_location);
        mCheckBoxSpinnerArrive = (CheckBox) rootView
                .findViewById(R.id.filter_checkbox_arrival_location);
        mCheckBoxPrice = (CheckBox) rootView.findViewById(R.id.filter_checkbox_price);
        mCheckBoxSeats = (CheckBox) rootView.findViewById(R.id.filter_checkbox_seats);
        mCheckBoxWhen = (CheckBox) rootView.findViewById(R.id.filter_checkbox_button_when);
    }

    private void initSpinners(View rootView) {
        ArrayAdapter<CharSequence> spinnerAdapterWhen = ArrayAdapter.createFromResource(
                getActivity(), R.array.filter_when_type, R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> spinnerAdapterCities = ArrayAdapter.createFromResource(
                getActivity(), R.array.cities, R.layout.simple_spinner_item);

        spinnerAdapterWhen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerWhen.setAdapter(spinnerAdapterWhen);
        mSpinnerDepart.setAdapter(spinnerAdapterCities);
        mSpinnerArrive.setAdapter(spinnerAdapterCities);
    }

    private void initCheckboxes(View rootView) {
        mCheckBoxSpinnerDepart.setOnCheckedChangeListener(new CheckBoxMultiViewToggler(
                new View[] { mSpinnerDepart }));

        mCheckBoxSpinnerArrive.setOnCheckedChangeListener(new CheckBoxMultiViewToggler(
                new View[] { mSpinnerArrive }));

        mCheckBoxPrice.setOnCheckedChangeListener(new CheckBoxMultiViewToggler(new View[] {
                mTextTitlePrice, mTextContentPrice, mEditPrice }));

        mCheckBoxSeats.setOnCheckedChangeListener(new CheckBoxMultiViewToggler(new View[] {
                mTextTitleSeats, mTextSeats, mSeekBarSeats }));

        mCheckBoxWhen.setOnCheckedChangeListener(new CheckBoxMultiViewToggler(new View[] {
                mTextTitleWhen, mTextWhen, mButtonWhen, mSpinnerWhen }));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_A_CHECKBOX_IS_CHECKED, mACheckBoxIsTicked);
        super.onSaveInstanceState(outState);
    }

    private void disableAllCheckBoxes() {
        // The CheckBoxes must first me true in order for the listener to take
        // effect, therefore,
        // set all CheckBoxes with true first before setting them all to false
        mCheckBoxSpinnerDepart.setChecked(true);
        mCheckBoxSpinnerArrive.setChecked(true);
        mCheckBoxPrice.setChecked(true);
        mCheckBoxSeats.setChecked(true);
        mCheckBoxWhen.setChecked(true);

        mCheckBoxSpinnerDepart.setChecked(false);
        mCheckBoxSpinnerArrive.setChecked(false);
        mCheckBoxPrice.setChecked(false);
        mCheckBoxSeats.setChecked(false);
        mCheckBoxWhen.setChecked(false);
    }

    private boolean dataIsValid() {
        if (mCheckBoxSpinnerDepart.isChecked() && mSpinnerDepart.getSelectedItemPosition() == 0) {
            Log.d(LOG_TAG, "cannot apply filter where depart location is \"Select One\"");
        } else if (mCheckBoxSpinnerArrive.isChecked()
                && mSpinnerArrive.getSelectedItemPosition() == 0) {
            Log.d(LOG_TAG, "cannot apply filter where arrive location is \"Select One\"");
        } else if (mCheckBoxSpinnerArrive.isChecked()
                && mCheckBoxSpinnerDepart.isChecked()
                && mSpinnerArrive.getSelectedItemPosition() == mSpinnerDepart
                        .getSelectedItemPosition()) {
            Log.d(LOG_TAG, "cannot apply filter where arrival and depart are the same");
        } else if (mCheckBoxPrice.isChecked() && mEditPrice.getText().toString() == "") {
            Log.d(LOG_TAG, "cannot apply filter where price field is empty");
        } else if (mCheckBoxPrice.isChecked()
                && Utils.getDoubleFromPriceEditText(mEditPrice) < Defaults.MINIMUM_PRICE) {
            Log.d(LOG_TAG, "cannot apply filter where price is less than $1");
        } else if (mCheckBoxSeats.isChecked() && mSeekBarSeats.getProgress() == 0) {
            Log.d(LOG_TAG, "cannot appl filter where seats remaining is 0");
        } else if (mCheckBoxWhen.isChecked() && mTimeWhen < System.currentTimeMillis()) {
            Log.d(LOG_TAG, "cannot apply filter where time selected is in the past");
        } else if (!mACheckBoxIsTicked) {
            Log.d(LOG_TAG, "cannot apply filter where no CheckBoxes are ticked");
        } else {
            return true;
        }

        return false;
    }

    private void enableButtonIfDataValid() {
        mButtonApply.setEnabled(dataIsValid());
    }

    private class CheckBoxMultiViewToggler implements OnCheckedChangeListener {

        private View[] mViewsToBeToggled;

        public CheckBoxMultiViewToggler(View[] viewsToBeToggled) {
            mViewsToBeToggled = viewsToBeToggled;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // Enable or disable all views associated with this CheckBox
            for (View view : mViewsToBeToggled) {
                view.setEnabled(isChecked);
            }
            mACheckBoxIsTicked = isChecked;
            enableButtonIfDataValid();
        }
    }

    // OnTouchListener implementation in order to stop the NavigationDrawer from
    // hijacking touch events that should be registered with the SeekBar.
    private class SeekBarTouchOverride implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow Drawer to intercept touch events.
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow Drawer to intercept touch events.
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            // Delegate the touch event to the seekbar
            view.onTouchEvent(event);
            return true;
        }
    }

    private class FilterSeekBarListener implements OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mTextSeats.setText(String.valueOf(progress));
            enableButtonIfDataValid();
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
                break;

            case R.id.filter_button_apply:
                Toast.makeText(getActivity(), "Apply the filter", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class DateTimePickerListener implements OnDateTimeSelectedListener {
        @Override
        public void onDateTimeSelected(long timeInMillis) {
            mTimeWhen = timeInMillis;
            mTextWhen.setText(Utils.multiCaseDateFormat(timeInMillis));
            enableButtonIfDataValid();
        }
    }
}
