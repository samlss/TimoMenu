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
 * @description The bounce in up animator for item view when display.
 */
public class BounceInUpItemAnimation extends ItemAnimation{
    @Override
    public Animation getAnimation(TimoItemView itemView, int positionOfVisibleViews) {
        return null;
    }

    @Override
    public Animator getAnimator(TimoItemView itemView, int positionOfVisibleViews) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(itemView, "translationY", itemView.getMeasuredHeight(), -30, 10, 0),
                ObjectAnimator.ofFloat(itemView, "alpha", 0, 1, 1, 1)
        );
        animatorSet.setDuration(1000 + new Random().nextInt(500));
        return animatorSet;
    }

    public static BounceInUpItemAnimation create(){
        return new BounceInUpItemAnimation();
    }
}
