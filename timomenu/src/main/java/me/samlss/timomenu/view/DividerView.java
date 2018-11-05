package me.samlss.timomenu.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import me.samlss.timomenu.util.ViewUtils;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description The divider view.
 */
class DividerView extends View {

    public DividerView(Context context, Drawable drawable) {
        super(context);

        ViewUtils.setDrawable(this, drawable);
    }
}
