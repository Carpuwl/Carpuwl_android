package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.facebook.widget.ProfilePictureView;

public class FragmentMyProfile extends SherlockFragment{
	
	public static final String TAG = FragmentMyProfile.class.getSimpleName();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.selection_profile_pic);
        profilePictureView.setProfileId(String.valueOf(AppData.getFacebookForeginKey()));
        
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }
}
