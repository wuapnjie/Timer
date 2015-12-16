package com.iec.dwx.timer.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Flying SnowBean on 2015/12/13.
 */
public class OthersWish extends BmobObject{
    private String content;
    private Integer likeNumber;
    private Integer commentNumber;
    private User author;
    private BmobRelation likes;

    public OthersWish(String content, Integer likeNumber, Integer commentNumber,User author) {
        this.commentNumber = commentNumber;
        this.content = content;
        this.likeNumber = likeNumber;
        this.author = author;
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

    public BmobUser getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }
}
