package com.dreamteam.hackwaterloo.volley;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * Singleton implementation for the Volley Networking Library.
 */
public enum MyVolley {
    
    INSTANCE;
    
    private static final String TAG = MyVolley.class.getSimpleName();
    
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    
    public static void init(Context context) {
        if (INSTANCE.mRequestQueue != null || INSTANCE.mImageLoader != null) {
            Log.w(TAG, "Volley already initialized");
        }
        
        INSTANCE.mRequestQueue = Volley.newRequestQueue(context);
        
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();

        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;
        INSTANCE.mImageLoader = new ImageLoader(INSTANCE.mRequestQueue, new BitmapLruCache(
                cacheSize));
    }
    
    public static RequestQueue getRequestQueue() {
        if (INSTANCE.mRequestQueue != null) {
            return INSTANCE.mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
    
    public static ImageLoader getImageLoader() {
        if (INSTANCE.mImageLoader != null) {
            return INSTANCE.mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }
    
    public static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {
        public BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }
    }
}
