package com.funs.appreciate.art.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebResHelper {
    private static List<String> _list;
    private static Map<String, SoftReference<Bitmap>> softCache = new HashMap<String, SoftReference<Bitmap>>();

    public static List<String> get_list() {
        return _list;
    }

    public static void set_list(List<String> _list) {
        WebResHelper._list = _list;
    }


    public static Bitmap loadImageLocal(String picPath) {
        Bitmap bitmap = null;

        if (softCache.containsKey(picPath)) {
            SoftReference<Bitmap> softBitmap = softCache.get(picPath);
            if (softBitmap != null) {
                bitmap = softBitmap.get();
            }
        }
        if (bitmap == null) {
            bitmap = decodeFile(new File(picPath));
            SoftReference<Bitmap> bitmap3 = new SoftReference<Bitmap>(bitmap);
            softCache.put(picPath, bitmap3);
        }
        return bitmap;
    }

    private static Bitmap decodeFile(File f){
        try {
            //解码图像大小,对图片进行缩放...防止图片过大导致内存溢出...
            BitmapFactory.Options o = new BitmapFactory.Options();//实例化一个对象...
            o.inJustDecodeBounds = true;//这个就是Options的第一个属性,设置为true的时候，不会完全的对图片进行解码操作,不会为其分配内存，只是获取图片的基本信息...
            BitmapFactory.decodeStream(new FileInputStream(f),null,o); //以码流的形式进行解码....

            /*
             * 下面也就是对图片进行的一个压缩的操作...如果图片过大，最后会根据指定的数值进行缩放...
             * 找到正确的刻度值，它应该是2的幂.
             * 这里我指定了图片的长度和宽度为70个像素...
             *
             * */

//            final int REQUIRED_SIZE=70;
            final int REQUIRED_SIZE_WIDTH=1920;
            final int REQUIRED_SIZE_HEIGHT=1080;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2 < REQUIRED_SIZE_WIDTH || height_tmp/2 < REQUIRED_SIZE_HEIGHT)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options(); //这里定义了一个新的对象...获取的还是同一张图片...
            o2.inSampleSize = scale;   //对这张图片设置一个缩放值...inJustDecodeBounds不需要进行设置...
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2); //这样通过这个方法就可以产生一个小的图片资源了...
        } catch (Exception e) {}
        return null;
    }
}
