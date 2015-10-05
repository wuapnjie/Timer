package com.iec.dwx.timer.Activities;

import android.os.Bundle;

import com.iec.dwx.timer.R;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class PublicWishes extends BaseActivity{
    private final String TAG = PublicWishes.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_wishes);
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_public_wishes;
    }
}
