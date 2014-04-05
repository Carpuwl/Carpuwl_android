package com.dreamteam.hackwaterloo.adapters;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Feed {

    @SerializedName("feed")
    private Event[] feeds;
    private String message;
    private int success;

    public Event[] getEvents() {
        return feeds;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }

    public static class Event implements Parcelable {

        @SerializedName("depart_date")
        private long departDate;
        @SerializedName("eta")
        private long arrivalTime;
        @SerializedName("end_point")
        private String endPoint;
        private double price;
        private double rating;
        @SerializedName("seats_rem")
        private int seatsRemaining;
        @SerializedName("start_point")
        private String startPoint;
        @SerializedName("event_pk")
        private int eventId;
        @SerializedName("user_fk")
        private long userId;
        @SerializedName("name")
        private String driverName;
        @SerializedName("num_ratings")
        private int ratingCount;
        private String description;
        private String phone;

        public Event(String startPoint, String endPoint, double price, int seatsRemaining,
                long departDate, long eta, long facebookForeignKey, String description) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.price = price;
            this.seatsRemaining = seatsRemaining;
            this.departDate = departDate;
            this.arrivalTime = eta;
            this.userId = facebookForeignKey;
            this.description = description;
        }

        public long getDepartDate() {
            return departDate;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public double getPrice() {
            return price;
        }

        public double getRating() {
            return rating;
        }

        public int getSeatsRemaining() {
            return seatsRemaining;
        }

        public String getStartPoint() {
            return startPoint;
        }

        public int getEventId() {
            return eventId;
        }

        public long getArrivalTime() {
            return arrivalTime;
        }

        public long getUserId() {
            return userId;
        }

        public int getRatingCount() {
            return ratingCount;
        }

        public String getDriverName() {
            return driverName;
        }

        public String getDescription() {
            return description;
        }

        public String getPhone() {
            return phone;
        }

        protected Event(Parcel in) {
            departDate = in.readLong();
            arrivalTime = in.readLong();
            endPoint = in.readString();
            price = in.readDouble();
            rating = in.readDouble();
            seatsRemaining = in.readInt();
            startPoint = in.readString();
            eventId = in.readInt();
            userId = in.readLong();
            driverName = in.readString();
            ratingCount = in.readInt();
            description = in.readString();
            phone = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(departDate);
            dest.writeLong(arrivalTime);
            dest.writeString(endPoint);
            dest.writeDouble(price);
            dest.writeDouble(rating);
            dest.writeInt(seatsRemaining);
            dest.writeString(startPoint);
            dest.writeInt(eventId);
            dest.writeLong(userId);
            dest.writeString(driverName);
            dest.writeInt(ratingCount);
            dest.writeString(description);
            dest.writeString(phone);
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
    }
}
