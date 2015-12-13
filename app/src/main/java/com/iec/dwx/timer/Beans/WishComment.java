package com.iec.dwx.timer.Beans;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by Flying SnowBean on 2015/12/13.
 */
public class WishComment extends BmobObject {
    private OthersWish wish;
    private String content;

    public WishComment(OthersWish wish,String content){
        this.wish = wish;
        this.content = content;
    }

    public WishComment() {

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
