package me.samlss.timomenu.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.Animation;

import java.util.Random;

import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The bounce animator for item view when display.
 */
public class BounceItemAnimation extends ItemAnimation{
    @Override
    public Animation getAnimation(TimoItemView itemView, int positionOfVisibleViews) {
        return null;
    }

    @Override
    public Animator getAnimator(TimoItemView itemView, int positionOfVisibleViews) {
        Animator animator = ObjectAnimator.ofFloat(itemView, "translationY", 0, 0, -30, 0, -15, 0, 0);
        animator.setDuration(1000 + new Random().nextInt(500));
        return animator;
    }

    public static BounceItemAnimation create(){
        return new BounceItemAnimation();
    }
}
