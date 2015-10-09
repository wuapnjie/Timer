package com.iec.dwx.timer.Beans;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class WishBean {
    private final String TAG = WishBean.class.getSimpleName();

    private String wishTime=null;
    private String wishContent=null;
    private String pictureUrl=null;

    //set get
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

    //set

}
