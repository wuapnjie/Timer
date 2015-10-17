package com.iec.dwx.timer.Beans;


/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class WishBean extends CommonBean implements CommonBean.InfoImpl{
    private final String TAG = WishBean.class.getSimpleName();

    //这里的mPicture是用来存取是否分享过得信息
    @Override
    public String getInfo() {
        return TAG + ":ID->"+getID()+",Time->" + getTime() + ",Content->" + getContent() + ",Picture->" + getPicture();
    }

}
