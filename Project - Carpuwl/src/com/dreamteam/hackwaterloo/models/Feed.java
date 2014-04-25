
package com.dreamteam.hackwaterloo.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("feed")
    private Event[] mEvents;
    private String message;
    private int success;

    public Event[] getEvents() {
        return mEvents;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public static final class SerializedNames {
        public static final String USER_ID = "user_fk";
        public static final String FACEBOOK_ID = "fb_fk";
        public static final String EVENT_ID = "event_pk";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String RATING = "rating";
        public static final String RATING_COUNT = "num_ratings";
        public static final String PRICE = "price";
        public static final String SEATS_REMAINING = "seats_rem";
        public static final String DESCRIPTION = "description";
        public static final String DATE_DEPART = "depart_date";
        public static final String DATE_ARRIVE = "eta";
        public static final String LOCATION_START = "start_point";
        public static final String LOCATION_END = "end_point";
    }

    public static class Event implements Parcelable {

        @SerializedName(SerializedNames.USER_ID)
        private long mUserId;

        @SerializedName(SerializedNames.FACEBOOK_ID)
        private long mFacebookId;
        
        @SerializedName(SerializedNames.EVENT_ID)
        private long mEventId;

        @SerializedName(SerializedNames.NAME)
        private String mDriverName;

        @SerializedName(SerializedNames.PHONE)
        private String mPhone;

        @SerializedName(SerializedNames.RATING)
        private double mRating;

        @SerializedName(SerializedNames.RATING_COUNT)
        private int mRatingCount;

        @SerializedName(SerializedNames.PRICE)
        private double mPrice;

        @SerializedName(SerializedNames.SEATS_REMAINING)
        private int mSeatsRemaining;

        @SerializedName(SerializedNames.DESCRIPTION)
        private String mDescription;

        @SerializedName(SerializedNames.DATE_DEPART)
        private long mDateDepart;

        @SerializedName(SerializedNames.DATE_ARRIVE)
        private long mDateArrive;

        @SerializedName(SerializedNames.LOCATION_START)
        private String mLocationStart;

        @SerializedName(SerializedNames.LOCATION_END)
        private String mLocationEnd;
        
        protected Event(Parcel in) {
            mUserId = in.readLong();
            mFacebookId = in.readLong();
            mEventId = in.readLong();
            mDriverName = in.readString();
            mPhone = in.readString();
            mRating = in.readDouble();
            mRatingCount = in.readInt();
            mPrice = in.readDouble();
            mSeatsRemaining = in.readInt();
            mDescription = in.readString();
            mDateDepart = in.readLong();
            mDateArrive = in.readLong();
            mLocationStart = in.readString();
            mLocationEnd = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(mUserId);
            dest.writeLong(mFacebookId);
            dest.writeLong(mEventId);
            dest.writeString(mDriverName);
            dest.writeString(mPhone);
            dest.writeDouble(mRating);
            dest.writeInt(mRatingCount);
            dest.writeDouble(mPrice);
            dest.writeInt(mSeatsRemaining);
            dest.writeString(mDescription);
            dest.writeLong(mDateDepart);
            dest.writeLong(mDateArrive);
            dest.writeString(mLocationStart);
            dest.writeString(mLocationEnd);
        }

        public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
            @Override
            public Event createFromParcel(Parcel in) {
                return new Event(in);
            }

            @Override
            public Event[] newArray(int size) {
                return new Event[size];
            }
        };

        public long getUserId() {
            return mUserId;
        }
        
        public long getFacebookId() {
            return mFacebookId;
        }

        public long getEventId() {
            return mEventId;
        }

        public String getDriverName() {
            return mDriverName;
        }

        public String getPhone() {
            return mPhone;
        }

        public double getRating() {
            return mRating;
        }

        public int getRatingCount() {
            return mRatingCount;
        }

        public double getPrice() {
            return mPrice;
        }

        public int getSeatsRemaining() {
            return mSeatsRemaining;
        }

        public String getDescription() {
            return mDescription;
        }

        public long getDateDepart() {
            return mDateDepart;
        }

        public long getDateArrive() {
            return mDateArrive;
        }

        public String getLocationStart() {
            return mLocationStart;
        }

        public String getLocationEnd() {
            return mLocationEnd;
        }

        @Override
        public String toString() {
            return "Event [mUserId=" + mUserId + ", mEventId=" + mEventId + ", mDriverName="
                    + mDriverName + ", mPhone=" + mPhone + ", mRating=" + mRating
                    + ", mRatingCount=" + mRatingCount + ", mPrice=" + mPrice
                    + ", mSeatsRemaining=" + mSeatsRemaining + ", mDescription=" + mDescription
                    + ", mDateDepart=" + mDateDepart + ", mDateArrive=" + mDateArrive
                    + ", mLocationStart=" + mLocationStart + ", mLocationEnd=" + mLocationEnd + "]";
        }
    }
}
