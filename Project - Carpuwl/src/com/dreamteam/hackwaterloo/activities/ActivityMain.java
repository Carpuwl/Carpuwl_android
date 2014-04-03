package com.dreamteam.hackwaterloo.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.adapters.DrawerLayoutAdapter;
import com.dreamteam.hackwaterloo.fragments.FragmentFindARide;
import com.dreamteam.hackwaterloo.fragments.FragmentMyProfile;
import com.facebook.Session;

public class ActivityMain extends SherlockFragmentActivity {

    private static final int DEFAULT_PAGE_POSITION = 1;
    
    private FragmentManager mFragmentManager;
    private String[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFragmentManager = getSupportFragmentManager();

        initNavDrawer();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportActionBar().setTitle("Find A Ride");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, new FragmentFindARide(), FragmentFindARide.FRAGMENT_TAG).commit();
        }
    }

    private void initNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_listview);

        mDrawerToggle = new DrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // set adapter
        mDrawerItems = getResources().getStringArray(R.array.drawer_item_names);
        mDrawerList.setAdapter(new DrawerLayoutAdapter(this, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerList.setItemChecked(DEFAULT_PAGE_POSITION, true);
    }

    private class DrawerToggle extends ActionBarDrawerToggle {
        
        public DrawerToggle(SherlockFragmentActivity activity, DrawerLayout drawerLayout,
                int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {

            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
                    closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            drawerSelectItem(position);
        }
    }

    private void drawerSelectItem(int position) {
        // Toast.makeText(ActivityMain.this, mDrawerItems[position],
        // Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0: // My Profile
                getSupportActionBar().setTitle("My Profile");
                mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, new FragmentMyProfile()).commit();
                break;

            case 1: // Find a ride
                getSupportActionBar().setTitle("Find A Ride");
                mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, new FragmentFindARide()).commit();
                break;
                
            case 3: // Sign out
            	Session session = Session.getActiveSession();
            	if (session.isOpened()) {
            		session.closeAndClearTokenInformation();
            		Intent intent = new Intent(ActivityMain.this, ActivityLogIn.class);
            		startActivity(intent);
            	}
            	break;
        }

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivitySplashPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        super.onBackPressed();
    }
    
}
