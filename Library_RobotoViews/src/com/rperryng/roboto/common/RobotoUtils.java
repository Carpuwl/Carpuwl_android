
package com.rperryng.roboto.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

/**
 * Helper class for doing all the heavylifting when grabbing Roboto Typefaces
 * from their raw .ttf file, or from the cache if they have been called before
 * during this sessions.
 * 
 * @author Ryan Perry-Nguyen
 */
public class RobotoUtils {

    // Singleton HashMap to cache the created typefaces
    private enum TypefaceHolder {
        INSTANCE;

        private SparseArray<Typeface> mTypefaceCache;

        static SparseArray<Typeface> getCacheInstance() {
            if (INSTANCE.mTypefaceCache == null) {
                INSTANCE.mTypefaceCache = new SparseArray<Typeface>();
            }
            return INSTANCE.mTypefaceCache;
        }
    }

    /**
     * @param context A Context to access the resources of the application
     * @param robotoFont The desired font, as represented by one of the
     *            {@link RobotoFont} values
     * @return The desired typeface, or the default typeface if the desired
     *         typeface could not be created
     */
    public static Typeface getTypeface(Context context, RobotoFont robotoFont) {
        int key = robotoFont.getRawFontResourceId();
        SparseArray<Typeface> cache = TypefaceHolder.getCacheInstance();
        
        // Attempt to grab the desired Typeface from the cache
        Typeface typeface = cache.get(key, null);
        
        if (typeface == null) {
            // The typeface isn't cached, attempt to create a new instance of
            // the desired Typeface
            typeface = createTypefaceFromResource(context, robotoFont.getRawFontResourceId());

            if (typeface != null) {
                // Typeface successfully created. cache the instance.
                cache.put(key, typeface);

            } else {
                // Couldn't create the desired typeface. Fallback to the default
                // typeface
                typeface = Typeface.DEFAULT;
            }
        } 

        return typeface;
    }

    /**
     * http://stackoverflow.com/questions/7610355/font-in-android-library
     * Creates a typeface that has been placed within the res/raw directory
     * 
     * @param context A context to access the application's resources
     * @param resourceId The id of the raw resource file
     * @return The desired typeface, or null if it could not be created.
     */
    private static Typeface createTypefaceFromResource(Context context, int resourceId) {

        Typeface typeface = null;
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        String outPath = context.getCacheDir() + "/tmp.raw";

        // extract the resource to a temporary file.
        try {
            byte[] buffer = new byte[inputStream.available()];
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(
                    outPath));

            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bufferLength);
            }
            outputStream.close();

            // Create the Typeface from the temporary file
            typeface = Typeface.createFromFile(outPath);

            // Remove the (no longer needed) temporary file
            new File(outPath).delete();

        } catch (IOException e) {
            // Couldn't create file.
            typeface = null;
        }

        return typeface;
    }
}
