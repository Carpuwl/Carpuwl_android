
package com.dreamteam.hackwaterloo.models;

import com.dreamteam.carpuwl.R;

public enum NavDrawerItem {

    MY_PROFILE(R.drawable.ic_action_person_selector, R.string.drawer_item_my_profile),
    FIND_A_RIDE(R.drawable.ic_action_search_selector, R.string.drawer_item_find_a_ride),
    POST_A_RIDE(R.drawable.ic_action_edit_selector, R.string.drawer_item_post_a_ride),
    LOG_OUT(R.drawable.ic_action_secure_selector, R.string.drawer_item_log_out);

    private int mIconId;
    private int mTitleId;

    private NavDrawerItem(int iconId, int titleId) {
        mIconId = iconId;
        mTitleId = titleId;
    }

    public int getIconId() {
        return mIconId;
    }

    public int getTitleId() {
        return mTitleId;
    }

    public int getListPosition() {
        return this.ordinal();
    }
}
