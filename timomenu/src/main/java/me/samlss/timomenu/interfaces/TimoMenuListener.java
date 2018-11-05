package me.samlss.timomenu.interfaces;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Listener for {@link me.samlss.timomenu.TimoMenu}
 */
public interface TimoMenuListener {
    /**
     * When the menu is fully showed, will call this method.
     * */
    void onShow();

    /**
     * When the menu is fully dismissed, will call this method.
     * */
    void onDismiss();
}
