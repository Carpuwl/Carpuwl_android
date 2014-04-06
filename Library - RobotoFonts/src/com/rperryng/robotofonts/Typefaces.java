package com.rperryng.robotofonts;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class Typefaces {
    
    public static final String ROBOTO_REGULAR = "Roboto-Regular";
    public static final String ROBOTO_MEDIUM = "Roboto-Medium";
    public static final String ROBOTO_LIGHT = "Roboto-Light";
    
    public static final String ROBOTO_CONDENSED_REGULAR = "RobotoCondensed-Regular";

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("fonts/%s.ttf", name));
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }
}