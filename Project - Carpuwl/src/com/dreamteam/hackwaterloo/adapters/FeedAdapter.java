package com.dreamteam.hackwaterloo.adapters;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.Constants;
import com.dreamteam.hackwaterloo.activities.ActivityDetailedPager;
import com.dreamteam.hackwaterloo.adapters.Feed.Event;

public class FeedAdapter extends BaseAdapter {
    
    private WeakReference<Activity> mContext;
    private ArrayList<Event> mEvents;
    private Resources resources;
    private int[] mColorList;
    
    public FeedAdapter(Activity context, List<Event> events) {
        mContext = new WeakReference<Activity>(context);
        mEvents = new ArrayList<Event>();
        mEvents.addAll(events);
        resources = context.getResources();
        
        mColorList = new int[]{
                resources.getColor(R.color.red),
                resources.getColor(R.color.orange),
                resources.getColor(R.color.green),
                resources.getColor(R.color.blue),
                resources.getColor(R.color.purple)
        };
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Event getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // Not used by android framework
        return 0;
    }
    
    public static class ViewHolder {
        public RelativeLayout parentView;
        public ImageView priceBackground;
        public RatingBar ratingBar;
        public TextView price;
        public TextView startingPoint;
        public TextView endingPoint;
        public TextView rating;
        public TextView seats;
        public TextView timeValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext.get());
            convertView = inflater.inflate(R.layout.listview_item_feed_posting, null, false);
            viewHolder = new ViewHolder();
            viewHolder.parentView = (RelativeLayout) convertView.findViewById(R.id.find_ride_listview_item_parent);
            viewHolder.priceBackground = (ImageView) convertView.findViewById(R.id.find_ride_listview_item_price_background);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.find_ride_rating_bar);
            viewHolder.price = (TextView) convertView.findViewById(R.id.find_ride_listview_item_price);
            viewHolder.startingPoint = (TextView) convertView.findViewById(R.id.find_ride_listview_item_start);
            viewHolder.endingPoint = (TextView) convertView.findViewById(R.id.find_ride_listview_item_end);
            viewHolder.seats = (TextView) convertView.findViewById(R.id.find_ride_listview_item_seats);
            viewHolder.timeValue = (TextView) convertView.findViewById(R.id.find_ride_listview_item_time_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        Event event = getItem(position);
        viewHolder.parentView.setOnClickListener(new OnEventClickedListener(position));
        viewHolder.priceBackground.setBackgroundColor(mColorList[position % 5]);
        viewHolder.ratingBar.setRating(event.getRating());
        viewHolder.price.setText(String.format("$%.2f", event.getPrice()));
        viewHolder.startingPoint.setText(event.getStartPoint());
        viewHolder.endingPoint.setText(event.getEndPoint());
        viewHolder.seats.setText(String.format(resources.getString(R.string.find_ride_seats), event.getSeatsRemaining())) ;
        viewHolder.timeValue.setText(new SimpleDateFormat("MMM dd, h a").format(event.getDepartDate())); 
        
        return convertView;
    }
    
    private class OnEventClickedListener implements OnClickListener {
        
        private int position;
        
        public OnEventClickedListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext.get(), ActivityDetailedPager.class);
            intent.putParcelableArrayListExtra(Constants.Extra.Event, mEvents);
            intent.putExtra(Constants.Extra.EventPosition, position);
            mContext.get().startActivity(intent);
        }
    }
}