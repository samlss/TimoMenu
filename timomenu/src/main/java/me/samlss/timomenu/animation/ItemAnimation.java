package me.samlss.timomenu.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Specify the display animation for {@link TimoItemView},
 *                you can extend this class to implement a custom display animation of the item view.
 *                You can refer the animations: {@link FlipItemAnimation} or {@link BombItemAnimation} or {@link ScaleItemAnimation}.
 *
 *                You can specify the following two methods to specify the opening animation.
 */
public abstract class ItemAnimation {
    /**
     * Get the animation when the menu is showing.
     *
     * @param itemView The item view.
     * @param positionOfVisibleViews The position in all visible views of the row.
     * @return The animation that executed when the item view is displayed.
     * */
    public abstract Animation getAnimation(TimoItemView itemView, int positionOfVisibleViews);

    /**
     *  Get the animator when the menu is showing.
     *
     * @param itemView The item view.
     * @param positionOfVisibleViews The position in all visible views of the row.
     * @return The animation that executed when the item view is displayed.
     * */
    public abstract Animator getAnimator(TimoItemView itemView, int positionOfVisibleViews);
}
