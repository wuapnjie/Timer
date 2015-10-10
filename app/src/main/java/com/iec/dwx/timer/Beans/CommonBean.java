package com.iec.dwx.timer.Beans;

import cn.bmob.v3.BmobObject;

/**
 * 通用的Bean，有WishBean，AchievementBean，SkillBean三个子类
 * Created by Flying SnowBean on 2015/10/10.
 */
public class CommonBean extends BmobObject {
    private int mID;
    private String mTime;
    private String mContent;
    private String mPicture;

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public interface InfoImpl {
        String getInfo();
    }
}
