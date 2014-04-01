package com.dreamteam.hackwaterloo.fontedviews.textviews;

import com.dreamteam.hackwaterloo.fontedviews.Typefaces;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoCondensedRegular extends TextView {
    public RobotoCondensedRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, "RobotoCondensed-Regular"));
        }
    }
}
