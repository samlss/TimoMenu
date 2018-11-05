package me.samlss.timomenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import java.util.List;

import me.samlss.timomenu.animation.AnimationUtils;
import me.samlss.timomenu.animation.ItemAnimation;
import me.samlss.timomenu.interfaces.AnimationListenerAdapter;
import me.samlss.timomenu.interfaces.OnTimoItemClickListener;
import me.samlss.timomenu.interfaces.OnTimoItemTouchListener;
import me.samlss.timomenu.interfaces.TimoCommonMethod;
import me.samlss.timomenu.interfaces.TimoMenuListener;
import me.samlss.timomenu.util.Preconditions;
import me.samlss.timomenu.util.ViewUtils;
import me.samlss.timomenu.view.BackgroundView;
import me.samlss.timomenu.view.MenuView;
import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Timo menu.
 */
public class TimoMenu implements TimoCommonMethod {
    private ViewGroup mRootView;
    private int mGravity = Gravity.BOTTOM;
    private int mDimColor;
    private BackgroundView mBackgroundView;
    private MenuView mMenuView;
    private Rect mMenuMarginRect;
    private Rect mMenuPaddingRect;
    private boolean isShowing = false;
    private boolean useDefaultAnimation = true;
    private boolean isImmediately;
    private Animation mShowAnimation;
    private Animation mDismissAnimation;
    private int mAnimationCounter;
    private TimoMenuListener mTimoMenuListener;
    private OnTimoItemClickListener mTimoItemClickListener;
    private OnTimoItemTouchListener mTimoItemTouchListener;

    protected TimoMenu(Activity activity){
        mRootView = (ViewGroup) activity.getWindow().getDecorView();

        init();
    }

    protected TimoMenu(ViewGroup rootView){
        mRootView = rootView;
        init();
    }

