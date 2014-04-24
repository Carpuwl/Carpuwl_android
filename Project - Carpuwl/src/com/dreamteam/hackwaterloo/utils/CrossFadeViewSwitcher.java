package com.dreamteam.hackwaterloo.utils;

import java.lang.ref.WeakReference;

import android.view.View;

import com.dreamteam.hackwaterloo.common.OnAnimationEndListener;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class CrossFadeViewSwitcher {
    
    private static final int ANIMATION_DURATION = 400;
    
    private OnAnimationEndListener mOnAnimationEndListener;
    private AnimatorSet mAnimatorSet;
    private WeakReference<View> mViewInitial;
    private WeakReference<View> mViewFinal;
    
    private boolean mPlaySequential;
    
    public CrossFadeViewSwitcher (View viewInitial, View viewFinal, boolean playSequential) {
        mViewInitial = new WeakReference<View> (viewInitial);
        mViewFinal = new WeakReference<View> (viewFinal);
        mPlaySequential = playSequential;
        mAnimatorSet = new AnimatorSet();
    }
    
    public void setOnAnimationEndListener(OnAnimationEndListener listener) {
        mOnAnimationEndListener = listener;
    }
    
    public void startAnimation() {
        final View viewInitial = mViewInitial.get();
        final View viewFinal = mViewFinal.get();
        
        if (viewInitial != null && viewFinal != null) {
            mAnimatorSet = new AnimatorSet();
            
            mAnimatorSet.setDuration(ANIMATION_DURATION);
            ViewHelper.setAlpha(viewFinal, 0f);
            viewFinal.setVisibility(View.VISIBLE);
            
            if (mPlaySequential) {
                playSequential(mAnimatorSet, viewInitial, viewFinal);
            } else {
                playTogether(mAnimatorSet, viewInitial, viewFinal);
            }
            
            mAnimatorSet.addListener(new AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewInitial.setVisibility(View.GONE);
                    if (mOnAnimationEndListener != null) {
                        mOnAnimationEndListener.onAnimationEnd();
                    }
                }
                
                @Override
                public void onAnimationStart(Animator animation) {
                    // No implementation
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // No implementation
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // No implementation
                }
            });
        }
    }
    
    private void playTogether(AnimatorSet animatorSet, View viewInitial, View viewFinal) {
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(viewInitial, "alpha", 0f),
                ObjectAnimator.ofFloat(viewFinal, "alpha", 1f));
        animatorSet.start();
    }
    
    private void playSequential(AnimatorSet animatorSet, View viewInitial, View viewFinal) {
        animatorSet.playSequentially(
                ObjectAnimator.ofFloat(viewInitial, "alpha", 0f),
                ObjectAnimator.ofFloat(viewFinal, "alpha", 1f));
        animatorSet.start();
    }
}
