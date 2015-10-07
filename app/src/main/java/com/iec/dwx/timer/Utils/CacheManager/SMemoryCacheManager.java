package com.iec.dwx.timer.Utils.CacheManager;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.iec.dwx.timer.BuildConfig;

/**
 * 内存缓存的单例管理类,在testActivity中测试可用
 * 存放用法   SMemoryCacheManager.getInstance().putBitmap(key,value)  即可
 * 取出用法   SMemoryCacheManager.getInstance().getBitmap(key)   即可
 * Created by Flying SnowBean on 2015/10/7.
 */
public class SMemoryCacheManager extends LruCache<String, Bitmap> {
    private final String TAG = SMemoryCacheManager.class.getSimpleName();
    private static SMemoryCacheManager mInstance;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private SMemoryCacheManager(int maxSize) {
        super(maxSize);
    }

    public static SMemoryCacheManager getInstance() {
        if (mInstance == null) {
            int maxMem = (int) Runtime.getRuntime().maxMemory();
            mInstance = new SMemoryCacheManager(maxMem / 8);
        }
        return mInstance;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    /**
     * 从内存中获得缓存的位图
     *
     * @param key 储存的key值
     * @return 内存中存在的位图，可能为空
     */
    public Bitmap getBitmap(String key) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Get bitmap from Mem Cache,the key is " + key);
        return this.get(key);
    }

    /**
     * 将位图对象储存至内存中
     *
     * @param key   存放的key值
     * @param value 需要存放的位图
     */
    public void putBitmap(String key, Bitmap value) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Put bitmap into Mem Cache，the key is " + key);
        this.put(key, value);
    }
}
