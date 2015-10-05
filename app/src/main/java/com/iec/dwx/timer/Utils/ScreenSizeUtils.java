package com.iec.dwx.timer.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class ScreenSizeUtils {

    /**
     * 获取手机屏幕大小
     * @author
     *
     */
    private final String TAG = ScreenSizeUtils.class.getSimpleName();

    /**
     * 宽
     * @return
     */
    public static int getWidth(Context context){
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 高
     * @return
     */
    public static int getHeight(Context context){
        WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
