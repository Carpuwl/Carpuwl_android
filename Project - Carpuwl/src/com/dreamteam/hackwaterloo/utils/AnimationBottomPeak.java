package com.dreamteam.hackwaterloo.utils;

import java.lang.ref.WeakReference;

import android.util.Log;
import android.view.View;

import com.dreamteam.hackwaterloo.Constants.Defaults;
import com.dreamteam.hackwaterloo.sharedinterfaces.OnAnimationEndListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class AnimationBottomPeak {
    
    private static final String LOG_TAG = AnimationBottomPeak.class.getSimpleName();
    
    private OnAnimationEndListener mListener;
    private WeakReference<View> mView;
    private boolean mShow;
    
    public AnimationBottomPeak(View view, boolean show) {
        mView = new WeakReference<View>(view);
        mShow = show;
    }
    
    public AnimationBottomPeak setOnAnimationEndListener(OnAnimationEndListener listener) {
        mListener = listener;
        return this;
    }
    
    public void startAnimation() {
        final View view = mView.get();
        if (view != null) {
            float value = mShow ? -view.getHeight() : view.getHeight();
            ObjectAnimator peekAnimator = ObjectAnimator.ofFloat(view, "translationY", value);
            peekAnimator.setDuration(Defaults.ANIMATION_DURATION);
            peekAnimator.addListener(new AnimatorListener() {
                
                @Override
                public void onAnimationStart(Animator animation) {
                    ViewHelper.setAlpha(view, 1f);
                    view.setVisibility(View.VISIBLE);
                }
                
                @Override
                public void onAnimationRepeat(Animator animation) {
                    // No implementation
                }
                
                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mListener != null) {
                        mListener.onAnimationEnd();
                    }
                }
                
                @Override
                public void onAnimationCancel(Animator animation) {
                    // No implementation
                }
            });
            peekAnimator.start();
        } else {
            Log.e(LOG_TAG, "Can not animate garbage collected view");
        }
    }
}
