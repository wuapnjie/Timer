package com.iec.dwx.timer.Beans;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class AchievementBean extends CommonBean implements CommonBean.InfoImpl{

    @Override
    public String getInfo() {
        return "AchievementBean" + ":ID->"+getID()+",Time->" + getTime() + ",Content->" + getContent() + ",Picture->" + getPicture();
    }
}
