package me.samlss.timo_demo;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.samlss.timomenu.TimoMenu;
import me.samlss.timomenu.animation.BombItemAnimation;
import me.samlss.timomenu.animation.BounceInDownItemAnimation;
import me.samlss.timomenu.animation.BounceInUpItemAnimation;
import me.samlss.timomenu.animation.BounceItemAnimation;
import me.samlss.timomenu.animation.FlipItemAnimation;
import me.samlss.timomenu.animation.RotateItemAnimation;
import me.samlss.timomenu.animation.ScaleItemAnimation;
import me.samlss.timomenu.animation.StandUpItemAnimation;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description Demo for all the opening animation.
 */
public class OpenAnimationActivity extends AppCompatActivity {
    private TimoMenu mTimoMenu;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_animation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        MenuHelper.setupToolBarBackAction(this, (Toolbar) findViewById(R.id.toolbar));

        initList();
        initMenu();
    }

    private void initList(){
        mListView = findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,
                MenuHelper.getAllOpenAnimationName()));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mTimoMenu.setItemAnimation(0, FlipItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, FlipItemAnimation.create());
                        break;

                    case 1:
                        mTimoMenu.setItemAnimation(0, ScaleItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, ScaleItemAnimation.create());
                        break;

                    case 2:
                        mTimoMenu.setItemAnimation(0, BombItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, BombItemAnimation.create());
                        break;

                    case 3:
                        mTimoMenu.setItemAnimation(0, StandUpItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, StandUpItemAnimation.create());
                        break;

                    case 4:
                        mTimoMenu.setItemAnimation(0, BounceItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, BounceItemAnimation.create());
                        break;

                    case 5:
                        mTimoMenu.setItemAnimation(0, BounceInDownItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, BounceInDownItemAnimation.create());
                        break;

                    case 6:
                        mTimoMenu.setItemAnimation(0, BounceInUpItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, BounceInUpItemAnimation.create());
                        break;

                    case 7:
                        mTimoMenu.setItemAnimation(0, RotateItemAnimation.create());
                        mTimoMenu.setItemAnimation(1, RotateItemAnimation.create());
                        break;
                }

                mTimoMenu.show();
            }
        });
    }

    private void initMenu(){
        int itemViewWidth = (getWindow().getWindowManager().getDefaultDisplay().getWidth() - 40) / 5;

        mTimoMenu =  new TimoMenu.Builder(this)
                .setGravity(Gravity.CENTER)
                .setMenuMargin(new Rect(20, 20, 20, 20))
                .setMenuPadding(new Rect(0, 10, 0, 10))
                .addRow(null, MenuHelper.getTopList(itemViewWidth))
                .addRow(null, MenuHelper.getBottomList(itemViewWidth))
                .build();
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
}
