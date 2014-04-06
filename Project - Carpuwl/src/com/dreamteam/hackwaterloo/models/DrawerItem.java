package com.dreamteam.hackwaterloo.models;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.utils.Utils;

public class DrawerItem {
    
    private static final int DRAWER_ITEMS_COUNT = 4;
    public static final int POSITION_MY_PROFILE = 0;
    public static final int POSITION_FIND_A_RIDE = 1;
    public static final int POSITION_POST_A_RIDE = 2;
    public static final int POSITION_LOG_OUT = 3;

    private int mIconId;
    private String mTitle;
    
    public static DrawerItem[] InitDrawerItems() {
        DrawerItem[] drawerItems = new DrawerItem[DRAWER_ITEMS_COUNT];
        drawerItems[POSITION_MY_PROFILE] = new DrawerItem(R.drawable.ic_action_person, R.string.drawer_item_my_profile);
        drawerItems[POSITION_FIND_A_RIDE] = new DrawerItem(R.drawable.ic_action_search, R.string.drawer_item_find_a_ride);
        drawerItems[POSITION_POST_A_RIDE] = new DrawerItem(R.drawable.ic_action_edit, R.string.drawer_item_post_a_ride);
        drawerItems[POSITION_LOG_OUT] = new DrawerItem(R.drawable.ic_action_edit, R.string.drawer_item_log_out);
        return drawerItems;
    }

    private DrawerItem(int iconId, int stringId) {
        mIconId = iconId;
        mTitle = Utils.getString(stringId);
    }

    public int getIconId() {
        return mIconId;
    }

    public String getTitle() {
        return mTitle;
    }
}
