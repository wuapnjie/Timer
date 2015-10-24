package com.iec.dwx.timer.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Utils.Utils;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

public class AboutActivity extends BaseActivity {
    GifDrawable mGifDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ((Toolbar)findViewById(R.id.toolbar_about)).setNavigationOnClickListener(v -> finish());

        try {
            mGifDrawable = new GifDrawable(getResources(),R.drawable.time_animated);
            ((ImageView)findViewById(R.id.iv_about_us)).setImageDrawable(mGifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getEdgeSize() {
        return Utils.dp2px(200);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_about;
    }
}
