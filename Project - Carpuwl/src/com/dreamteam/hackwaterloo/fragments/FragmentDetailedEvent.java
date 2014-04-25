
package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.toolbox.NetworkImageView;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.dreamteam.hackwaterloo.volley.MyVolley;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detailed_event, container, false);

        Event mEvent = getArguments().getParcelable(KEY_PARCELABLE);

        NetworkImageView profilePicture = (NetworkImageView) rootView
                .findViewById(R.id.event_details_profile_picture);
        Log.d("ryan", "facebook id: " + mEvent.getFacebookId());
        profilePicture.setImageUrl("http://graph.facebook.com/" + mEvent.getFacebookId()
                + "/picture?type=large", MyVolley.getImageLoader());

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

        mRatingBar.setRating((float) mEvent.getRating());
        mTextViewUserName.setText(mEvent.getDriverName());
        mRatingNumber.setText(String.format("(%d)", mEvent.getRatingCount()));
        mTextViewStart.setText(mEvent.getLocationStart());
        mTextViewEnd.setText(mEvent.getLocationEnd());
        mTextViewPrice.setText(String.format("$%.2f", mEvent.getPrice()));
        mTextViewSeats
                .setText(String.format(Utils.getString(R.string.event_details_seats_remaining),
                        mEvent.getSeatsRemaining()));
        mTextViewStartTime.setText(Utils.multiCaseDateFormat((mEvent.getDateDepart())));
        mTextViewArrivalTime.setText(Utils.multiCaseDateFormat((mEvent.getDateArrive())));
        mTextViewDescription.setText(mEvent.getDescription());

        return rootView;
    }
}
