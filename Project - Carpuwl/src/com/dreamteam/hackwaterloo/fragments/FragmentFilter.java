
package com.dreamteam.hackwaterloo.fragments;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.common.Constants.Defaults;
import com.dreamteam.hackwaterloo.models.Feed.SerializedNames;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper;
import com.dreamteam.hackwaterloo.utils.DateTimePickerHelper.OnDateTimeSelectedListener;
import com.dreamteam.hackwaterloo.utils.Utils;

/**
 * @author Ryan
 */
public class FragmentFilter extends SherlockFragment implements OnClickListener {

    public static final String TAG = FragmentFilter.class.getSimpleName();
    private static final String SHARED_PREF_FILTER = "filter_settings";

    private static final String KEY_CHECKBOX_LOCATION_DEPART = "checkBoxLocationDepart";
    private static final String KEY_CHECKBOX_LOCATION_ARRIVE = "checkBoxLocationArrive";
    private static final String KEY_CHECKBOX_PRICE = "checkBoxPrice";
    private static final String KEY_CHECKBOX_SEATS = "checkBoxSeats";
    private static final String KEY_CHECKBOX_WHEN = "checkBoxWhen";

    private static final String KEY_LOCATION_DEPART = "locationDepart";
    private static final String KEY_LOCATION_ARRIVE = "locationArrive";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SEATS = "seats";
    private static final String KEY_WHEN = "when";

    private OnFilterAppliedListener mListener;
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

    private long mTimeWhen;

    @Override
    public void onAttach(Activity activity) {
        try {
            mListener = (OnFilterAppliedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement OnFilterappliedListener");
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, null, false);

        initViewReferences(rootView);
        initCheckBoxGroups(rootView);
        initSpinners(rootView);

        mEditPrice.addTextChangedListener(new EditTextWatcher());

        mSeekBarSeats.setOnSeekBarChangeListener(new FilterSeekBarListener());
        mSeekBarSeats.setOnTouchListener(new SeekBarTouchOverride());
        mSeekBarSeats.setProgress(Defaults.MINIMUM_SEATS);

        mButtonWhen.setOnClickListener(this);
        mButtonApply.setOnClickListener(this);

        restoreFilterSettings();

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
                getActivity(), R.array.filter_when_type, R.layout.spinner_selected_item);

        ArrayAdapter<CharSequence> spinnerAdapterCities = ArrayAdapter.createFromResource(
                getActivity(), R.array.cities, R.layout.spinner_selected_item);

        spinnerAdapterWhen.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAdapterCities.setDropDownViewResource(R.layout.spinner_dropdown_item);

        mSpinnerWhen.setAdapter(spinnerAdapterWhen);
        mSpinnerDepart.setAdapter(spinnerAdapterCities);
        mSpinnerArrive.setAdapter(spinnerAdapterCities);

        SpinnerButtonEnabler spinnerListener = new SpinnerButtonEnabler();
        mSpinnerDepart.setOnItemSelectedListener(spinnerListener);
        mSpinnerArrive.setOnItemSelectedListener(spinnerListener);
    }

