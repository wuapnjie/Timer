package com.iec.dwx.timer.Beans;

/**
 * Created by Flying SnowBean on 2015/10/2.
 */
public class SkillBean extends CommonBean implements CommonBean.InfoImpl{
    private final String TAG = SkillBean.class.getSimpleName();

    //picture用来做marginTop
    //time用来做marginLeft

    @Override
    public String getInfo() {
        return TAG + ":ID->"+getID()+",Time->" + getTime() + ",Content->" + getContent() + ",Picture->" + getPicture();
    }

    public void  setMarginLeft(int marginLeft){
        setTime(marginLeft+"");
    }

    public int getMarginLeft(){
       return Integer.parseInt(getTime());
    }

    public void  setMarginTop(int marginTop){
        setPicture(marginTop+"");
    }

    public int getMarginTop(){
        return Integer.parseInt(getPicture());
    }

}
