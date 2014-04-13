
package com.dreamteam.hackwaterloo.fragments;

import java.util.Arrays;
import java.util.Map;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.Constants.Endpoint;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter;
import com.dreamteam.hackwaterloo.adapters.FeedAdapter.OnScrollToShowPromptListener;
import com.dreamteam.hackwaterloo.models.Feed;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.sharedinterfaces.OnAnimationEndListener;
import com.dreamteam.hackwaterloo.utils.AnimationBottomPeak;
import com.dreamteam.hackwaterloo.utils.CrossFadeViewSwitcher;
import com.dreamteam.hackwaterloo.volley.GsonRequest;
import com.dreamteam.hackwaterloo.volley.MyVolley;
import com.nineoldandroids.view.ViewHelper;

public class FragmentFindARide extends SherlockFragment implements OnScrollToShowPromptListener,
        OnClickListener, OnRefreshListener {

    public static final String TAG = FragmentFindARide.class.getSimpleName();

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
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_find_a_ride, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.find_ride_progress_bar);
        mListView = (ListView) rootView.findViewById(R.id.find_ride_list_view);
        mViewStubFilterPrompt = (ViewStub) rootView
                .findViewById(R.id.find_ride_viewstub_filter_prompt);
        mPullToRefresh = (PullToRefreshLayout) rootView
                .findViewById(R.id.find_ride_pull_to_refresh);

        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefresh);

        updateEvents(null);

        return rootView;
    }

    @Override
    public void onRefreshStarted(View view) {
        updateEvents(null);
    }

    public void updateEvents(final Map<String, String> params) {
        if (!mPullToRefresh.isRefreshing()) {
            mPullToRefresh.setRefreshing(true);
        }
        
        CrossFadeViewSwitcher switcher = new CrossFadeViewSwitcher(mListView, mProgressBar, false);
        switcher.setOnAnimationEndListener(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                GsonRequest<Feed> request = new GsonRequest<Feed>(Method.GET, Endpoint.FEED,
                        Feed.class,
                        createSuccessListener(), createErrorListeners(), params);
                MyVolley.getRequestQueue().add(request);
            }
        });
        switcher.startAnimation();
    }

    /**
     * @return Creates the listener that is invoked when the VolleyRequest is
     *         successful
     */
    private Response.Listener<Feed> createSuccessListener() {
        return new Response.Listener<Feed>() {
            @Override
            public void onResponse(Feed response) {
                updateList(response.getEvents());
                mPullToRefresh.setRefreshComplete();
                new CrossFadeViewSwitcher(mProgressBar, mListView, false).startAnimation();
            }
        };
    }

    /**
     * @return Creates the listener that is invoked when the VolleyRequest is
     *         unsuccessful
     */
    private Response.ErrorListener createErrorListeners() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    Log.e(TAG, "Error: " + error.networkResponse.statusCode);
                }
                // TODO: make a string perhaps
                Toast.makeText(getActivity(), "Error getting results", Toast.LENGTH_SHORT).show();
            }
        };
    }

    /**
     * Updates the contents of the ListView's adapter, instantiating it if
     * necessary
     * 
     * @param events The events to be inserted into the ListView
     */
    private void updateList(Event[] events) {
        if (mListAdapter == null) {
            mListView = (ListView) getView().findViewById(R.id.find_ride_list_view);
            mListAdapter = new FeedAdapter(getActivity(), this);
            mListView.setAdapter(mListAdapter);
        }

        if (events != null) {
            mListAdapter.replaceDataset(Arrays.asList(events));
        }
    }

    /*
     * The listener invoked when the listView has been scrolled past a certain
     * number of items
     */
    @Override
    public void onScrollToShowPrompt() {
        mButtonFilterPrompt = (Button) mViewStubFilterPrompt.inflate();
        mButtonFilterPrompt.setOnClickListener(this);

        ViewHelper.setAlpha(mButtonFilterPrompt, 0f);
        mButtonFilterPrompt.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(mButtonFilterPrompt, "alpha", 1f).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.find_ride_button_filter_prompt:
            mListener.onFilterPromptToBeShown();
            new AnimationBottomPeak(mButtonFilterPrompt, false).startAnimation();
            break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_find_a_ride, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
