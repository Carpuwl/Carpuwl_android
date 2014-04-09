package com.dreamteam.hackwaterloo.fragments;

import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.adapters.Feed;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter.OnScrollToShowPromptListener;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.utils.server.BaseTask.OnPostExecuteListener;
import com.dreamteam.hackwaterloo.utils.server.GetEventsTask;

public class FragmentFindARide extends SherlockFragment implements OnScrollToShowPromptListener, OnClickListener {

    public static final String FRAGMENT_TAG = FragmentFindARide.class.getSimpleName();

    private ProgressBar mProgressBar;
    private ListView mListView;
    private FeedAdapter mListAdapter;
    private Button mButtonFilterPrompt;
    private ViewStub mViewStubFilterPrompt;
    private FilterPromptListener mListener;
    
    public interface FilterPromptListener {
        void onFilterPromptToBeShown();
    }
    
    @Override
    public void onAttach(Activity activity) {
        try {
            mListener = (FilterPromptListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getClass().getSimpleName() + " must implement FilterPromptListener");
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_find_a_ride, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.find_ride_progress_bar);
        mListView = (ListView) rootView.findViewById(R.id.find_ride_list_view);
        mViewStubFilterPrompt = (ViewStub) rootView.findViewById(R.id.find_ride_viewstub_filter_prompt);
        
        getEvents();
        
        return rootView;
    }

    private void getEvents() {
        GetEventsTask getEventsTask = new GetEventsTask();
        getEventsTask.setOnPostExecuteListener(new OnPostExecuteListener<Feed.Event[]>() {
            @Override
            public void onFinish(Event[] events) {
                if (events != null && events.length > 0) {
                    setupListView(events);
                }
                new CrossFadeViewSwitcher(mProgressBar, mListView, false).startAnimation();
            }
        });
        getEventsTask.executeParallel();
    }
    
    private void setupListView(Event[] events) {
        mListView = (ListView) getView().findViewById(R.id.find_ride_list_view);
        mListAdapter = new FeedAdapter(getActivity(), Arrays.asList(events), this);
        mListView.setAdapter(mListAdapter);
    }
    
    @Override
    public void onScrollToShowPrompt() {
        mButtonFilterPrompt = (Button) mViewStubFilterPrompt.inflate();
        mButtonFilterPrompt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_ride_button_filter_prompt:
                mListener.onFilterPromptToBeShown();
                mButtonFilterPrompt.setVisibility(View.GONE);
                break;
        }
    }
    
}