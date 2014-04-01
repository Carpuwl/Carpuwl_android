package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.User;
import com.facebook.widget.ProfilePictureView;
//<<<<<<< HEAD
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//import com.actionbarsherlock.app.SherlockFragment;
//import com.dreamteam.carpuwl.R;
//import com.dreamteam.hackwaterloo.User;
//import com.dreamteam.hackwaterloo.adapters.Feed;
//import com.dreamteam.hackwaterloo.adapters.Feed.Event;
//import com.dreamteam.hackwaterloo.utils.server.BaseTask.OnPostExecuteListener;
//import com.dreamteam.hackwaterloo.utils.server.GetEventsTask;
//import com.facebook.widget.ProfilePictureView;
//
//public class FragmentMyProfile extends SherlockFragment {
//    
//    private TextView mTextViewProfileName;
//    private TextView mTextViewRatingsCount;
//    private RatingBar mRating;
//    
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.activity_user_profile, container, false);
//        
//        mTextViewProfileName = (TextView) rootView.findViewById(R.id.user_profile_name);
//        mTextViewRatingsCount = (TextView) rootView.findViewById(R.id.user_profile_ratings);
//        mRating = (RatingBar) rootView.findViewById(R.id.user_profile_rating_bar);
//        
//        mTextViewProfileName.setText(User.getInstance().get_name());
//        mRating.setRating(4.3f);
//        
//        ProfilePictureView profilePicture = (ProfilePictureView) rootView.findViewById(R.id.user_profile_picture_image);
//        profilePicture.setProfileId(String.valueOf(User.getInstance().get_fb_fk()));
//        
//        GetEventsTask getEventsTask = new GetEventsTask();
//        getEventsTask.setOnPostExecuteListener(new OnPostExecuteListener<Feed.Event[]>() {
//            @Override
//            public void onFinish(Event[] returnItem) {
//            }
//        });
//        getEventsTask.executeParallel();
//        
//        return rootView;
//    }
//    
//    
//=======

public class FragmentMyProfile extends SherlockFragment{
	
	public static final String FRAGMENT_TAG = FragmentMyProfile.class.getSimpleName();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        
        String username = User.getInstance().get_name();
        int fb_pk = User.getInstance().get_fb_fk();
        String phone_number = User.getInstance().get_phone();
        
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.selection_profile_pic);
        profilePictureView.setProfileId(Integer.toString(fb_pk));
        
        return rootView;
    }
}
