package com.iec.dwx.timer.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class Utils {
    private final String TAG = Utils.class.getSimpleName();

    /**
     * 将dp数值转换为px
     *
     * @param dp 输入dp值
     * @return px值
     */
    public static int dp2px(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp);
    }

    /**
     * 格式化时间，用做数据库储存室的时间
     *
     * @param date 一般new Date()即可
     * @return 格式化后的时间
     */
    public static String formatTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取App版本号
     *
     * @return App的版本号
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
