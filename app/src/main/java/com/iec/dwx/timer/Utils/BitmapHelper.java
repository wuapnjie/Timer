package com.iec.dwx.timer.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.iec.dwx.timer.Utils.CacheManager.SMemoryCacheManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Flying SnowBean on 2015/10/15.
 */
public class BitmapHelper {
    private final String TAG = BitmapHelper.class.getSimpleName();

    public static void loadBitmapFromFile(final ImageView imageView, final String path, int width, int height) {
        final SMemoryCacheManager memoryCacheManager = SMemoryCacheManager.getInstance();
        if (memoryCacheManager.getBitmap(path) != null) {
            imageView.setImageBitmap(memoryCacheManager.getBitmap(path));
        } else {

        }
    }

    private static void saveAndShow(ImageView imageView, SMemoryCacheManager memoryCacheManager, String path, Bitmap bitmap1) {
        imageView.setImageBitmap(bitmap1);
        memoryCacheManager.putBitmap(path, bitmap1);
    }
}
