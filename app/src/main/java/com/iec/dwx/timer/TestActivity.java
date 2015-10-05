package com.iec.dwx.timer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Activities.MainActivity;
import com.iec.dwx.timer.Activities.TimeActivity;

import butterknife.Bind;

public class TestActivity extends BaseActivity {


    @Bind(R.id.btn_test)
    Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBtnTest.setOnClickListener(v -> linkTo());
        mSwipeBackLayout.setEnableGesture(false);
    }

    private void linkTo() {
        startActivity(new Intent(this, TimeActivity.class));
        //进入viewpager测试使用，记得修改回去
//        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }


}
