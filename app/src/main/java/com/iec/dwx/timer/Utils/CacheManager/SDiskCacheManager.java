package com.iec.dwx.timer.Utils.CacheManager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 磁盘缓存的单例管理类,在testActivity中测试可用
 * 存放用法   SDiskCacheManager.getInstance(context).putBitmap(key,value)  即可
 * 取出用法   SDiskCacheManager.getInstance(context).getBitmap(key)   即可
 * Created by Flying SnowBean on 2015/10/7.
 */
public class SDiskCacheManager {
    private final String TAG = SDiskCacheManager.class.getSimpleName();
    private static SDiskCacheManager mInstance;
    private DiskLruCache mDiskLruCache;
    private static Context mCtx;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;   //图片缓存压缩格式
    private int mCompressQuality = 70;  //图片压缩质量
    private static final int IO_BUFFER_SIZE = 8 * 1024;    //IO操作缓存大小
    private static final int DISK_BUFFER_SIZE = 10 * 1024 * 1024;  //磁盘缓存质量，设为10M
    private static final int VALUE_COUNT = 1;

    private SDiskCacheManager(Context context) {
        mCtx = context;
        try {
            File cacheDir = getDiskCacheDir("cache");
            if (!cacheDir.exists()) cacheDir.mkdirs(); //如果不存在就新建为文件夹
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(), VALUE_COUNT, DISK_BUFFER_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SDiskCacheManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SDiskCacheManager(context);
        }
        return mInstance;
    }

    /**
     * 将图片写入磁盘中，建议在非主线程中进行
     *
     * @param key   图片的key
     * @param value 图片
     */
    public void putBitmap(String key, Bitmap value) {
        DiskLruCache.Editor editor = null;
        String cacheKey = hashKeyForDiskCache(key);
        try {
            editor = mDiskLruCache.edit(cacheKey);
            if (editor == null) return;
            if (writeBitmapToFile(value, editor)) {
                mDiskLruCache.flush();
                editor.commit();
                Log.d(TAG, "put bitmap into disk cache,the key is " + cacheKey);
            } else {
                editor.abort();
                Log.e(TAG, "error on put bitmap into disk cache,the key is " + cacheKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "io error on put bitmap into disk cache,the key is " + cacheKey);
            try {
                if (editor != null)
                    editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 从磁盘缓存中获得图片，建议在非主线程中进行
     *
     * @param key 图片的key
     * @return 图片
     */
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        String cacheKey = hashKeyForDiskCache(key);
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(cacheKey);
            Log.d(TAG, "->" + (snapshot == null));
            if (snapshot == null) return null;
            final InputStream in = snapshot.getInputStream(0);
            if (in != null) {
                final BufferedInputStream bufferedInputStream = new BufferedInputStream(in, IO_BUFFER_SIZE);
                bitmap = BitmapFactory.decodeStream(bufferedInputStream);
                Log.d(TAG, "get bitmap from disk cache,the key is " + cacheKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) snapshot.close();
        }
        return bitmap;
    }

    /**
     * 将图片写入文件中
     *
     * @param value  位图
     * @param editor 磁盘缓存编辑者
     * @return 是否写入成功
     */
    private boolean writeBitmapToFile(Bitmap value, DiskLruCache.Editor editor) throws IOException {
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
            boolean flag = value.compress(mCompressFormat, mCompressQuality, out);
            Log.d(TAG, flag + "");
            return flag;
        } finally {
            if (out != null) out.close();
        }
    }

    /**
     * 用MD5编码图片的key
     *
     * @param key 图片的key，可以为url
     * @return 可以作为DiskLruCache的key
     */
    private String hashKeyForDiskCache(String key) {
        String cachekey;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            cachekey = bytes2HexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cachekey = String.valueOf(key.hashCode());
        }
        return cachekey;
    }

    /**
     * 将字节数组转化为hashString
     *
     * @param digest 字节数组
     * @return hashString
     */
    private String bytes2HexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 返回一个存放缓存位置的文件
     *
     * @param filename 文件名
     * @return 存放缓存位置的文件
     */
    private File getDiskCacheDir(String filename) {
        String cacheDir; //缓存文件名
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cacheDir = mCtx.getExternalCacheDir().getPath();
        } else cacheDir = mCtx.getCacheDir().getPath();
        return new File(cacheDir + File.separator + filename);
    }

    /**
     * 获取App版本号
     *
     * @return App的版本号
     */
    private int getAppVersion() {
        try {
            PackageInfo info = mCtx.getPackageManager().getPackageInfo(mCtx.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
