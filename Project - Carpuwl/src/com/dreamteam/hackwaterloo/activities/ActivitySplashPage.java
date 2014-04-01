package com.dreamteam.hackwaterloo.activities;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dreamteam.carpuwl.R;

public class ActivitySplashPage extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash_page);
        Intent intent = new Intent(this, ActivityLogIn.class);
    	startActivity(intent);    
    }
}
