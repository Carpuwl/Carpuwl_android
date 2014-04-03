package com.dreamteam.hackwaterloo.fontedviews.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.dreamteam.hackwaterloo.Typefaces;

public class RobotoLight extends Button {
    public RobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, "Roboto-Light"));
        }
    }
}