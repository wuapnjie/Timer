package com.iec.dwx.timer.Activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.iec.dwx.timer.R;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class PublicWishes extends BaseActivity{
    private final String TAG = PublicWishes.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Bitmap convertDrawableToBitmap(int image) {
        return ((BitmapDrawable)getResources().getDrawable(image)).getBitmap();
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
