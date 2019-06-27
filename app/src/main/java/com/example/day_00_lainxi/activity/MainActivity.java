package com.example.day_00_lainxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.day_00_lainxi.R;

public class MainActivity extends AppCompatActivity {

    private ImageView main_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        //周林  1811B
    }
    private void initData() {
        AnimationSet animationSet = new AnimationSet(this, null);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2, 0, 2, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.setDuration(8000);
        main_iv.setAnimation(animationSet);
    }

    private void initView() {
        main_iv = (ImageView) findViewById(R.id.main_iv);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(8000);
                    Intent intent = new Intent(MainActivity.this, VpActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
