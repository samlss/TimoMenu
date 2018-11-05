package me.samlss.timomenu.animation;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description utils of animation
 */
public class AnimationUtils {
    private AnimationUtils(){
        throw new UnsupportedOperationException("Can not be instantiated.");
    }

    public static ObjectAnimator animate(Object target, String property, long delay, long duration,
                                       TypeEvaluator evaluator, int... values) {
        return animate(target, property, delay, duration, evaluator, null, values);
    }

    public static ObjectAnimator animate(Object target, String property, long delay, long duration,
                                         TypeEvaluator evaluator, AnimatorListenerAdapter listenerAdapter, int... values) {
        ObjectAnimator animator = ObjectAnimator.ofInt(target, property, values);
        animator.setStartDelay(delay);
        animator.setDuration(duration);
        animator.setEvaluator(evaluator);
        if (listenerAdapter != null) animator.addListener(listenerAdapter);
        animator.start();
        return animator;
    }

    public static Animation getDefaultShowAnimationFromBottom(){
        Animation animation = new TranslateAnimation(0, 0, 0, 0, Animation.RELATIVE_TO_SELF,
                1, Animation.RELATIVE_TO_SELF, 0);

        animation.setDuration(300);
        return animation;
    }

    public static Animation getDefaultDismissAnimationToBottom(){
        Animation animation = new TranslateAnimation(0, 0, 0, 0, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF, 1);

        animation.setDuration(300);
        return animation;
    }

    public static Animation getDefaultShowAnimationFromTop(){
        Animation animation = new TranslateAnimation(0, 0, 0, 0, Animation.RELATIVE_TO_SELF,
                -1, Animation.RELATIVE_TO_SELF, 0);


        animation.setDuration(300);
        return animation;
    }

    public static Animation getDefaultDismissAnimationToTop(){
        Animation animation = new TranslateAnimation(0, 0, 0, 0, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF, -1);

        animation.setDuration(300);
        return animation;
    }

    public static Animation getDefaultShowScaleAnimation(){
        Animation animation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(300);
        return animation;
    }

    public static Animation getDefaultDismissScaleAnimation(){
        Animation animation = new ScaleAnimation(1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(300);
        return animation;
    }

    public static Animation getDefaultShowAnimation(int gravity){
        switch (gravity){
            case Gravity.TOP:
                return getDefaultShowAnimationFromTop();

            case Gravity.BOTTOM:
                return getDefaultShowAnimationFromBottom();

        }

        return getDefaultShowScaleAnimation();
    }

    public static Animation getDefaultDismissAnimation(int gravity){
        switch (gravity){
            case Gravity.TOP:
                return getDefaultDismissAnimationToTop();

            case Gravity.BOTTOM:
                return getDefaultDismissAnimationToBottom();

        }

        return getDefaultDismissScaleAnimation();
    }

    public static float getFlipAnimationRandomDegree(){
        return (float) (60 + Math.random() * 20);
    }

    public static int getFlipAnimationRandomDuration(){
        return (int) (200 + Math.random() * 100);
    }

    public static Animation getItemFlipAnimation(int width, int height){
        final float degree = AnimationUtils.getFlipAnimationRandomDegree();
        final int duration = AnimationUtils.getFlipAnimationRandomDuration();

        Animation animation = new FlipAnimation(-degree, degree,
                width / 2, height / 2, duration, true);

        return animation;
    }

    public static Animation getItemScaleAnimation(float fromScale, float toScale,
                                                  int startOffset){
        Animation animation = new ScaleAnimation(fromScale, toScale, fromScale, toScale, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(400);
        animation.setStartOffset(startOffset);
        return animation;
    }

    public static Animation getItemBombAnimation(int startOffset){
        Animation animation = new TranslateAnimation(0, 0, 120, 0);
        animation.setDuration(400);
        animation.setStartOffset(startOffset);
        animation.setInterpolator(new OvershootInterpolator());
        return animation;
    }
}
