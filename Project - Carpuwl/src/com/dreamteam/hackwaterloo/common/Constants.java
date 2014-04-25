package com.dreamteam.hackwaterloo.common;

public class Constants {
    
    public enum Endpoint {
        USER("user"),
        EVENT("event"), 
        FEED("feed");
        
        private String mValue;
        
        private Endpoint(String value) {
            this.mValue = value;
        }
        
        public String getValue() {
            return mValue;
        }
    }
    
    public static class Animation {
        public static final int DURATION = 400;
    }
    
    public static class Defaults {
        public static final int MINIMUM_SEATS = 1;
        public static final int MINIMUM_PRICE = 1;
        public static final long MINIMUM_WHEN_OFFSET = 1000 * 60 * 60 * 24 * 2; // Two days
    }
    
    public static class RequestCode {
        public static final int FILTER = 0;
    }
    
    public static class Extra {
        public static final String EVENT = "com.dreamteam.carpuwl.eventParcelable";
        public static final String EVENT_POSITION = "com.dreamteam.carpuwl.eventPosition";
        public static final String EVENT_SINGLE = "com.dreamteam.carpuwl.eventMultiple";
    }
    
    public static class StatusCode {
        public static final int OK = 200;
    }
    
}
