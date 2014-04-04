package com.dreamteam.hackwaterloo.fragments;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.dreamteam.hackwaterloo.User;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.facebook.widget.ProfilePictureView;

public class FragmentDetailedEvent extends SherlockFragment {

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

    public static FragmentDetailedEvent newInstance(Event event) {
        FragmentDetailedEvent eventDetailFragment = new FragmentDetailedEvent();
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
        profilePicture.setProfileId(String.valueOf(AppData.getFacebookForeginKey()));

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
        mTextViewStartTime.setText(Utils.multiCaseDateFormat((mEvent.getDepartDate())));
        mTextViewArrivalTime.setText(Utils.multiCaseDateFormat((mEvent.getArrivalTime())));
        mTextViewDescription.setText(mEvent.getDescription());

        return rootView;
    }
}