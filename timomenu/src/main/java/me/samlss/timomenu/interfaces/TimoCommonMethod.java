package me.samlss.timomenu.interfaces;

import android.widget.HorizontalScrollView;

import java.util.List;

import me.samlss.timomenu.view.MenuView;
import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The common method of {@link me.samlss.timomenu.TimoMenu} & {@link me.samlss.timomenu.TimoMenuImpl}
 */
public interface TimoCommonMethod {
    /**
     * Get all the rows of the menu. <br>
     *
     * You can get all the views of every row.
     *
     * @return The item view list of every row.
     * */
    List<List<TimoItemView>> getRows();


    /**
     * Get all the {@link HorizontalScrollView} of the menu. <br>
     *
     * One row corresponds to a {@link HorizontalScrollView}
     *
     * @return The list of {@link HorizontalScrollView}
     * */
    List<HorizontalScrollView> getScrollViews();

    /**
     * Get the menu view.
     * */
    MenuView getMenuView();
}
