package com.iec.dwx.timer.Beans;

import android.graphics.Color;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class WishBean extends BmobObject{
    private final String TAG = WishBean.class.getSimpleName();

    private boolean isShared=false;
    private String wishTime=null;
    private String wishContent=null;
    private String pictureUrl=null;
    private Color textColor=null;


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


    public boolean isShared() {
        return isShared;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }
}
