package com.iec.dwx.timer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.iec.dwx.timer.R;
import com.iec.dwx.timer.Views.CardFlipLayout;

import butterknife.Bind;

public class TimeActivity extends BaseActivity {

    @Bind(R.id.card_container)
    CardFlipLayout mCardContainer;
    @Bind(R.id.btn_time_now)
    Button mBtnTimeNow;
    @Bind(R.id.btn_time_leave)
    Button mBtnTimeLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
    }

    private void initListener() {
        mBtnTimeNow.setOnClickListener(v -> mCardContainer.flip());
        mBtnTimeLeave.setOnClickListener(v -> mCardContainer.flip());
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_time;
    }

}
