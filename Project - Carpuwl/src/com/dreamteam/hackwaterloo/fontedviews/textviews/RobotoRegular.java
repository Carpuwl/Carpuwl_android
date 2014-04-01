package com.dreamteam.hackwaterloo.fontedviews.textviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dreamteam.hackwaterloo.fontedviews.Typefaces;

public class RobotoRegular extends TextView {

    public RobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            setTypeface(Typefaces.get(getContext(), "Roboto-Regular"));
        }
    }
}