    private void initCheckBoxGroups(View rootView) {
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

    private void restoreFilterSettings() {
        disableAllCheckBoxes();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                SHARED_PREF_FILTER, Context.MODE_PRIVATE);

        mCheckBoxSpinnerDepart.setChecked(sharedPreferences.getBoolean(
                KEY_CHECKBOX_LOCATION_DEPART, false));
        mCheckBoxSpinnerArrive.setChecked(sharedPreferences.getBoolean(
                KEY_CHECKBOX_LOCATION_ARRIVE, false));
        mCheckBoxPrice.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX_PRICE, false));
        mCheckBoxSeats.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX_SEATS, false));
        mCheckBoxWhen.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX_WHEN, false));

        mSpinnerDepart.setSelection(sharedPreferences.getInt(KEY_LOCATION_DEPART, 0));
        mSpinnerArrive.setSelection(sharedPreferences.getInt(KEY_LOCATION_ARRIVE, 0));
        mEditPrice.setText((sharedPreferences.getString(KEY_PRICE, "")));
        mSeekBarSeats.setProgress(sharedPreferences.getInt(KEY_SEATS, Defaults.MINIMUM_SEATS));
        mTimeWhen = sharedPreferences.getLong(KEY_WHEN, System.currentTimeMillis()
                + Defaults.MINIMUM_WHEN_OFFSET);
        mTextWhen.setText(Utils.multiCaseDateFormat(mTimeWhen));
    }

    @Override
    public void onStop() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(SHARED_PREF_FILTER,
                Context.MODE_PRIVATE).edit();

        editor.putBoolean(KEY_CHECKBOX_LOCATION_DEPART, mCheckBoxSpinnerDepart.isChecked());
        editor.putBoolean(KEY_CHECKBOX_LOCATION_ARRIVE, mCheckBoxSpinnerArrive.isChecked());
        editor.putBoolean(KEY_CHECKBOX_PRICE, mCheckBoxPrice.isChecked());
        editor.putBoolean(KEY_CHECKBOX_SEATS, mCheckBoxSeats.isChecked());
        editor.putBoolean(KEY_CHECKBOX_WHEN, mCheckBoxWhen.isChecked());

        editor.putInt(KEY_LOCATION_DEPART, mSpinnerDepart.getSelectedItemPosition());
        editor.putInt(KEY_LOCATION_ARRIVE, mSpinnerArrive.getSelectedItemPosition());
        editor.putString(KEY_PRICE, String.valueOf(mEditPrice.getText().toString()));
        editor.putInt(KEY_SEATS, Integer.valueOf(mTextSeats.getText().toString()));
        editor.putLong(KEY_WHEN, mTimeWhen);

        editor.commit();

        super.onStop();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void disableAllCheckBoxes() {
        // CheckBoxes must CHANGE state for the listener to take effect (that
        // is, if a CkecBox is already false and is explicitly set to false,
        // its listener will not be called).
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

    private boolean noCheckBoxesTicked() {
        return (!mCheckBoxSpinnerDepart.isChecked()
                && !mCheckBoxSpinnerArrive.isChecked()
                && !mCheckBoxPrice.isChecked()
                && !mCheckBoxSeats.isChecked()
                && !mCheckBoxWhen.isChecked());
    }

    private boolean dataIsValid() {
        if (mCheckBoxSpinnerDepart.isChecked() && mSpinnerDepart.getSelectedItemPosition() == 0) {
            Log.d(TAG, "cannot apply filter where depart location is \"Select One\"");
        } else if (mCheckBoxSpinnerArrive.isChecked()
                && mSpinnerArrive.getSelectedItemPosition() == 0) {
            Log.d(TAG, "cannot apply filter where arrive location is \"Select One\"");
        } else if (mCheckBoxSpinnerArrive.isChecked()
                && mCheckBoxSpinnerDepart.isChecked()
                && mSpinnerArrive.getSelectedItemPosition() == mSpinnerDepart
                        .getSelectedItemPosition()) {
            Log.d(TAG, "cannot apply filter where arrival and depart are the same");
        } else if (mCheckBoxPrice.isChecked() && TextUtils.isEmpty(mEditPrice.getText().toString())) {
            Log.d(TAG, "cannot apply filter where price field is empty");
        } else if (mCheckBoxPrice.isChecked()
                && Integer.parseInt(mEditPrice.getText().toString()) < Defaults.MINIMUM_PRICE) {
            Log.d(TAG, "cannot apply filter where price is less than $1");
        } else if (mCheckBoxSeats.isChecked() && mSeekBarSeats.getProgress() == 0) {
            Log.d(TAG, "cannot appl filter where seats remaining is 0");
        } else if (mCheckBoxWhen.isChecked() && mTimeWhen < System.currentTimeMillis()) {
            Log.d(TAG, "cannot apply filter where time selected is in the past");
        } else if (noCheckBoxesTicked()) {
            Log.d(TAG, "cannot apply filter where no CheckBoxes are ticked");
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
            enableButtonIfDataValid();
        }
    }

    private class SpinnerButtonEnabler implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            enableButtonIfDataValid();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
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

    private class EditTextWatcher implements TextWatcher {

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after) {
            enableButtonIfDataValid();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
                Map<String, String> filterSettings = new HashMap<String, String>();

                if (mCheckBoxPrice.isChecked()) {
                    filterSettings.put(SerializedNames.PRICE, mEditPrice.getText().toString());
                }

                if (mCheckBoxSeats.isChecked()) {
                    filterSettings
                            .put(SerializedNames.SEATS_REMAINING, mTextSeats.getText().toString());
                }

                if (mCheckBoxWhen.isChecked()) {
                    filterSettings.put(SerializedNames.DATE_DEPART, String.valueOf(mTimeWhen));
                }

                if (mCheckBoxSpinnerDepart.isChecked()) {
                    filterSettings.put(SerializedNames.LOCATION_START, mSpinnerDepart
                            .getSelectedItem()
                            .toString());
                }

                if (mCheckBoxSpinnerArrive.isChecked()) {
                    filterSettings.put(SerializedNames.LOCATION_END, mSpinnerArrive
                            .getSelectedItem()
                            .toString());
                }

                Log.d(TAG, "Filter settings: " + filterSettings.toString());
                mListener.onFilterApplied(filterSettings);
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

    public interface OnFilterAppliedListener {
        void onFilterApplied(Map<String, String> filterSettings);
    }
}
