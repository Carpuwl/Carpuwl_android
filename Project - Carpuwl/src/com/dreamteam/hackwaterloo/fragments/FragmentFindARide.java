package com.dreamteam.hackwaterloo.fragments;

import java.util.Arrays;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
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

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.adapters.Feed;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter.OnScrollToShowPromptListener;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher.OnAnimationEndListener;
import com.dreamteam.hackwaterloo.utils.server.BaseTask.OnPostExecuteListener;
import com.dreamteam.hackwaterloo.utils.server.GetEventsTask;

public class FragmentFindARide extends SherlockFragment implements OnScrollToShowPromptListener,
        OnClickListener, OnRefreshListener {

    public static final String FRAGMENT_TAG = FragmentFindARide.class.getSimpleName();

    private PullToRefreshLayout mPullToRefresh;
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
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement FilterPromptListener");
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_find_a_ride, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.find_ride_progress_bar);
        mListView = (ListView) rootView.findViewById(R.id.find_ride_list_view);
        mViewStubFilterPrompt = (ViewStub) rootView
                .findViewById(R.id.find_ride_viewstub_filter_prompt);
        mPullToRefresh = (PullToRefreshLayout) rootView.findViewById(R.id.find_ride_pull_to_refresh);

        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefresh);
        
        getEvents();

        return rootView;
    }
    
    @Override
    public void onRefreshStarted(View view) {
        CrossFadeViewSwitcher switcher = new CrossFadeViewSwitcher(mListView, mProgressBar, false);
        switcher.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                getEvents();
            }
        });
        switcher.startAnimation();
    }

    private void getEvents() {
        GetEventsTask getEventsTask = new GetEventsTask();
        getEventsTask.setOnPostExecuteListener(new OnPostExecuteListener<Feed.Event[]>() {
            @Override
            public void onFinish(Event[] events) {
                if (events != null && events.length > 0) {
                    updateList(events);
                }
                mPullToRefresh.setRefreshComplete();
                new CrossFadeViewSwitcher(mProgressBar, mListView, false).startAnimation();
            }
        });
        getEventsTask.executeParallel();
    }

    private void updateList(Event[] events) {
        if (mListAdapter == null) {
            mListView = (ListView) getView().findViewById(R.id.find_ride_list_view);
            mListAdapter = new FeedAdapter(getActivity(), Arrays.asList(events), this);
            mListView.setAdapter(mListAdapter);
        } else {
            mListAdapter.addItemBatch(Arrays.asList(events));
        }
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