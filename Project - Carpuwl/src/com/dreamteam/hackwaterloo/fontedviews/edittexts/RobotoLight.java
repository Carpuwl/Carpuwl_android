package com.dreamteam.hackwaterloo.fontedviews.edittexts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.dreamteam.hackwaterloo.fontedviews.Typefaces;

public class RobotoLight extends EditText {
    public RobotoLight(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            setTypeface(Typefaces.get(context, "Roboto-Light"));
        }
    }
}
