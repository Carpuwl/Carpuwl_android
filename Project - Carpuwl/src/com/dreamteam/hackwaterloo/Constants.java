package com.dreamteam.hackwaterloo;

public class Constants {
    
    public static final String UTF_8 = "UTF-8";
    public static final String BASE_URL = "http://s417363377.onlinehome.us/";
    
    public enum Endpoint {
        USER("user"),
        CREATE_EVENT("create_event"), // TODO: Make this endpoint use feed instead
        FEED("feed");
        
        private String mValue;
        
        private Endpoint(String value) {
            this.mValue = value;
        }
        
        public String getValue() {
            return mValue;
        }
    }
    
    public static class Defaults {
        public static final int ANIMATION_DURATION = 400;
        public static final int MINIMUM_SEATS = 1;
        public static final double MINIMUM_PRICE = 1d;
        public static final long MINIMUM_WHEN_OFFSET = 1000 * 60 * 60 * 24 * 2; // Two days
    }
    
    public static class RequestCode {
        public static final int FILTER = 0;
    }
    
    public static class Extra {
        public static final String EVENT = "com.dreamteam.carpuwl.eventParcelable";
        public static final String EVENT_POSITION = "com.dreamteam.carpuwl.eventPosition";
    }
    
    public static class FragmentTag {
        public static final String DIALOG_DATE_PICKER = "fragmentTagDatePicker";
        public static final String DIALOG_TIME_PICKER = "fragmenTagTimePicker";
    }
    
    public static class StatusCode {
        public static final int OK = 200;
    }
    
}
