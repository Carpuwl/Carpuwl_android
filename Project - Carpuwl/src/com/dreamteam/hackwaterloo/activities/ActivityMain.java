
package com.dreamteam.hackwaterloo.activities;

import java.util.Map;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.adapters.AdapterNavDrawer;
import com.dreamteam.hackwaterloo.fragments.FragmentFilter;
import com.dreamteam.hackwaterloo.fragments.FragmentMyProfile;
import com.dreamteam.hackwaterloo.fragments.FragmentFilter.OnFilterAppliedListener;
import com.dreamteam.hackwaterloo.fragments.FragmentFindARide;
import com.dreamteam.hackwaterloo.fragments.FragmentFindARide.FilterPromptListener;
import com.dreamteam.hackwaterloo.fragments.FragmentPostARide;
import com.dreamteam.hackwaterloo.models.NavDrawerItem;
import com.dreamteam.hackwaterloo.utils.Utils;

public class ActivityMain extends ActivityDreamTeam implements FilterPromptListener,
        OnFilterAppliedListener {

    // private static final String TAG = ActivityMain.class.getSimpleName();
    private static final String KEY_SELECTED_DRAWER_POSITION = "keySelectedDrawerPosition";
    private static final String KEY_CURRENT_TITLE = "keyCurrentTitle";

    private FragmentManager mFragmentManager;
    private LayoutInflater mLayoutInflater;
    private Fragment mFragmentFilter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerPrimary;
    private FrameLayout mDrawerSecondary;

    private int mSelectedDrawerPosition;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        mLayoutInflater = LayoutInflater.from(this);
        mFragmentFilter = mFragmentManager.findFragmentByTag(FragmentFilter.TAG);

        initDrawers();

        if (savedInstanceState == null) {
            mDrawerPrimary.setItemChecked(NavDrawerItem.FIND_A_RIDE.getListPosition(), true);
            mSelectedDrawerPosition = NavDrawerItem.FIND_A_RIDE.getListPosition();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_container, new FragmentFindARide(),
                            FragmentFindARide.TAG)
                    .commit();
            mTitle = Utils.getString(NavDrawerItem.FIND_A_RIDE.getTitleId());
        } else {
            mSelectedDrawerPosition = savedInstanceState.getInt(KEY_SELECTED_DRAWER_POSITION);
            mTitle = savedInstanceState.getCharSequence(KEY_CURRENT_TITLE);
        }

        if (mSelectedDrawerPosition == NavDrawerItem.FIND_A_RIDE.getListPosition()) {
            addOrRestoreSecondaryDrawer();
        }

        setTitle(mTitle);
        if (mDrawerLayout.isDrawerOpen(mDrawerPrimary)) {
            getSupportActionBar().setTitle(Utils.getString(R.string.app_name));
        }
        mDrawerTitle = Utils.getString(R.string.app_name);
    }

    private void initDrawers() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerPrimary = (ListView) findViewById(R.id.drawer_listview);

        mDrawerToggle = new DrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 0, 0);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.LEFT);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow_right, Gravity.RIGHT);

        // set adapter
        mDrawerPrimary.setAdapter(new AdapterNavDrawer(this));
        mDrawerPrimary.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void addOrRestoreSecondaryDrawer() {
        mDrawerSecondary = (FrameLayout) findViewById(R.id.drawer_secondary);

        if (mDrawerSecondary == null) {
            mLayoutInflater.inflate(R.layout.drawer_secondary, mDrawerLayout, true);
            mDrawerSecondary = (FrameLayout) findViewById(R.id.drawer_secondary);
        }

        if (mFragmentFilter == null) {
            mFragmentFilter = new FragmentFilter();
        }

        if (!mFragmentFilter.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.drawer_secondary, mFragmentFilter, FragmentFilter.TAG)
                    .commit();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SELECTED_DRAWER_POSITION, mSelectedDrawerPosition);
        outState.putCharSequence(KEY_CURRENT_TITLE, mTitle);
        super.onSaveInstanceState(outState);
    }

    private class DrawerToggle extends ActionBarDrawerToggle {

        public DrawerToggle(SherlockFragmentActivity activity, DrawerLayout drawerLayout,
                int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {

            super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
                    closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (drawerView.equals(mDrawerPrimary)) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (drawerView.equals(mDrawerPrimary)) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            drawerSelectItem(position);
        }
    }

    private void drawerSelectItem(int position) {
        mDrawerPrimary.setItemChecked(position, true);
        NavDrawerItem selectedNavDrawerItem = NavDrawerItem.values()[position];

        if (mSelectedDrawerPosition != position) {
            mSelectedDrawerPosition = position;
            Fragment theFragmentToSwitchTo = null;
            String fragmentTag = null;

            switch (selectedNavDrawerItem) {
                case MY_PROFILE:
                    theFragmentToSwitchTo = new FragmentMyProfile();
                    fragmentTag = FragmentMyProfile.TAG;
                    break;

                case FIND_A_RIDE:
                    theFragmentToSwitchTo = new FragmentFindARide();
                    fragmentTag = FragmentFindARide.TAG;
                    break;

                case POST_A_RIDE:
                    theFragmentToSwitchTo = new FragmentPostARide();
                    fragmentTag = FragmentPostARide.TAG;
                    break;

                case LOG_OUT:
                    break;

                default:
                    throw new IllegalStateException("Unhandled enum type: "
                            + selectedNavDrawerItem.toString());

            }

            if (theFragmentToSwitchTo != null) {
                mFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, theFragmentToSwitchTo, fragmentTag)
                        .commit();
            }
        }

        if (selectedNavDrawerItem.equals(NavDrawerItem.FIND_A_RIDE)) {
            addOrRestoreSecondaryDrawer();
        } else if (mDrawerSecondary != null) {
            mDrawerSecondary.removeAllViews();
            mDrawerLayout.removeView(mDrawerSecondary);
            mFragmentManager.beginTransaction().remove(mFragmentFilter).commit();
            mDrawerSecondary = null;
        }

        setTitle(Utils.getString(NavDrawerItem.values()[position].getTitleId()));
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
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Hides all Menu Items if the drawer is open
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerPrimary);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                toggleThisDrawerAndCloseOthers(mDrawerPrimary);
                break;

            case R.id.action_find_ride_filter:
                toggleThisDrawerAndCloseOthers(mDrawerSecondary);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleThisDrawerAndCloseOthers(View drawerToToggle) {
        View drawerToClose = (drawerToToggle.equals(mDrawerPrimary)) ? mDrawerSecondary
                : mDrawerPrimary;

        if (mDrawerLayout.isDrawerOpen(drawerToToggle)) {
            mDrawerLayout.closeDrawer(drawerToToggle);
        } else {
            mDrawerLayout.openDrawer(drawerToToggle);
        }

        if (drawerToClose != null && mDrawerLayout.isDrawerOpen(drawerToClose)) {
            mDrawerLayout.closeDrawer(drawerToClose);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivitySplashPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        super.onBackPressed();
    }

    /**
     * The filter prompt has been pressed, open the filter drawer
     */
    @Override
    public void onFilterPromptToBeShown() {
        if (mDrawerSecondary != null) {
            mDrawerLayout.openDrawer(mDrawerSecondary);
        }
    }

    /*
     * A filter was applied from the right hand drawer. Close the drawer and
     * pass the data to the Find A Ride fragment to handle.
     */
    @Override
    public void onFilterApplied(Map<String, String> filterSettings) {
        mDrawerLayout.closeDrawer(mDrawerSecondary);
        FragmentFindARide fragment = (FragmentFindARide) mFragmentManager
                .findFragmentByTag(FragmentFindARide.TAG);
        fragment.updateEvents(filterSettings);
    }
}
