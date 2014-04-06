package com.dreamteam.hackwaterloo;


public enum AppData {
    INSTANCE;

    private String name;
    private String phoneNumber;
    private long facebookForeignKey;
    
    public static AppData construct(String name, String phoneNumber, long facebookForeignKey) {
        INSTANCE.name = name;
        INSTANCE.phoneNumber = phoneNumber;
        INSTANCE.facebookForeignKey = facebookForeignKey;
        return INSTANCE;
    }

    public static String getName() {
        return INSTANCE.name;
    }

    public static String getPhoneNumber() {
        return INSTANCE.phoneNumber;
    }

    public static long getFacebookForeginKey() {
        return INSTANCE.facebookForeignKey;
    }
}
