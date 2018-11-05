package me.samlss.timomenu.interfaces;

import android.view.MotionEvent;

import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description
 */
public interface OnTimoItemTouchListener {
    /**
     * When the item is on touch, will call this method.
     *
     * @param row The row of the menu, starting from 0.
     * @param index The index of the row, starting from 0.
     * @param event
     * @param view The clicked item view.
     * @return True if the listener has consumed the event, false otherwise.
     * */
    boolean onItemTouch(int row, int index, MotionEvent event, TimoItemView view);
}
