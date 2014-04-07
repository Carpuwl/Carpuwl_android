package com.dreamteam.hackwaterloo.fragments;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.adapters.Feed;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.utils.server.BaseTask.OnPostExecuteListener;
import com.dreamteam.hackwaterloo.utils.server.GetEventsTask;

public class FragmentFindARide extends SherlockFragment {

    public static final String FRAGMENT_TAG = FragmentFindARide.class.getSimpleName();

    private ProgressBar mProgressBar;
    private ListView mListView;
    private FeedAdapter mListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_find_a_ride, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.find_ride_progress_bar);
        mListView = (ListView) rootView.findViewById(R.id.find_ride_list_view);
        
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }
    
}