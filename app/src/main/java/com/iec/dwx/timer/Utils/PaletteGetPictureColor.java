package com.iec.dwx.timer.Utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/10 0010.
 */
public class PaletteGetPictureColor {
    private final String TAG = PaletteGetPictureColor.class.getSimpleName();

    public static void getColorFromBitmap(Resources resources,Bitmap bitmap,TextView textView){
        Palette.Builder builder=new Palette.Builder(bitmap);
        //设置最多取数
        builder.maximumColorCount(1);
        builder.generate(new Palette.PaletteAsyncListener() {
                             @Override
                             public void onGenerated(Palette palette) {
                                 Palette.Swatch  swatch=palette.getSwatches().get(0);
                                 if(swatch!=null){
                                     textView.setTextColor(Color.WHITE-swatch.getTitleTextColor());
                                 }else {
                                     System.out.println("swatch is null !");
                                 }

                             }
                         }
        );
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//               Palette.Swatch  swatch=palette.getSwatches().get(0);
//                if(swatch!=null){
//                    textView.setTextColor(swatch.getTitleTextColor());
//                }else {
//                    System.out.println("swatch is null !");
//                }
//
//            }
//        });
    }

}
