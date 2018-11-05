package me.samlss.timomenu.view;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.samlss.timomenu.TimoItemViewParameter;
import me.samlss.timomenu.util.ViewUtils;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The button view for {@link MenuView}
 */
public class TimoItemView extends LinearLayout {
    private ImageView mImageView;
    private TextView mTextView;
    private TimoItemViewParameter mTimoItemViewParameter; //the button bounded data.
    private boolean isNormalState = true;

    public TimoItemView(Context context, TimoItemViewParameter timoItemViewParameter) {
        super(context);

        mTimoItemViewParameter = timoItemViewParameter;
        mImageView = new ImageView(context);
        mTextView = new TextView(context);

        if (mTimoItemViewParameter != null) {
            init();
        }
    }

    private void init(){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        Drawable drawable = mTimoItemViewParameter.getBackgroundDrawable();
        if(drawable == null && mTimoItemViewParameter.getBackgroundDrawableRes() != 0){
            try{
                drawable = ViewUtils.getDrawable(this, mTimoItemViewParameter.getBackgroundDrawableRes());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (drawable != null){
            ViewUtils.setDrawable(this, drawable);
        }else{
            if (mTimoItemViewParameter.isUseDefaultBackgroundDrawable()) {
                setDefaultBackgroundDrawable();
            }
        }

        initImageView();
        initTextView();
    }

    private void setDefaultBackgroundDrawable(){
        try {
            TypedValue typedValue = new TypedValue();
            getContext().getTheme()
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
            int[] attribute = new int[]{android.R.attr.selectableItemBackground};
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
            ViewUtils.setDrawable(this, typedArray.getDrawable(0));
            typedArray.recycle();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initImageView(){
        setNormalImageState();

        if (mTimoItemViewParameter.getScaleType() != null) {
            mImageView.setScaleType(mTimoItemViewParameter.getScaleType());
        }

        LayoutParams layoutParams = new LayoutParams(mTimoItemViewParameter.getImageWidth(),
                mTimoItemViewParameter.getImageHeight());

        if (mTimoItemViewParameter.getImageMargin() != null) {
            layoutParams.leftMargin = mTimoItemViewParameter.getImageMargin().left;
            layoutParams.topMargin = mTimoItemViewParameter.getImageMargin().top;
            layoutParams.rightMargin = mTimoItemViewParameter.getImageMargin().right;
            layoutParams.bottomMargin = mTimoItemViewParameter.getImageMargin().bottom;
        }

        if (mTimoItemViewParameter.getImagePadding() != null) {
            mImageView.setPadding(mTimoItemViewParameter.getImagePadding().left,
                    mTimoItemViewParameter.getImagePadding().top,
                    mTimoItemViewParameter.getImagePadding().right,
                    mTimoItemViewParameter.getImagePadding().bottom);
        }

        addView(mImageView, layoutParams);
    }

    private void initTextView(){
        setNormalTextState();

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTimoItemViewParameter.getTextSize());
        mTextView.setEllipsize(mTimoItemViewParameter.getEllipsize());
        mTextView.setGravity(Gravity.CENTER);

        if (mTimoItemViewParameter.getEllipsize() == null
                || mTimoItemViewParameter.getEllipsize() == TextUtils.TruncateAt.MARQUEE) {
            mTextView.setSingleLine(true);
            mTextView.setMarqueeRepeatLimit(-1);
            mTextView.setHorizontallyScrolling(true);
            mTextView.setFocusable(true);
            mTextView.setFocusableInTouchMode(true);
            mTextView.setFreezesText(true);
        }

        LayoutParams layoutParams = new LayoutParams(mTimoItemViewParameter.getTextWidth(), mTimoItemViewParameter.getTextHeight());

        if (mTimoItemViewParameter.getTextMargin() != null) {
            layoutParams.leftMargin = mTimoItemViewParameter.getTextMargin().left;
            layoutParams.topMargin = mTimoItemViewParameter.getTextMargin().top;
            layoutParams.rightMargin = mTimoItemViewParameter.getTextMargin().right;
            layoutParams.bottomMargin = mTimoItemViewParameter.getTextMargin().bottom;
        }

        if (mTimoItemViewParameter.getTextPadding() != null) {
            mTextView.setPadding(mTimoItemViewParameter.getTextPadding().left,
                    mTimoItemViewParameter.getTextPadding().top,
                    mTimoItemViewParameter.getTextPadding().right,
                    mTimoItemViewParameter.getTextPadding().bottom);
        }

        addView(mTextView, layoutParams);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView(){
        return mTextView;
    }

    public TimoItemViewParameter getTimoItemViewParameter() {
        return mTimoItemViewParameter;
    }

    @Deprecated
    private void delayToShow(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                didShow();
            }
        });
    }

    public void didShow() {
        /**
         * Avoid calling the interface after the view has not been initialized.
         * Please do not display the menu in {@link android.app.Activity#onCreate(Bundle)} or other methods that the view has not been initialized.
         * Otherwise, the marquee effect may be not work.
         * */
        mTextView.setSelected(true);
    }

    public void didDismiss(){
        mTextView.setSelected(false);
    }

    public void showAnimation(Animation animation){
        if (animation == null){
            return;
        }

        startAnimation(animation);
    }

    public void showAnimator(Animator animator){
        if (animator == null){
            return;
        }

        animator.start();
    }

    private void onHighlighted(){
        if (!isNormalState){
            return;
        }

        isNormalState = false;
        setHighlightedImageState();
        setHighlightedTextState();
    }

    private void onNormal(){
        if (isNormalState){
            return;
        }

        isNormalState = true;
        setNormalImageState();
        setNormalTextState();
    }

    private void setNormalImageState(){
        ViewUtils.setDrawable(mImageView, mTimoItemViewParameter.getNormalImageRes(),
                mTimoItemViewParameter.getNormalImageDrawable());
    }

    private void setHighlightedImageState(){
        ViewUtils.setDrawable(mImageView, mTimoItemViewParameter.getHighlightedImageRes(),
                mTimoItemViewParameter.getHighlightedImageDrawable());
    }

    private void setNormalTextState(){
        ViewUtils.setText(mTextView, mTimoItemViewParameter.getNormalTextRes(),
                mTimoItemViewParameter.getNormalText());

        ViewUtils.setTextColor(mTextView, mTimoItemViewParameter.getNormalTextColorRes(),
                mTimoItemViewParameter.getNormalTextColor());
    }

    private void setHighlightedTextState(){
        ViewUtils.setText(mTextView, mTimoItemViewParameter.getHighlightedTextRes(),
                mTimoItemViewParameter.getHighlightedText());

        ViewUtils.setTextColor(mTextView, mTimoItemViewParameter.getHighlightedTextColorRes(),
                mTimoItemViewParameter.getHighlightedTextColor());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onHighlighted();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onNormal();
                break;
        }

        return super.onTouchEvent(event);
    }

}
