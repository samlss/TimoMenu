package me.samlss.timomenu.interfaces;

import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description the click listener of {@link TimoItemView}
 */
public interface OnTimoItemClickListener {
    /**
     * When the item is clicked, will call this method.
     *
     * @param row The row of the menu, starting from 0.
     * @param index The index of the row, starting from 0.
     * @param view The clicked item view.
     * */
    void onItemClick(int row, int index, TimoItemView view);
}
