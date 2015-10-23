package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.iec.dwx.timer.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * 开始动画界面
 */
public class StartActivity extends AppCompatActivity {
    GifDrawable mGifDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        try {
            mGifDrawable = new GifDrawable(getResources(), R.drawable.running_clock);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ImageView) findViewById(R.id.iv_introduce)).setImageDrawable(mGifDrawable);

        (new Handler()).postDelayed(() -> {
            startActivity(new Intent(this, TimeActivity.class));
            finish();
        }, 2000);
//        Animator animator = ObjectAnimator.ofFloat(findViewById(R.id.iv_introduce), "alpha", 1f, 0.5f);
//        animator.setDuration(2000);
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                startActivity(new Intent(StartActivity.this, TimeActivity.class));
//                finish();
//            }
//        });
//        animator.start();
    }

}
