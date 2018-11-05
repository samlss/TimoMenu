package me.samlss.timomenu.animation;

import android.animation.Animator;
import android.view.animation.Animation;

import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The flip animation for item view when display.
 */
public class FlipItemAnimation extends ItemAnimation{

    @Override
    public Animation getAnimation(TimoItemView itemView, int positionOfVisibleViews) {
        return AnimationUtils.getItemFlipAnimation(itemView.getWidth(),
                itemView.getHeight());
    }

    @Override
    public Animator getAnimator(TimoItemView itemView, int positionOfVisibleViews) {
        return null;
    }

    public static FlipItemAnimation create(){
        return new FlipItemAnimation();
    }
}
