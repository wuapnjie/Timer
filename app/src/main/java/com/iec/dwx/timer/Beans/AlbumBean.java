package com.iec.dwx.timer.Beans;

/**
 * 相册列表的数据封装
 * Created by Flying SnowBean on 2015/10/15.
 */
public class AlbumBean {
    private final String TAG = AlbumBean.class.getSimpleName();
    private String mBucketName;
    private String mBucketID;
    private String mThumbnailPath;

    public String getBucketID() {
        return mBucketID;
    }

    public void setBucketID(String bucketID) {
        mBucketID = bucketID;
    }

    public String getThumbnailPath() {
        return mThumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        mThumbnailPath = thumbnailPath;
    }

    public String getBucketName() {
        return mBucketName;
    }

    public void setBucketName(String bucketName) {
        mBucketName = bucketName;
    }
}
