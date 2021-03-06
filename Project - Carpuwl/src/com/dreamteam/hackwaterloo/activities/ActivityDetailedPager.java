
package com.dreamteam.hackwaterloo.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.view.MenuItem;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.common.Constants;
import com.dreamteam.hackwaterloo.fragments.FragmentDetailedEvent;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.utils.Utils;

public class ActivityDetailedPager extends ActivityDreamTeam {

    private EventDetailAdapter mAdapter;
    private ViewPager mPager;
    private ArrayList<Event> mEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_event);

        if (getIntent().getBooleanExtra(Constants.Extra.EVENT_SINGLE, false)) {
            Event event = getIntent().getParcelableExtra(Constants.Extra.EVENT);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content,
                            FragmentDetailedEvent.newInstance(event))
                    .commit();
        } else {
            mEvents = getIntent().getParcelableArrayListExtra(Constants.Extra.EVENT);
            int position = getIntent().getIntExtra(Constants.Extra.EVENT_POSITION, 0);
            
            mAdapter = new EventDetailAdapter(getSupportFragmentManager(), mEvents);
            mPager = (ViewPager) findViewById(R.id.event_pager);
            mPager.setAdapter(mAdapter);
            mPager.setCurrentItem(position);
        }

        getSupportActionBar().setTitle(Utils.getString(R.string.actionbar_title_find_a_ride));
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
