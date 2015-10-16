package com.iec.dwx.timer.Content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.iec.dwx.timer.Beans.AlbumBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Flying SnowBean on 2015/10/15.
 */
public class PhotoAlbumManager {
    private final String TAG = PhotoAlbumManager.class.getSimpleName();

    private Context mContext;
    private ContentResolver mContentResolver;

    private PhotoAlbumManager() {
    }


    public static PhotoAlbumManager initial(Context context) {
        PhotoAlbumManager manager = new PhotoAlbumManager();
        if (context != null) {
            manager.mContext = context;
            manager.mContentResolver = context.getContentResolver();
        }
        return manager;
    }


    public List<AlbumBean> getAlbumList() {
        List<AlbumBean> data = new ArrayList<>();
        ArrayMap<String, String> text = getAlbumName();
        for (String bucketId : text.keySet()) {
            AlbumBean bean = new AlbumBean();
            bean.setBucketID(bucketId);
            bean.setBucketName(text.get(bucketId));
            bean.setThumbnailPath(getAlbumThumb(bucketId));
            data.add(bean);
        }

        return data;
    }

    public Observable<List<AlbumBean>> getAlbumListObservable() {
        return Observable.just(getAlbumList());
    }

    public ArrayMap<String, String> getAlbumName() {
        ArrayMap<String, String> data = new ArrayMap<>();
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME}, null, null, MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor == null) {
            Log.d(TAG, "the cursor is null");
            return null;
        }
        if (cursor.moveToFirst()) {
            do {
                String bucket_id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                String bucket_name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                if (!data.containsKey(bucket_id)) {
                    data.put(bucket_id, bucket_name);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public String getAlbumThumb(String id) {
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA}, MediaStore.Images.Media.BUCKET_ID + "=?", new String[]{id}, MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor == null) {
            return null;
        }
        String path;
        try {
            if (cursor.moveToFirst())
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            else path = null;
        } finally {
            cursor.close();
        }
        return path;
    }

    public List<String> getPhotosInAlbum(String id) {
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA}, MediaStore.Images.Media.BUCKET_ID + "=?", new String[]{id}, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return null;
        }
        List<String> paths = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    paths.add(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return paths;
    }


    /**
     * just test
     */
    public void test() {
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA, MediaStore.Images.Media.MINI_THUMB_MAGIC}, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            Log.d(TAG, "the cursor is null");
            return;
        }
        if (cursor.moveToFirst()) {
            do {
                Log.d(TAG, "_ID->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                Log.d(TAG, "BUCKET_ID->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)));
                Log.d(TAG, "BUCKET_DISPLAY_NAME->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                Log.d(TAG, "DATA->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                Log.d(TAG, "THUMB->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MINI_THUMB_MAGIC)));
                Log.d(TAG, "-----------------------------------------");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void getBucket() {
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.BUCKET_DISPLAY_NAME}, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            Log.d(TAG, "the cursor is null");
            return;
        }
        if (cursor.moveToFirst()) {
            do {
                Log.d(TAG, "BUCKET_DISPLAY_NAME->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                Log.d(TAG, "-----------------------------------------");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void getThumbnail() {
        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnails(mContentResolver, MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, MediaStore.Images.Thumbnails.MINI_KIND, new String[]{MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA});
        if (cursor == null) {
            Log.d(TAG, "the cursor is null");
            return;
        }
        if (cursor.moveToFirst()) {
            do {
                Log.d(TAG, "_ID->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID)));
                Log.d(TAG, "IMAGE_ID->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID)));
                Log.d(TAG, "DATA->" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)));
                Log.d(TAG, "-----------------------------------------");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


}
