package com.iec.dwx.timer.Beans;

import android.graphics.Color;


/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class WishBean extends CommonBean implements CommonBean.InfoImpl{
    private final String TAG = WishBean.class.getSimpleName();

    private String wishTime=null;
    private String wishContent=null;

    //由于现在不需要图片，所以这里可以用来记录是否分享过,0表示未分享过,1表示分享过
    private String pictureUrl=null;



    public String getWishTime() {
        return wishTime;
    }

    public void setWishTime(String wishTime) {
        this.wishTime = wishTime;
    }

    public String getWishContent() {
        return wishContent;
    }

    public void setWishContent(String wishContent) {
        this.wishContent = wishContent;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String getInfo() {
        return TAG + ":ID->"+getID()+",Time->" + getTime() + ",Content->" + getContent() + ",Picture->" + getPicture();
    }

}
