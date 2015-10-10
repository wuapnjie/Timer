package com.iec.dwx.timer.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;

import com.iec.dwx.timer.R;

/**
 * Created by Administrator on 2015/10/10 0010.
 */
public class PaletteGetPictureColor {
    private final String TAG = PaletteGetPictureColor.class.getSimpleName();

    public static int getColorFromBitmap(Resources resources,Bitmap bitmap){
        Palette palette=Palette.generate(bitmap);
        Palette.Swatch vb=palette.getVibrantSwatch();
        return vb.getTitleTextColor();
    }

}
