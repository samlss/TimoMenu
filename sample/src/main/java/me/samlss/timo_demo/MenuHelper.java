package me.samlss.timo_demo;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.samlss.timomenu.TimoItemViewParameter;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description helper of menu.
 */
public class MenuHelper {
    private MenuHelper(){

    }

    public static void setupToolBarBackAction(final AppCompatActivity appCompatActivity, Toolbar toolbar){
        if (appCompatActivity == null
                || toolbar == null){
            return;
        }

        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appCompatActivity.finish();
            }
        });
    }

    public static int ROW_TEXT[][] = {
        {R.string.face_book, R.string.alipay, R.string.ding_ding, R.string.moments, R.string.we_chat, R.string.qq},
        {R.string.collect, R.string.night, R.string.font, R.string.block, R.string.report, 0},
    };

    public static List<TimoItemViewParameter> getTopList(int itemWidth){
        List<TimoItemViewParameter> listTop = new ArrayList<>();
        TimoItemViewParameter facebook = getTimoItemViewParameter(itemWidth, R.drawable.icon_facebook_normal,
                R.drawable.icon_facebook_highlight, R.string.face_book, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter alipay = getTimoItemViewParameter(itemWidth, R.drawable.icon_alipay_normal,
                R.drawable.icon_alipay_highlight, R.string.alipay, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter ding = getTimoItemViewParameter(itemWidth, R.drawable.icon_dingding_normal,
                R.drawable.icon_dingding_highlight, R.string.ding_ding, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter moment = getTimoItemViewParameter(itemWidth, R.drawable.icon_moments_normal,
                R.drawable.icon_moments_highlight, R.string.moments, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter weChat = getTimoItemViewParameter(itemWidth, R.drawable.icon_we_chat_normal,
                R.drawable.icon_we_chat_highlight, R.string.we_chat, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter qq = getTimoItemViewParameter(itemWidth, R.drawable.icon_qq_normal,
                R.drawable.icon_qq_highlight, R.string.qq, R.color.normal_text_color,
                R.color.highlight_text_color);


        listTop.add(facebook);
        listTop.add(alipay);
        listTop.add(ding);
        listTop.add(moment);
        listTop.add(weChat);
        listTop.add(qq);
        return listTop;
    }

    public static List<TimoItemViewParameter> getBottomList(int itemWidth){
        List<TimoItemViewParameter> listBottom = new ArrayList<>();

        TimoItemViewParameter collect = getTimoItemViewParameter(itemWidth, R.drawable.icon_collect_normal,
                R.drawable.icon_collect_hight, R.string.collect, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter night = getTimoItemViewParameter(itemWidth, R.drawable.icon_night_normal,
                R.drawable.icon_night_highlight, R.string.night, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter font = getTimoItemViewParameter(itemWidth, R.drawable.icon_font_normal,
                R.drawable.icon_font_highlight, R.string.font, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter block = getTimoItemViewParameter(itemWidth, R.drawable.icon_block_normal,
                R.drawable.icon_block_highlight, R.string.block, R.color.normal_text_color,
                R.color.highlight_text_color);

        TimoItemViewParameter report = getTimoItemViewParameter(itemWidth, R.drawable.icon_report_normal,
                R.drawable.icon_report_highlight, R.string.report, R.color.normal_text_color,
                R.color.highlight_text_color);


        listBottom.add(collect);
        listBottom.add(night);
        listBottom.add(font);
        listBottom.add(block);
        listBottom.add(report);
        return listBottom;
    }

    public static TimoItemViewParameter getTimoItemViewParameter(int itemWidth,
                                                                 int normalImageRes,
                                                                 int highlightImageRes,
                                                                 int normalTextRes,
                                                                 int normalTextColorRes,
                                                                 int highlightTextColorRes){
        return new TimoItemViewParameter.Builder()
                .setWidth(itemWidth)
                .setImagePadding(new Rect(10, 10, 10, 10))
                .setTextPadding(new Rect(5, 0, 5, 0))
                .setNormalImageRes(normalImageRes)
                .setHighlightedImageRes(highlightImageRes)
                .setNormalTextRes(normalTextRes)
                .setNormalTextColorRes(normalTextColorRes)
                .setHighlightedTextColorRes(highlightTextColorRes)
                .build();

    }


    public static List<String> getAllOpenAnimationName(){
        List<String> list = new ArrayList<>();
        list.add("Flip");
        list.add("Scale");
        list.add("Bomb");
        list.add("Stand Up");
        list.add("Bounce");
        list.add("Bounce In Down");
        list.add("Bounce In Up");
        list.add("Rotate");
        return list;
    }


}
