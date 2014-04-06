package com.dreamteam.hackwaterloo.fragments;

import java.util.Arrays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.activities.ActivityMain;
import com.dreamteam.hackwaterloo.adapters.Feed;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.utils.server.BaseTask.OnPostExecuteListener;
import com.dreamteam.hackwaterloo.utils.server.GetEventsTask;

public class FragmentFindARide extends SherlockFragment implements OnClickListener {

    public static final String FRAGMENT_TAG = FragmentFindARide.class.getSimpleName();

    // Ui Widgets
    private ProgressBar mProgressBar;
    private ListView mListView;
    private FeedAdapter mListAdapter;
    private Button mFilterButton;
    private FragmentManager mFragmentManager;

    @Override
    public void onAttach(Activity activity) {
        mFragmentManager = ((ActivityMain) activity).getSupportFragmentManager();
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_find_a_ride, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.find_ride_progress_bar);
        mFilterButton = (Button) rootView.findViewById(R.id.find_ride_button_filter);
        mListView = (ListView) rootView.findViewById(R.id.find_ride_list_view);
        
        if (mFilterButton == null) {
            Log.e("ryan", "filter button is null!!!!");
        }
        mFilterButton.setOnClickListener(this);
        
        getEvents();
        
        return rootView;
    }

    private void getEvents() {
        GetEventsTask getEventsTask = new GetEventsTask();
        getEventsTask.setOnPostExecuteListener(new OnPostExecuteListener<Feed.Event[]>() {
            @Override
            public void onFinish(Event[] events) {
                if (events != null && events.length > 0) {
                    mListAdapter = new FeedAdapter(getActivity(), Arrays.asList(events));
                    mListView.setAdapter(mListAdapter);
                    mListAdapter.notifyDataSetChanged();
                }
                new CrossFadeViewSwitcher(mProgressBar, mListView, false).startAnimation();
            }
        });
        getEventsTask.executeParallel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_ride_button_filter:
                DialogFilter dialogFilter = (DialogFilter) mFragmentManager.findFragmentByTag(DialogFilter.FRAGMENT_TAG);
                if (dialogFilter == null) {
                    dialogFilter = new DialogFilter();
                }
                dialogFilter.show(mFragmentManager, DialogFilter.FRAGMENT_TAG);
        }
    }

    public static class DialogFilter extends SherlockDialogFragment {

        public static final String FRAGMENT_TAG = DialogFilter.class.getSimpleName();

        private Spinner mSpinnerStart;
        
        private String[] mCities;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(STYLE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_filter);
            dialog.setCancelable(true);
            dialog.setTitle(R.string.dialog_filter_title);

            mSpinnerStart = (Spinner) dialog.findViewById(R.id.dialog_filter_spinner_startpoint);
            
            mCities = getActivity().getResources().getStringArray(R.array.cities);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mCities);
            mSpinnerStart.setAdapter(adapter);
            
            return dialog;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }
    
}