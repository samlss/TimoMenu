package me.samlss.timomenu.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import me.samlss.timomenu.animation.AnimationUtils;
import me.samlss.timomenu.animation.ItemAnimation;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The {@link HorizontalScrollView} for {@link MenuView}
 */
class SubScrollView extends HorizontalScrollView {
    private LinearLayout mContainer;
    private ItemAnimation mItemAnimation;

    public SubScrollView(Context context) {
        super(context);

        init();
    }

    private void init(){
        setHorizontalScrollBarEnabled(false);

        mContainer = new LinearLayout(getContext());
        mContainer.setOrientation(LinearLayout.HORIZONTAL);

        mContainer.setFocusable(true);
        mContainer.setFocusableInTouchMode(true);
        addView(mContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * Add a view into this {@link HorizontalScrollView}
     *
     * @param view The view you want to add.
     * */
    public void add(View view){
        add(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * Add a view into this {@link HorizontalScrollView}
     *
     * @param view The view you want to add.
     * @param layoutParams The layout parameters to set on the child.
     * */
    public void add(View view, LinearLayout.LayoutParams layoutParams){
        mContainer.addView(view, layoutParams);
    }

    public void didShow() {
        did(true);
    }

    public void didDismiss() {
        did(false);
    }

    private void did(boolean show){
        for (int i = 0; i < mContainer.getChildCount(); i++){
            View child = mContainer.getChildAt(i);

            if (child instanceof TimoItemView){
                if (show) {
                    ((TimoItemView) child).didShow();
                }else{
                    ((TimoItemView) child).didDismiss();
                }
            }
        }
    }

    public void showItemAnimation(){
        if (mItemAnimation == null){
            return;
        }

        int animationIndexForVisibleChild = 0;

        for (int i = 0; i < mContainer.getChildCount(); i++){
            View child = mContainer.getChildAt(i);

            if (child.getGlobalVisibleRect(new Rect()) && child instanceof TimoItemView){
                TimoItemView itemView = (TimoItemView) child;

                Animation animation = mItemAnimation.getAnimation(itemView,
                        animationIndexForVisibleChild);

                Animator animator = mItemAnimation.getAnimator(itemView,
                        animationIndexForVisibleChild);

                if (animation != null) {
                    ((TimoItemView) child).showAnimation(animation);
                }else{
                    ((TimoItemView) child).showAnimator(animator);
                }

                animationIndexForVisibleChild++;
            }
        }
    }

    public void setItemAnimation(ItemAnimation itemAnimation) {
        this.mItemAnimation = itemAnimation;
    }
}
