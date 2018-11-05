package me.samlss.timo_demo;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import me.samlss.timomenu.TimoMenu;
import me.samlss.timomenu.animation.FlipItemAnimation;
import me.samlss.timomenu.interfaces.OnTimoItemClickListener;
import me.samlss.timomenu.interfaces.OnTimoItemTouchListener;
import me.samlss.timomenu.interfaces.TimoMenuListener;
import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Demo for touched event.
 */
public class TouchActivity extends AppCompatActivity {
    private TimoMenu mTimoMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        MenuHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));

        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mTimoMenu.isShowing()){
                mTimoMenu.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void init(){
        int itemViewWidth = (getWindow().getWindowManager().getDefaultDisplay().getWidth() - 40) / 5;

        mTimoMenu = new TimoMenu.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setTimoMenuListener(new TimoMenuListener() {
                    @Override
                    public void onShow() {
                        Toast.makeText(getApplicationContext(), "Show", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDismiss() {
                        Toast.makeText(getApplicationContext(), "Dismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setTimoItemClickListener(new OnTimoItemClickListener() {
                    @Override
                    public void onItemClick(int row, int index, TimoItemView view) {
                        Toast.makeText(getApplicationContext(), String.format("%s click~", getString(MenuHelper.ROW_TEXT[row][index])), Toast.LENGTH_SHORT).show();
                    }
                })
                .setTimoItemTouchListener(new OnTimoItemTouchListener() {
                    @Override
                    public boolean onItemTouch(int row, int index, MotionEvent event, TimoItemView view) {
                        switch (event.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                view.getImageView().animate()
                                        .scaleX(0.75f)
                                        .scaleY(0.75f)
                                        .setDuration(200)
                                        .setInterpolator(new BounceInterpolator())
                                        .start();
                                break;

                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_CANCEL:
                                view.getImageView().animate()
                                        .scaleX(1)
                                        .scaleY(1)
                                        .setDuration(200)
                                        .setInterpolator(new BounceInterpolator())
                                        .start();
                                break;
                        }
                        return false;
                    }
                })
                .setMenuMargin(new Rect(20, 20, 20, 20))
                .setMenuPadding(new Rect(0, 10, 0, 10))
                .addRow(FlipItemAnimation.create(), MenuHelper.getTopList(itemViewWidth))
                .addRow(FlipItemAnimation.create(), MenuHelper.getBottomList(itemViewWidth))
                .build();
    }

    public void onShow(View view) {
        mTimoMenu.show();
    }
}
