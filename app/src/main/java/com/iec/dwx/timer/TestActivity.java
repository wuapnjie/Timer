package com.iec.dwx.timer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Activities.MainActivity;

import butterknife.Bind;
import rx.Observable;

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
        Observable.just(new Intent()).map(intent1 -> intent1.setClass(this, MainActivity.class)).subscribe(intent -> startActivity(intent));
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
