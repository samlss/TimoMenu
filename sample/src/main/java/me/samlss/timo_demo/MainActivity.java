package me.samlss.timo_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startBottomMenu(View view) {
        startActivity(new Intent(this, BottomActivity.class));
    }

    public void startCenter(View view) {
        startActivity(new Intent(this, CenterActivity.class));
    }

    public void startTop(View view) {
        startActivity(new Intent(this, TopActivity.class));
    }

    public void startHeaderAndFooter(View view) {
        startActivity(new Intent(this, HeaderAndFooterActivity.class));
    }

    public void startTouchAnimation(View view) {
        startActivity(new Intent(this, TouchActivity.class));
    }

    public void startOpenAnimation(View view) {
        startActivity(new Intent(this, OpenAnimationActivity.class));
    }
}
