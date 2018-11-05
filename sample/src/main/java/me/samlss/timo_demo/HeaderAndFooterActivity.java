package me.samlss.timo_demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import me.samlss.timomenu.TimoMenu;
import me.samlss.timomenu.animation.BombItemAnimation;
import me.samlss.timomenu.interfaces.OnTimoItemClickListener;
import me.samlss.timomenu.interfaces.TimoMenuListener;
import me.samlss.timomenu.view.TimoItemView;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description  Demo that the menu is displayed with header & footer.
 */
public class HeaderAndFooterActivity extends AppCompatActivity {
    private TimoMenu mTimoMenu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_header_footer);
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
        int itemViewWidth = (getWindow().getWindowManager().getDefaultDisplay().getWidth()) / 4;

        mTimoMenu =  new TimoMenu.Builder(this)
                .setGravity(Gravity.BOTTOM)
                .setMenuBackgroundDrawable(new ColorDrawable(Color.WHITE))
                .setHeaderLayoutRes(R.layout.layout_header)
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
                .addRow(BombItemAnimation.create(), MenuHelper.getTopList(itemViewWidth))
                .addRow(BombItemAnimation.create(), MenuHelper.getBottomList(itemViewWidth))
                .build();

        View footer = getLayoutInflater().inflate(R.layout.layout_footer, mTimoMenu.getMenuView(), false);
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimoMenu.isShowing()){
                    mTimoMenu.dismiss();
                }
            }
        });

        mTimoMenu.setFooterView(footer);
    }

    public void onShow(View view) {
        mTimoMenu.show();
    }
}
