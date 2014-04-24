
package com.dreamteam.hackwaterloo.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.dreamteam.hackwaterloo.App;
import com.dreamteam.hackwaterloo.common.Constants;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class AnimationCardEntrance {

    private static final float ROTATION_OFFSET = 30f;
    private static final float DECELLERATION_INTERPOLATION_FACTOR = 2f;
    private static final int TRANSLATION_DISTANCE_DP = 100;
    private static int sTranslationDistance;
    private View mView;

    private AnimationCardEntrance(View view) {
        mView = view;
    }

    public static void withView(View view) {
        new AnimationCardEntrance(view).start();
    }

    private int getTranslationDistance() {
        if (sTranslationDistance == 0) {
            DisplayMetrics metrics = App.getAppContext().getResources().getDisplayMetrics();
            sTranslationDistance = (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, TRANSLATION_DISTANCE_DP, metrics);
        }
        return sTranslationDistance;
    }

    private void start() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mView, "rotationX", ROTATION_OFFSET, 0f),
                ObjectAnimator.ofFloat(mView, "translationY", getTranslationDistance(), 0f),
                ObjectAnimator.ofFloat(mView, "alpha", 0f, 1f));

        animatorSet.setDuration(Constants.Animation.DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator(DECELLERATION_INTERPOLATION_FACTOR));
        animatorSet.start();
    }

}
