package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
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
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.server.CreateEventTask;

public class FragmentPostARide extends SherlockFragment implements OnClickListener {

    private Button mButtonSubmit;
    private Spinner mSpinnerStart;
    private Spinner mSpinnerEnd;
    private EditText mEditPrice;
    private EditText mEditSeatsRemaining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_a_ride, container, false);

        
        mButtonSubmit = (Button) rootView.findViewById(R.id.post_ride_button_submit_event);
        mSpinnerStart = (Spinner) rootView.findViewById(R.id.post_ride_spinner_depart_from);
        mSpinnerEnd = (Spinner) rootView.findViewById(R.id.post_ride_spinner_arrive_at);
        mEditPrice = (EditText) rootView.findViewById(R.id.post_ride_edittext_price);
        mEditSeatsRemaining = (EditText) rootView.findViewById(R.id.post_ride_edittext_seats);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        mSpinnerStart.setAdapter(adapter);
        mSpinnerEnd.setAdapter(adapter);
        
        mSpinnerStart.setSelection(1);
        mSpinnerEnd.setSelection(5);

        mButtonSubmit.setOnClickListener(this);

        return rootView;
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
        }

    }

}
