package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import com.iec.dwx.timer.R;

/**
 * Created by Administrator on 2015/10/16 0016.
 */
public class MyWishDetails extends BaseActivity{
    private final String TAG = MyWishDetails.class.getSimpleName();
    private ViewPager viewPager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager= (ViewPager) findViewById(R.id.detail_viewPager);
        View view=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
        
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_wishes_detail;
    }
}
