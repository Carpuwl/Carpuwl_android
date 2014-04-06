package com.dreamteam.hackwaterloo.adapters;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.models.DrawerItem;
import com.dreamteam.hackwaterloo.utils.Utils;

public class DrawerLayoutAdapter extends BaseAdapter {

    private ArrayList<DrawerItem> mItems;
    private WeakReference<Context> mContext;

    public DrawerLayoutAdapter(Context context) {
        mContext = new WeakReference<Context>(context);
        mItems = new ArrayList<DrawerItem>(Arrays.asList(DrawerItem.InitDrawerItems()));
    }

    private static class ViewHolder {
        ImageView itemIcon;
        TextView itemTitle;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public DrawerItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Not used by android framework. No implementation needed.
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItem drawerItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext.get());
            convertView = inflater.inflate(R.layout.listview_item_drawer, null);
            viewHolder.itemIcon = (ImageView) convertView.findViewById(R.id.nav_drawer_icon);
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.nav_drawer_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.itemIcon.setImageDrawable(Utils.getDrawable(drawerItem.getIconId()));
        viewHolder.itemTitle.setText(drawerItem.getTitle());
        
        return convertView;
    }
}