    private void init(){
        mMenuMarginRect  = new Rect();
        mMenuPaddingRect = new Rect();
        mBackgroundView = new BackgroundView(mRootView.getContext());

        mBackgroundView.setOnClickListener(mBackgroundViewClickListener);
        mMenuView = new MenuView(mBackgroundView.getContext());
        mMenuView.setItemClickListener(mItemClickListener);
        mMenuView.setItemTouchListener(mItemTouchListener);

        if (ViewUtils.isAttachedToWindow(mRootView)){
            addBackgroundViewToRootView();
        }else{
            mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mRootViewGlobalLayoutListener);
        }
    }

    /**
     * Add the {@link BackgroundView} to the {@link #mRootView}
     * */
    private void addBackgroundViewToRootView(){
        if (mBackgroundView.getParent() != null){
            return;
        }

        mRootView.addView(mBackgroundView, new ViewGroup.LayoutParams(mRootView.getWidth(),
                mRootView.getHeight()));

        /**
         * Add the {@link #mMenuView} to the {@link #mBackgroundView}
         * */
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = mGravity;
        layoutParams.setMargins(mMenuMarginRect.left, mMenuMarginRect.top,
                mMenuMarginRect.right, mMenuMarginRect.bottom);

        mBackgroundView.addView(mMenuView, layoutParams);
    }

    protected void setMenuBackgroundRes(int menuBackgroundRes) {
        mMenuView.setBackgroundResource(menuBackgroundRes);
    }

    protected void setMenuBackgroundDrawable(Drawable drawable) {
        ViewUtils.setDrawable(mMenuView, drawable);
    }

    protected void setDividerLine(TimoDividerLine dividerLine) {
        mMenuView.setDividerLine(dividerLine);
    }

    protected void setUseDefaultDividerLine(boolean use) {
        mMenuView.setUseDefaultDividerLine(use);
    }

    protected void setMenuMarginRect(Rect menuMarginRect) {
        if (menuMarginRect == null){
            return;
        }

        this.mMenuMarginRect.set(menuMarginRect.left, menuMarginRect.top,
                menuMarginRect.right, menuMarginRect.bottom);
    }

    protected void setMenuPaddingRect(Rect menuPaddingRect) {
        if (mMenuPaddingRect == null){
            return;
        }

        this.mMenuPaddingRect.set(menuPaddingRect.left,
                menuPaddingRect.top, menuPaddingRect.right, menuPaddingRect.bottom);

        mMenuView.setPadding(mMenuPaddingRect.left,
                mMenuPaddingRect.top,
                mMenuPaddingRect.right,
                mMenuPaddingRect.bottom);
    }

    public void setHeaderView(View headerView){
        mMenuView.setHeaderView(headerView);
    }

    public void setFooterView(View footerView){
        mMenuView.setFooterView(footerView);
    }

    public void setHeaderLayoutRes(int headerLayoutRes) {
        mMenuView.setHeaderLayoutRes(headerLayoutRes);
    }

    public void setFooterLayoutRes(int footerLayoutRes) {
        mMenuView.setFooterLayoutRes(footerLayoutRes);
    }

    protected void setAnimation(Animation show, Animation dismiss){
        this.mShowAnimation = show;
        this.mDismissAnimation  = dismiss;
    }

    protected void setUseDefaultAnimation(boolean useDefaultAnimation) {
        this.useDefaultAnimation = useDefaultAnimation;
    }

    protected void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    protected void setDimColor(int color) {
        this.mDimColor = color;
        mBackgroundView.setDimColor(mDimColor);
    }

    protected void setTimoMenuListener(TimoMenuListener timoMenuListener) {
        this.mTimoMenuListener = timoMenuListener;
    }

    protected void setTimoItemClickListener(OnTimoItemClickListener timoItemClickListener) {
        this.mTimoItemClickListener = timoItemClickListener;
    }

    protected void setTimoItemTouchListener(OnTimoItemTouchListener itemTouchListener) {
        this.mTimoItemTouchListener = itemTouchListener;
    }

    public void setMoveToTheFirstOneWhenShow(boolean moveToTheFirstOneWhenShow) {
        mMenuView.setMoveToTheFirstOneWhenShow(moveToTheFirstOneWhenShow);
    }

    public boolean isShowing() {
        return isShowing;
    }

    private void resetAnimations(){
        mAnimationCounter = 0;
        mMenuView.clearAnimation();
    }

    private void innerShow(boolean immediately){
        if (isShowing){
            return;
        }


        isShowing = true;
        isImmediately = immediately;
        resetAnimations();

        mMenuView.showNow();
        mMenuView.showItemAnimation();

        if (immediately){
            mMenuView.didShow();
        }else{
            if (mShowAnimation == null
                    && useDefaultAnimation){
                mShowAnimation = AnimationUtils.getDefaultShowAnimation(mGravity);
            }

            if (mShowAnimation != null) {
                mAnimationCounter++;
                mShowAnimation.setAnimationListener(mShowAnimationListenerAdapter);
                mMenuView.startAnimation(mShowAnimation);
            }
        }

        mAnimationCounter++;
        mBackgroundView.show(immediately ? 0 : 300, mShowAnimatorListenerAdapter);
    }

    private void innerDismiss(boolean immediately){
        if (!isShowing){
            return;
        }

        isShowing = false;
        isImmediately = immediately;
        resetAnimations();

        if (!immediately){
            if (mDismissAnimation == null
                    && useDefaultAnimation){
                mDismissAnimation = AnimationUtils.getDefaultDismissAnimation(mGravity);
            }

            if (mDismissAnimation != null) {
                mAnimationCounter++;
                mDismissAnimation.setAnimationListener(mDismissAnimationListenerAdapter);
                mMenuView.startAnimation(mDismissAnimation);
            }
        }

        mAnimationCounter++;
        mBackgroundView.dismiss(immediately ? 0 : 300, mDismissAnimatorListenerAdapter);
    }

    public void addRow(ItemAnimation itemAnimation, List<TimoItemViewParameter> timoItemViewParameters) {
        mMenuView.addRow(itemAnimation, timoItemViewParameters);
    }

    /**
     * Set the item opening animation to the corresponding row.
     *
     * @param row The row of the menu, form 0.
     * @param itemAnimation The item opening animation.
     * */
    public void setItemAnimation(int row, ItemAnimation itemAnimation){
        mMenuView.setItemAnimation(row, itemAnimation);
    }

    public void show() {
        innerShow(false);
    }

    public void showImmediately() {
        innerShow(true);
    }

    public void dismiss() {
        innerDismiss(false);
    }

    public void dismissImmediately() {
        innerDismiss(true);
    }

    private void checkShowAnimationCounter(){
        mAnimationCounter--;
        if (mAnimationCounter == 0){
            showFinished();
        }
    }

    private void showFinished(){
        if (mTimoMenuListener != null){
            mTimoMenuListener.onShow();
        }
        mAnimationCounter = 0;
        mMenuView.didShow();
    }

    private void checkDismissAnimationCounter(){
        mAnimationCounter--;
        if (mAnimationCounter == 0){
            dismissFinished();
        }
    }

    private void dismissFinished(){
        if (mTimoMenuListener != null){
            mTimoMenuListener.onDismiss();
        }

        mAnimationCounter = 0;
        mMenuView.didDismiss();
        mBackgroundView.setVisibility(View.GONE);
    }

    @Override
    public List<List<TimoItemView>> getRows() {
        return mMenuView.getRowsViewList();
    }

    @Override
    public List<HorizontalScrollView> getScrollViews(){
        return mMenuView.getScrollViews();
    }

    @Override
    public MenuView getMenuView() {
        return mMenuView;
    }

    private ViewTreeObserver.OnGlobalLayoutListener mRootViewGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(mRootViewGlobalLayoutListener);
            }

            addBackgroundViewToRootView();
        }
    };

    private AnimationListenerAdapter mDismissAnimationListenerAdapter = new AnimationListenerAdapter(){
        @Override
        public void onAnimationEnd(Animation animation) {
            checkDismissAnimationCounter();
        }
    };

    private AnimatorListenerAdapter mDismissAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            checkDismissAnimationCounter();
        }
    };

    private AnimationListenerAdapter mShowAnimationListenerAdapter = new AnimationListenerAdapter(){
        @Override
        public void onAnimationEnd(Animation animation) {
            checkShowAnimationCounter();
        }
    };

    private AnimatorListenerAdapter mShowAnimatorListenerAdapter = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            checkShowAnimationCounter();
        }
    };

    View.OnClickListener mBackgroundViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            innerDismiss(isImmediately);
        }
    };

    private MenuView.OnItemClickListener mItemClickListener = new MenuView.OnItemClickListener() {
        @Override
        public void onItemClick(int row, int index, TimoItemView view) {
            if (mTimoMenuListener != null){
                mTimoItemClickListener.onItemClick(row, index, view);
            }
        }
    };

    private MenuView.OnItemTouchListener mItemTouchListener = new MenuView.OnItemTouchListener() {
        @Override
        public boolean onItemTouch(int row, int index, MotionEvent event, TimoItemView view) {
            if (mTimoItemTouchListener != null)
                return mTimoItemTouchListener.onItemTouch(row, index, event, view);
            return false;
        }
    };

    public static class Builder {
        private TimoMenu mImpl;

        /**
         * This menu will attached to an activity, will
         * add the menu to activity.getWindow().getDecorView().
         * */
        public Builder(Activity activity){
            Preconditions.checkNotNull(activity, "The parameter 'activity' can not be null.");
            mImpl = new TimoMenu(activity);
        }

        /**
         * This menu will attached to a viewgroup.
         * */
        public Builder(ViewGroup rootView){
            Preconditions.checkNotNull(rootView, "The parameter 'rootView' can not be null.");
            mImpl = new TimoMenu(rootView);
        }

        /**
         * Set the background resource of menu.
         *
         * @param menuBackgroundRes The drawable resource id.
         * */
        public Builder setMenuBackgroundRes(int menuBackgroundRes) {
            mImpl.setMenuBackgroundRes(menuBackgroundRes);
            return this;
        }

        /**
         * Set the background drawable of menu.
         *
         * @param menuBackgroundDrawable The bg drawable
         * */
        public Builder setMenuBackgroundDrawable(Drawable menuBackgroundDrawable) {
            mImpl.setMenuBackgroundDrawable(menuBackgroundDrawable);
            return this;
        }

        /**
         * Set the dividing line between each line in the menu.
         *
         * @param dividerLine The dividing line parameter.
         * */
        public Builder setDividerLine(TimoDividerLine dividerLine) {
            mImpl.setDividerLine(dividerLine);
            return this;
        }

        /**
         * Set whether to use the default divider line, the default is true.
         *
         * @param use True will use the divider line, otherwise not.
         * */
        public Builder setUseDefaultDividerLine(boolean use) {
            mImpl.setUseDefaultDividerLine(use);
            return this;
        }

        /**
         * Sets the margins for menu, in pixels.
         *
         * @param menuMarginRect Will get the value of {@link Rect#left}, {@link Rect#top}, {@link Rect#right},
         *                       {@link Rect#bottom}
         * */
        public Builder setMenuMargin(Rect menuMarginRect) {
            mImpl.setMenuMarginRect(menuMarginRect);
            return this;
        }

        /**
         * Sets the padding for menu, in pixels.
         *
         * @param menuPaddingRect Will get the value of {@link Rect#left}, {@link Rect#top}, {@link Rect#right},
         *                       {@link Rect#bottom}
         * */
        public Builder setMenuPadding(Rect menuPaddingRect) {
            mImpl.setMenuPaddingRect(menuPaddingRect);
            return this;
        }

        /**
         * Set up the show animation & dismiss animation. <br>
         * But if you call {@link #showImmediately()} or {@link #dismissImmediately()}, will not start the animation.
         * @param show The showing animation.
         * @param dismiss The dismissing animation
         * */
        public Builder setAnimation(Animation show, Animation dismiss){
            mImpl.setAnimation(show, dismiss);
            return this;
        }

        /**
         * Whether to use the default show animation and dismiss animation for menu when no show animation and dismiss animation are specified.
         * The default is true.
         *
         * @param use True will use the default animation, otherwise not.
         * */
        public Builder setUseDefaultAnimation(boolean use) {
            mImpl.setUseDefaultAnimation(use);
            return this;
        }

        /**
         * Set a header view to the menu.
         *
         * @param headerView The header view.
         * */
        public Builder setHeaderView(View headerView){
            mImpl.setHeaderView(headerView);
            return this;
        }

        /**
         * Set a footer view to the menu.
         *
         * @param footerView The footer view.
         * */
        public Builder setFooterView(View footerView){
            mImpl.setFooterView(footerView);
            return this;
        }

        /**
         * Set a header view to the menu.
         *
         * @param headerLayoutRes The header view layout resource.
         * */
        public Builder setHeaderLayoutRes(int headerLayoutRes) {
            mImpl.setHeaderLayoutRes(headerLayoutRes);
            return this;
        }

        /**
         * Set a footer view to the menu.
         *
         * @param footerLayoutRes The footer view layout resource..
         * */
        public Builder setFooterLayoutRes(int footerLayoutRes) {
            mImpl.setFooterLayoutRes(footerLayoutRes);
            return this;
        }

        /**
         * Describes how this menu is positioned. Defaults to Bottom.
         *
         * @param gravity Only can be Bottom | Top | Center.
         * */
        public Builder setGravity(int gravity){
            mImpl.setGravity(gravity);
            return this;
        }

        /**
         * Set the dim color.
         * */
        public Builder setDimColor(int color){
            mImpl.setDimColor(color);
            return this;
        }

        /**
         * Set whether to move to the first one when show, the default is true.
         * */
        public Builder setMoveToTheFirstOneWhenShow(boolean moveToTheFirstOneWhenShow) {
            mImpl.setMoveToTheFirstOneWhenShow(moveToTheFirstOneWhenShow);
            return this;
        }

        /**
         * Set the listener for menu, will call back the show & dismiss method..
         *
         * @param timoMenuListener The menu listener.
         * */
        public Builder setTimoMenuListener(TimoMenuListener timoMenuListener) {
            mImpl.setTimoMenuListener(timoMenuListener);
            return this;
        }

        /**
         * Set the item click listener for menu.
         *
         * @param timoItemClickListener The item click listener.
         * */
        public Builder setTimoItemClickListener(OnTimoItemClickListener timoItemClickListener) {
            mImpl.setTimoItemClickListener(timoItemClickListener);
            return this;
        }

        /**
         * Set the item click listener for menu.
         *
         * @param itemTouchListener The item touch listener.
         * */
        public Builder setTimoItemTouchListener(OnTimoItemTouchListener itemTouchListener) {
            mImpl.setTimoItemTouchListener(itemTouchListener);
            return this;
        }

        /**
         * Add a row into the menu with the item view.
         *
         * @param itemAnimation The animation when item is displayed, if it's null, do not need to display animation.
         * @param timoItemViewParameters The parameter of the item views.
         * */
        public Builder addRow(ItemAnimation itemAnimation, List<TimoItemViewParameter> timoItemViewParameters){
            mImpl.addRow(itemAnimation, timoItemViewParameters);
            return this;
        }

        /**
         * To build a menu.
         * */
        public TimoMenu build(){
            return mImpl;
        }
    }
}
