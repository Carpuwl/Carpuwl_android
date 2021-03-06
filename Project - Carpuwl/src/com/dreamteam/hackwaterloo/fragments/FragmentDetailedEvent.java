
package com.dreamteam.hackwaterloo.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FragmentDetailedEvent extends SherlockFragment {

    private static final String KEY_EVENT = "keyParcelable";

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
        bundle.putParcelable(KEY_EVENT, event);
        eventDetailFragment.setArguments(bundle);
        return eventDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detailed_event, container, false);

        Event mEvent = getArguments().getParcelable(KEY_EVENT);

//        NetworkImageView profilePicture = (NetworkImageView) rootView
//                .findViewById(R.id.event_details_profile_picture);
//        
//        // TODO: this is sloppy
//        profilePicture.setImageUrl("http://graph.facebook.com/" + mEvent.getFacebookId()
//                + "/picture?type=large", MyVolley.getImageLoader());
        
        
        
        ImageView profilePicture = (ImageView) rootView.findViewById(R.id.event_details_profile_picture);
        profilePicture.setImageDrawable(null);
        String url = "http://graph.facebook.com/" + mEvent.getFacebookId() + "/picture?type=square&height=200&width=200";
        Log.d("ryan", "url: " + url);
        
        ImageLoader.getInstance().displayImage(url, profilePicture);
        
        mRatingBar = (RatingBar) rootView.findViewById(R.id.event_details_rating);
        mTextViewUserName = (TextView) rootView.findViewById(R.id.event_details_name);
        mRatingNumber = (TextView) rootView.findViewById(R.id.event_details_num_ratings);
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
        mTextViewSeats.setText(String.valueOf(mEvent.getSeatsRemaining()));
        mTextViewStartTime.setText(Utils.multiCaseDateFormat((mEvent.getDateDepart())));
        mTextViewArrivalTime.setText(Utils.multiCaseDateFormat((mEvent.getDateArrive())));
        mTextViewDescription.setText(mEvent.getDescription());

        return rootView;
    }
}
