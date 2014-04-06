package com.dreamteam.hackwaterloo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.dreamteam.carpuwl.R;

public class FragmentFilter extends SherlockFragment {
    
    public static final String FRAGMENT_TAG = FragmentFilter.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_view, null, false);
        
        return rootView;
    }
}
