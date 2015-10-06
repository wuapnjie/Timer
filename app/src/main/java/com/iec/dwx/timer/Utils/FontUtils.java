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

    public static Typeface getTypeface(Context context, String path) {
        return Typeface.createFromAsset(context.getAssets(), path);
    }

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
