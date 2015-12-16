package com.iec.dwx.timer.Beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by Flying SnowBean on 2015/12/13.
 */
public class WishComment extends BmobObject {
    private OthersWish wish;
    private String content;
    private User author;

    public WishComment(OthersWish wish, String content, User user) {
        this.wish = wish;
        this.content = content;
        this.author = user;
    }

    public WishComment() {

    }

    public User getUser() {
        return author;
    }

    public void setUser(User user) {
        this.author = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OthersWish getWish() {
        return wish;
    }

    public void setWish(OthersWish wish) {
        this.wish = wish;
    }
}
