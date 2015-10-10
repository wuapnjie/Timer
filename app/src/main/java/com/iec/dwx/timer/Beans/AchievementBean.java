package com.iec.dwx.timer.Beans;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class AchievementBean extends CommonBean implements CommonBean.InfoImpl{
    private final String TAG = AchievementBean.class.getSimpleName();

    @Override
    public String getInfo() {
        return TAG + ":ID->"+getID()+",Time->" + getTime() + ",Content->" + getContent() + ",Picture->" + getPicture();
    }
}
