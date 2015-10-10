package com.iec.dwx.timer.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class FontUtils {
    private final String TAG = FontUtils.class.getSimpleName();

    /**
     * 获取字体
     * @param context  上下文
     * @param path   字体文件路径
     * @return   字体
     */
    public static Typeface getTypeface(Context context, String path) {
        return Typeface.createFromAsset(context.getAssets(), path);
    }

    /**
     * 通过递归为一个布局内的所有TextView设置字体
     * @param view    View
     * @param typeface  字体
     */
    public static void setTypeface(View view, Typeface typeface) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setTypeface(viewGroup.getChildAt(i), typeface);
            }
        } else if (view instanceof TextView) {
            setTypeface(view, typeface);
        }
    }
}
