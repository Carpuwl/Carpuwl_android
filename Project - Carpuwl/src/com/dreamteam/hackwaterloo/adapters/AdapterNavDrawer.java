package com.dreamteam.hackwaterloo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.models.NavDrawerItem;
import com.dreamteam.hackwaterloo.utils.Utils;

public class AdapterNavDrawer extends BaseAdapter {

    private Context mContext;

    /**
     * @param context A context for the arrayAdapter to grab a layoutInflater instance
     */
    public AdapterNavDrawer(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return NavDrawerItem.values().length;
    }

    @Override
    public NavDrawerItem getItem(int position) {
        return NavDrawerItem.values()[position];
    }

    @Override
    public long getItemId(int position) {
        // Not used by android framework. No implementation needed.
        return 0;
    }
    
    /**
     * @author Ryan
     *
     */
    private static class ViewHolder {
        ImageView itemIcon;
        TextView itemTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.listview_item_drawer, null);
            viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.nav_drawer_icon);
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.nav_drawer_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        NavDrawerItem drawerItem = getItem(position);
        viewHolder.itemIcon.setImageDrawable(Utils.getDrawable(drawerItem.getIconId()));
        viewHolder.itemTitle.setText(Utils.getString(drawerItem.getTitleId()));
        
        return convertView;
    }
}