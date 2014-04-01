package com.dreamteam.hackwaterloo.adapters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;

public class DrawerLayoutAdapter extends BaseAdapter {
    
    private ArrayList<String> mItems;
    private WeakReference<Context> mContext;
    
    public DrawerLayoutAdapter(Context context, String[] items) {
        mContext = new WeakReference<Context>(context);
        mItems = new ArrayList<String>(Arrays.asList(items));
    }

    private static class ViewHolder {
        TextView itemName;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Not used by android framework. No implementation needed.
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext.get());
            convertView = inflater.inflate(R.layout.drawer_item, null);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.drawer_item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.itemName.setText(getItem(position));
        
        return convertView;
    }
}