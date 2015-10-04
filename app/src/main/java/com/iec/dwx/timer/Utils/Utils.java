package com.iec.dwx.timer.Utils;

import android.content.res.Resources;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class Utils {
    private final String TAG = Utils.class.getSimpleName();

    /**
     * 将dp数值转换为px
     * @param dp  输入dp值
     * @return   px值
     */
    public static int dp2px(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp);
    }
}
