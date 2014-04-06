package com.dreamteam.hackwaterloo.models;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.utils.Utils;

public class DrawerItem {
    
    private static final int DRAWER_ITEMS_COUNT = 4;

    private int mIconId;
    private String mTitle;
    
    public static DrawerItem[] InitDrawerItems() {
        DrawerItem[] drawerItems = new DrawerItem[DRAWER_ITEMS_COUNT];
        drawerItems[0] = new DrawerItem(R.drawable.ic_action_person, R.string.drawer_item_my_profile);
        drawerItems[1] = new DrawerItem(R.drawable.ic_action_search, R.string.drawer_item_find_a_ride);
        drawerItems[2] = new DrawerItem(R.drawable.ic_action_edit, R.string.drawer_item_post_a_ride);
        drawerItems[3] = new DrawerItem(R.drawable.ic_action_edit, R.string.drawer_item_log_out);
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
