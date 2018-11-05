package me.samlss.timomenu.animation;

import android.animation.Animator;
import android.view.animation.Animation;

import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The bomb animation for item view when display.
 */
public class BombItemAnimation extends ItemAnimation{
    @Override
    public Animation getAnimation(TimoItemView itemView, int positionOfVisibleViews) {
        return AnimationUtils.getItemBombAnimation(positionOfVisibleViews * 100);
    }

    @Override
    public Animator getAnimator(TimoItemView itemView, int positionOfVisibleViews) {
        return null;
    }

    public static BombItemAnimation create(){
        return new BombItemAnimation();
    }
}
