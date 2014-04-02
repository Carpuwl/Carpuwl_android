package com.dreamteam.hackwaterloo.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.App;
import com.dreamteam.hackwaterloo.Constants;
import com.dreamteam.hackwaterloo.User;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.fragments.FragmentDetailedEvent;
import com.facebook.widget.ProfilePictureView;

public class ActivityDetailedPager extends SherlockFragmentActivity {

    private EventDetailAdapter mAdapter;
    private ViewPager mPager;
    private ArrayList<Event> mEvents;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fragment_detailed_event);

        mEvents = getIntent().getParcelableArrayListExtra(Constants.Extra.Event);
        int position = getIntent().getIntExtra(Constants.Extra.EventPosition, 0);

        mAdapter = new EventDetailAdapter(getSupportFragmentManager(), mEvents);
        mPager = (ViewPager) findViewById(R.id.event_pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(position);

        getSupportActionBar().setTitle(App.getXmlString(R.string.actionbar_title_find_a_ride));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class EventDetailAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Event> mEvents;

        public EventDetailAdapter(FragmentManager fm, ArrayList<Event> events) {
            super(fm);
            mEvents = events;
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentDetailedEvent.newInstance(mEvents.get(position));
        }

        @Override
        public int getCount() {
            return mEvents.size();
        }
    }
}
