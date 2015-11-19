package com.iec.dwx.timer.Beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/11/18 0018.
 */
public class SkillBean extends BmobObject{
    private final String TAG = SkillBean.class.getSimpleName();

    private int mId;
    private String mCotent;
    private int marginLeft;
    private int marginTop;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmCotent() {
        return mCotent;
    }

    public void setmCotent(String mCotent) {
        this.mCotent = mCotent;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }



}
