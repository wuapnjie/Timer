package com.iec.dwx.timer.Animate;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.iec.dwx.timer.BuildConfig;

/**
 * Created by Flying SnowBean on 2015/10/3.
 */
public class PageTransformer implements ViewPager.PageTransformer {
    private final String TAG = PageTransformer.class.getSimpleName();

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "position->" + position);
        int pageWidth = page.getWidth();

        if (position < -1) {    // [-Infinity,-1)
            page.setAlpha(0);
        } else if (position <= 0) {  // (0,1]
            page.setAlpha(1);
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        } else if (position <= 1) {   // (0,1]
            page.setAlpha(1 - position);
            page.setTranslationX(pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            if (BuildConfig.DEBUG)
                Log.d(TAG, "scaleFactor->" + scaleFactor);
        } else {    // (1,+Infinity]
            page.setAlpha(0);
        }
    }
}
