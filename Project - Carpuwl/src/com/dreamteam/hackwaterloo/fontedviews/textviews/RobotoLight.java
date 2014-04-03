package com.dreamteam.hackwaterloo.fontedviews.textviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dreamteam.hackwaterloo.Typefaces;

public class RobotoLight extends TextView {
    public RobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, "Roboto-Light"));
        }
    }
}
