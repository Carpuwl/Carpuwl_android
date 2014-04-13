package com.dreamteam.hackwaterloo.activities;

import android.os.Build;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class ActivityDreamTeam extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getSupportActionBar().setHomeButtonEnabled(true);
        if (Utils.supportsSdk(Build.VERSION_CODES.HONEYCOMB)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Utils.getColor(R.color.app_theme_color));
    }
}
