
package com.dreamteam.hackwaterloo.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
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
import com.dreamteam.hackwaterloo.activities.ActivityDetailedPager;
import com.dreamteam.hackwaterloo.common.Constants;
import com.dreamteam.hackwaterloo.models.Feed.Event;
import com.dreamteam.hackwaterloo.utils.AnimationCardEntrance;
import com.dreamteam.hackwaterloo.utils.Utils;

public class FeedAdapter extends BaseAdapter {

    private static final int POSITION_TO_PROMPT_FILTER = 10;

    private OnScrollToShowPromptListener mListener;

    private Activity mActivity;
    private ArrayList<Event> mEvents;
    private TypedArray mColorList;

    private boolean promptShown;
    private int mLastDrawnViewPosition;

    public FeedAdapter(Activity activity, OnScrollToShowPromptListener listener) {
        mListener = listener;
        mActivity = activity;
        mEvents = new ArrayList<Event>();
        promptShown = false;
        mColorList = Utils.obtainTypedArray(R.array.find_ride_post_colors);
        mLastDrawnViewPosition = -1;
    }

    public interface OnScrollToShowPromptListener {
        void onScrollToShowPrompt();
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
    public long getItemId(int position) {
        // No Implementation. Not used by android framework.
        return 0;
    }

    public void addAll(Collection<? extends Event> newEvents) {
        mEvents.addAll(newEvents);
        notifyDataSetChanged();
    }

    public void replaceDataset(List<Event> events) {
        mEvents.clear();
        mEvents.addAll(events);
        notifyDataSetChanged();
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
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(R.layout.listview_item_feed_posting, null, false);
            viewHolder = new ViewHolder();

            viewHolder.parentView = (RelativeLayout) convertView
                    .findViewById(R.id.find_ride_listview_item_parent);
            viewHolder.priceBackground = (ImageView) convertView
                    .findViewById(R.id.find_ride_listview_item_price_background);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.find_ride_rating_bar);
            viewHolder.price = (TextView) convertView
                    .findViewById(R.id.find_ride_listview_item_price);
            viewHolder.startingPoint = (TextView) convertView
                    .findViewById(R.id.find_ride_listview_item_start);
            viewHolder.endingPoint = (TextView) convertView
                    .findViewById(R.id.find_ride_listview_item_end);
            viewHolder.seats = (TextView) convertView
                    .findViewById(R.id.find_ride_listview_item_seats);
            viewHolder.timeValue = (TextView) convertView
                    .findViewById(R.id.find_ride_listview_item_time_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = getItem(position);
        viewHolder.parentView.setOnClickListener(new OnEventClickedListener(position));
        viewHolder.priceBackground.setBackgroundColor(mColorList.getColor((position % 5), 0));
        viewHolder.ratingBar.setRating((float) event.getRating());
        viewHolder.price.setText(String.format("%s%.2f", Utils.getCurrencySymbol(),
                event.getPrice()));
        viewHolder.startingPoint.setText(event.getLocationStart());
        viewHolder.endingPoint.setText(event.getLocationEnd());
        viewHolder.seats.setText(String.format(Utils.getString(R.string.find_ride_seats),
                event.getSeatsRemaining()));
        viewHolder.timeValue.setText(Utils.multiCaseDateFormat(event.getDateDepart()));

        if (position > POSITION_TO_PROMPT_FILTER && !promptShown) {
            promptShown = true;
            mListener.onScrollToShowPrompt();
        }

        if (position > mLastDrawnViewPosition) {
            AnimationCardEntrance.withView(convertView);
            mLastDrawnViewPosition = position;
        }

        return convertView;
    }

    private class OnEventClickedListener implements OnClickListener {

        private int position;

        public OnEventClickedListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, ActivityDetailedPager.class);
            intent.putParcelableArrayListExtra(Constants.Extra.EVENT, mEvents);
            intent.putExtra(Constants.Extra.EVENT_POSITION, position);
            mActivity.startActivity(intent);
        }
    }
}
