package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.iec.dwx.timer.Beans.WishBean;
import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.DBHelper;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * 开始动画界面
 */
public class StartActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
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

        Log.d(TAG, "wishes' size->" + DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_WISH).size());
        Log.d(TAG, "achievement' size->"+DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_ACHIEVEMENT).size());
        Log.d(TAG, "skills' size->"+DBHelper.getInstance(this).getAllBeans(DBHelper.DB_TABLE_SKILL).size());
    }

}
