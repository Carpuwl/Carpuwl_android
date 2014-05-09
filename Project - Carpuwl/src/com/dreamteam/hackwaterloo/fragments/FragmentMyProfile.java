package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.AppData;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FragmentMyProfile extends SherlockFragment{
	
	public static final String TAG = FragmentMyProfile.class.getSimpleName();
	private TextView mTextProfileName;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        
        ImageView profilePicture = (ImageView) rootView.findViewById(R.id.profile_profile_picture);
        profilePicture.setImageDrawable(null);
        String url = "http://graph.facebook.com/" + AppData.getFacebookForeginKey() + "/picture?type=square&height=200&width=200";
        
        ImageLoader.getInstance().displayImage(url, profilePicture);
        
        mTextProfileName = (TextView) rootView.findViewById(R.id.profile_name);
        mTextProfileName.setText(AppData.getName());
        
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }
}
