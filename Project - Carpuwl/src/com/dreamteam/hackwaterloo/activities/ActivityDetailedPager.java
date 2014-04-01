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
import com.dreamteam.hackwaterloo.Constants;
import com.dreamteam.hackwaterloo.User;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.facebook.widget.ProfilePictureView;

public class ActivityDetailedPager extends SherlockFragmentActivity {

    private EventDetailAdapter mAdapter;
    private ViewPager mPager;
    private ArrayList<Event> mEvents;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fragment_detailed_event);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.app_theme_color)));

        mEvents = getIntent().getParcelableArrayListExtra(Constants.Extra.Event);
        int position = getIntent().getIntExtra(Constants.Extra.EventPosition, 0);

        mAdapter = new EventDetailAdapter(getSupportFragmentManager(), mEvents);
        mPager = (ViewPager) findViewById(R.id.event_pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(position);

        getSupportActionBar().setTitle("Ride Details");
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
            return EventDetailFragment.newInstance(mEvents.get(position));
        }

        @Override
        public int getCount() {
            return mEvents.size();
        }
    }

    public static class EventDetailFragment extends SherlockFragment {

        private static final String KEY_PARCELABLE = "keyParcelable";

        private RatingBar mRatingBar;
        private TextView mTextViewUserName;
        private TextView mRatingNumber;
        private TextView mTextViewStart;
        private TextView mTextViewEnd;
        private TextView mTextViewPrice;
        private TextView mTextViewSeats;
        private TextView mTextViewStartTime;
        private TextView mTextViewArrivalTime;
        private TextView mTextViewDescription;

        public static EventDetailFragment newInstance(Event event) {
            EventDetailFragment eventDetailFragment = new EventDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_PARCELABLE, event);
            eventDetailFragment.setArguments(bundle);
            return eventDetailFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_event_page, container, false);

            Event mEvent = getArguments().getParcelable(KEY_PARCELABLE);

            ProfilePictureView profilePicture = (ProfilePictureView) rootView
                    .findViewById(R.id.event_user_profile_picture);
            profilePicture.setProfileId(String.valueOf(User.getInstance().get_fb_fk()));

            mRatingBar = (RatingBar) rootView.findViewById(R.id.event_user_rating_bar);
            mTextViewUserName = (TextView) rootView.findViewById(R.id.event_user_name);
            mRatingNumber = (TextView) rootView.findViewById(R.id.event_user_num_ratings);
            mTextViewStart = (TextView) rootView.findViewById(R.id.event_details_start);
            mTextViewEnd = (TextView) rootView.findViewById(R.id.event_details_end);
            mTextViewPrice = (TextView) rootView.findViewById(R.id.event_details_price);
            mTextViewSeats = (TextView) rootView.findViewById(R.id.event_details_seats);
            mTextViewStartTime = (TextView) rootView.findViewById(R.id.event_details_start_time);
            mTextViewArrivalTime = (TextView) rootView.findViewById(R.id.event_details_eta);
            mTextViewDescription = (TextView) rootView.findViewById(R.id.event_details_description);

            mRatingBar.setRating(mEvent.getRating());
            mTextViewUserName.setText(mEvent.getDriverName());
            mRatingNumber.setText(String.format("(%s)", mEvent.getRatingCount()));
            mTextViewStart.setText(mEvent.getStartPoint());
            mTextViewEnd.setText(mEvent.getEndPoint());
            mTextViewPrice.setText(String.format("$%.2f", mEvent.getPrice()));
            mTextViewSeats
                    .setText(String.format("Seats Remaining: %s", mEvent.getSeatsRemaining()));
            mTextViewStartTime.setText(new SimpleDateFormat("MMM dd, h a").format(mEvent
                    .getDepartDate()));
            mTextViewArrivalTime.setText(new SimpleDateFormat("MMM dd, h a").format(mEvent
                    .getArrivalTime()));
            mTextViewDescription.setText(mEvent.getDescription());

            return rootView;
        }
    }
}
