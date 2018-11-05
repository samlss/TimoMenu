package me.samlss.timomenu.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.samlss.timomenu.R;
import me.samlss.timomenu.TimoDividerLine;
import me.samlss.timomenu.TimoItemViewParameter;
import me.samlss.timomenu.animation.ItemAnimation;
import me.samlss.timomenu.util.ViewUtils;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The menu view.
 */
public class MenuView extends LinearLayout {
    private List<List<TimoItemView>> mRowsViewList = new ArrayList<>();
    private boolean useDefaultDividerLine = true;
    private boolean moveToTheFirstOneWhenShow = true;
    private TimoDividerLine mDividerLine;
    private OnItemClickListener mItemClickListener;
    private OnItemTouchListener mItemTouchListener;
    private int mRowCounter;

    private View mHeaderView;
    private View mFooterView;
    List<HorizontalScrollView> mScrollViews = new ArrayList<>();

    public MenuView(Context context) {
        super(context);

        init();
    }

    private void init(){
        setOrientation(VERTICAL);
        setBackgroundResource(R.drawable.tm_shape_menu_default_bg);
    }

    private void addDividerLine(){
        if (!useDefaultDividerLine){
            return;
        }

        int height;
        Drawable drawable = null;

        int marginLeft = 0;
        int marginRight = 0;

        if (mDividerLine == null){
            height = 1;
        }else{
            height = mDividerLine.getHeight();
            if (mDividerLine.getDrawable() != null){
                drawable = mDividerLine.getDrawable();
            }else{
                try{
                    drawable = getContext().getResources().getDrawable(mDividerLine.getDrawableRes());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            marginLeft  = mDividerLine.getMarginLeft();
            marginRight = mDividerLine.getMarginRight();
        }

        if (drawable == null){
            drawable = new ColorDrawable(0xFFE0E0E0);
        }

        DividerView dividerView = new DividerView(getContext(), drawable);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        layoutParams.leftMargin = marginLeft;
        layoutParams.rightMargin = marginRight;
        addView(dividerView,
                mFooterView == null ? -1 : indexOfChild(mFooterView),
                layoutParams);
    }

    public void addRow(ItemAnimation itemAnimation, List<TimoItemViewParameter> timoItemViewParameters) {
        if (timoItemViewParameters == null
                || timoItemViewParameters.isEmpty()){
            return;
        }

        if (!mRowsViewList.isEmpty()){
            addDividerLine();
        }

        SubScrollView subScrollView = new SubScrollView(getContext());
        subScrollView.setItemAnimation(itemAnimation);
        mScrollViews.add(subScrollView);

        addView(subScrollView,
                mFooterView == null ? -1 : indexOfChild(mFooterView),
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        List<TimoItemView> itemViews = new ArrayList<>(timoItemViewParameters.size());

        int index = 0;
        for (TimoItemViewParameter timoItemViewParameter : timoItemViewParameters){
            TimoItemView timoItemView = new TimoItemView(getContext(), timoItemViewParameter);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(timoItemViewParameter.getWidth(),
                    timoItemViewParameter.getHeight());

            subScrollView.add(timoItemView, layoutParams);
            setItemClickListener(timoItemView, mRowCounter, index);
            setItemTouchListener(timoItemView, mRowCounter, index++);
            itemViews.add(timoItemView);
        }

        mRowsViewList.add(itemViews);
        mRowCounter++;
    }

    public void setItemAnimation(int row, ItemAnimation itemAnimation){
        if (getScrollViews() == null
                || row < 0
                || row >= getScrollViews().size()){
            return;
        }

        for (HorizontalScrollView subScrollView : getScrollViews()){
            ((SubScrollView)subScrollView).setItemAnimation(itemAnimation);
        }
    }

    private void setItemClickListener(final TimoItemView timoItemView, final int row, final int index){
        timoItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null){
                    mItemClickListener.onItemClick(row, index, timoItemView);
                }
            }
        });
    }

    private void setItemTouchListener(final TimoItemView timoItemView, final int row, final int index){
        timoItemView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mItemTouchListener != null)
                    return mItemTouchListener.onItemTouch(row, index, event,timoItemView);
                return false;
            }
        });
    }

    public List<List<TimoItemView>> getRowsViewList() {
        return mRowsViewList;
    }

    public List<HorizontalScrollView> getScrollViews(){
        return mScrollViews;
    }

    public void setDividerLine(TimoDividerLine dividerLine) {
        mDividerLine = dividerLine;
    }

    public void setUseDefaultDividerLine(boolean use) {
        useDefaultDividerLine = use;
    }

    public void didShow(){
        for (int i = 0; i < getChildCount(); i++){
            if (getChildAt(i) instanceof SubScrollView){
                ((SubScrollView)getChildAt(i)).didShow();
            }
        }
    }

    public void didDismiss(){
        for (int i = 0; i < getChildCount(); i++){
            if (getChildAt(i) instanceof SubScrollView){
                ((SubScrollView)getChildAt(i)).didDismiss();
            }
        }
    }

    public void showItemAnimation(){
        for (int i = 0; i < getChildCount(); i++){
            if (getChildAt(i) instanceof SubScrollView){
                ((SubScrollView)getChildAt(i)).showItemAnimation();
            }
        }
    }

    public void showNow(){
        if (!moveToTheFirstOneWhenShow
                || getScrollViews() == null){
            return;
        }

        for (HorizontalScrollView subScrollView : getScrollViews()){
            subScrollView.scrollTo(0, 0);
        }
    }

    public void setHeaderView(View headerView) {
        if (mHeaderView != null){
            removeView(mHeaderView);
        }
        mHeaderView = headerView;

        if (mHeaderView != null){
            addView(mHeaderView, 0);
        }
    }

    public void setFooterView(View footerView) {
        if (mFooterView != null){
            removeView(mFooterView);
        }
        mFooterView = footerView;

        if (mFooterView != null){
            addView(mFooterView);
        }
    }

    public void setHeaderLayoutRes(int headerLayoutRes) {
        View header = LayoutInflater.from(getContext())
                .inflate(headerLayoutRes, this, false);
        setHeaderView(header);
    }

    public void setFooterLayoutRes(int footerLayoutRes) {
        View footer = LayoutInflater.from(getContext())
                .inflate(footerLayoutRes, this, false);
        setFooterView(footer);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItemTouchListener(OnItemTouchListener itemTouchListener) {
        this.mItemTouchListener = itemTouchListener;
    }

    public void setMoveToTheFirstOneWhenShow(boolean moveToTheFirstOneWhenShow) {
        this.moveToTheFirstOneWhenShow = moveToTheFirstOneWhenShow;
    }

    public interface OnItemClickListener{
        void onItemClick(int row, int index, TimoItemView view);
    }

    public interface OnItemTouchListener{
        boolean onItemTouch(int row, int index, MotionEvent event, TimoItemView view);
    }
}
