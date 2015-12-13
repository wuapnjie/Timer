package com.iec.dwx.timer.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Flying SnowBean on 2015/12/13.
 */
public class OthersWish extends BmobObject{
    private String content;
    private Integer likeNumber;
    private Integer commentNumber;

    public OthersWish(String content, Integer likeNumber, Integer commentNumber) {
        this.commentNumber = commentNumber;
        this.content = content;
        this.likeNumber = likeNumber;
    }

    public OthersWish() {
    }


    public Integer getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(Integer commentNumber) {
        this.commentNumber = commentNumber;
    }

    public Integer getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Integer likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
