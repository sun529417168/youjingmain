package com.youjing.yjeducation.util;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/2/29.
 */
public class CropCircleTransfermation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bp = PicUtils.getCircleBitmap(source);
        if (bp!=null){
            source.recycle();
        }
        return bp;
    }

    @Override
    public String key() {
        return "circle()";
    }
}
