package com.dreamteam.hackwaterloo.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.adapters.DrawerLayoutAdapter;
import com.dreamteam.hackwaterloo.fragments.FragmentFindARide;
import com.dreamteam.hackwaterloo.fragments.FragmentMyProfile;
import com.dreamteam.hackwaterloo.fragments.FragmentPostARide;
import com.dreamteam.hackwaterloo.models.DrawerItem;

public class ActivityMain extends SherlockFragmentActivity {

    private static final int DEFAULT_PAGE_POSITION = 1;
    
    private FragmentManager mFragmentManager;
    private DrawerItem[] mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerPrimary;
    private FrameLayout mDrawerSecondary;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mFragmentManager = getSupportFragmentManager();

        initNavDrawer();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportActionBar().setTitle((mDrawerItems[1].getTitle()));
            LayoutInflater.from(this).inflate(R.layout.filter_view, mDrawerSecondary, true);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, new FragmentFindARide(), FragmentFindARide.FRAGMENT_TAG).commit();
        }
    }

    private void initNavDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerPrimary = (ListView) findViewById(R.id.drawer_listview);
        mDrawerSecondary = (FrameLayout) findViewById(R.id.drawer_secondary);

        mDrawerToggle = new DrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 0, 0);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // set adapter
        mDrawerItems = DrawerItem.InitDrawerItems();
        
        mDrawerPrimary.setAdapter(new DrawerLayoutAdapter(this));
        mDrawerPrimary.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerPrimary.setItemChecked(DEFAULT_PAGE_POSITION, true);
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
        getSupportActionBar().setTitle(mDrawerItems[position].getTitle());
        switch (position) {
            case 0: // My Profile
                mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, new FragmentMyProfile()).commit();
                break;

            case 1: // Find a ride
                mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, new FragmentFindARide()).commit();
                LayoutInflater.from(this).inflate(R.layout.filter_view, mDrawerSecondary, true);
                break;
                
            case 2: // Post a Ride
                mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, new FragmentPostARide()).commit();
                
            case 3: // Sign out
            
        }
        mDrawerLayout.closeDrawer(mDrawerPrimary);
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
            if (mDrawerLayout.isDrawerOpen(mDrawerPrimary)) {
                mDrawerLayout.closeDrawer(mDrawerPrimary);
            } else {
                mDrawerLayout.openDrawer(mDrawerPrimary);
            }
            
            if (mDrawerLayout.isDrawerOpen(mDrawerSecondary)) {
                mDrawerLayout.closeDrawer(mDrawerSecondary);
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
