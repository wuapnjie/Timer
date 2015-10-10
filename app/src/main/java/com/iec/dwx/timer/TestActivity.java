package com.iec.dwx.timer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.iec.dwx.timer.Activities.BaseActivity;
import com.iec.dwx.timer.Activities.TimeActivity;
import com.iec.dwx.timer.Utils.CacheManager.SDiskCacheManager;

import butterknife.Bind;

public class TestActivity extends BaseActivity {


    @Bind(R.id.btn_test_1)
    Button mBtnTest1;
    @Bind(R.id.btn_test_2)
    Button mBtnTest2;
    @Bind(R.id.iv_show)
    ImageView mIvShow;
    @Bind(R.id.btn_test_3)
    Button mBtnTest3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSwipeBackLayout.setEnableGesture(false);
        mBtnTest1.setOnClickListener(v -> save());
        mBtnTest2.setOnClickListener(v -> get());
        mBtnTest3.setOnClickListener(v -> linkTo());
    }

    private void get() {
        Bitmap bitmap = SDiskCacheManager.getInstance(this).getBitmap("http:\\sdfas");
        if (bitmap == null) System.out.println("get null");
        mIvShow.setImageBitmap(bitmap);
    }

    private void save() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.b);
        if (bitmap == null) System.out.println("save null");
        SDiskCacheManager.getInstance(this).putBitmap("http:\\sdfas", bitmap);
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
