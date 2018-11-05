package me.samlss.timomenu.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.FrameLayout;

import me.samlss.timomenu.R;
import me.samlss.timomenu.animation.AnimationUtils;
import me.samlss.timomenu.util.ViewUtils;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The back ground view for {@link MenuView}
 */
public class BackgroundView extends FrameLayout {
    private int mDimColor;
    private Animator mAnimator;

    public BackgroundView(Context context) {
        super(context);

        mDimColor = ViewUtils.getColor(getContext(), R.color.default_dimColor);
        setVisibility(INVISIBLE);
    }

    /**
     * Set the background color.
     * */
    public void setDimColor(int dimColor) {
        this.mDimColor = dimColor;
    }

    /**
     * Show this background view
     *
     * @param duration The duration of show.
     * @param listenerAdapter The animation listener.
     * */
    public void show(long duration, AnimatorListenerAdapter listenerAdapter) {
        setVisibility(VISIBLE);
        if (mAnimator != null){
            mAnimator.removeAllListeners();
            mAnimator.cancel();
        }

        mAnimator = AnimationUtils.animate(
                this, "backgroundColor", 0, duration, new ArgbEvaluator(), listenerAdapter,
                Color.TRANSPARENT, mDimColor);
    }

    /**
     * Hide this background view
     *
     * @param duration The duration of show.
     * @param listenerAdapter The animation listener.
     * */
    public void dismiss(long duration, AnimatorListenerAdapter listenerAdapter) {
        if (mAnimator != null){
            mAnimator.removeAllListeners();
            mAnimator.cancel();
        }

        mAnimator = AnimationUtils.animate(
                this, "backgroundColor", 0, duration, new ArgbEvaluator(), listenerAdapter,
                mDimColor, Color.TRANSPARENT);
    }
}
