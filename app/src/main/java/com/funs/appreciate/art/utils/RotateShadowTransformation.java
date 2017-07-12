package com.funs.appreciate.art.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by yc on 2017/7/11.
 */

public class RotateShadowTransformation extends BitmapTransformation {


    public RotateShadowTransformation(Context context) {
        super( context );
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap originalImage, int outWidth, int outHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        // 实现图片翻转90度
        matrix.preScale(1, -1);
        // 创建倒影图片
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height - 50 , width, 50, matrix, false);
        // 创建画布
        Canvas canvas = new Canvas(reflectionImage);
        //把倒影图片画到画布上
        canvas.drawBitmap(reflectionImage, 0, 0, null);
        Paint shaderPaint = new Paint();
        //创建线性渐变LinearGradient对象
        LinearGradient shader = new LinearGradient(0, 0, 0, reflectionImage.getHeight(), 0x19ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
        canvas.drawRect(0, 0, width, reflectionImage.getHeight(), shaderPaint);
        return reflectionImage;

    }

    @Override
    public String getId() {
        return "rotate" ;
    }
}
