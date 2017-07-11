package com.funs.appreciate.art.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.utils.RotateShadowTransformation;
import com.funs.appreciate.art.utils.UIHelper;

import java.util.List;


/**
 * Created by yc on 2017/6/12.
 *  阴影
 */

public class PictureShadowRelative extends FocusRelative {

    private List<PictureModel> lms;
    public PictureShadowRelative(Context context) {
        super(context);
    }

    public PictureShadowRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        margin = 6;
    }

    public PictureShadowRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void addViews(final List<PictureModel> list){
        if (list == null)
            throw new IllegalArgumentException("nullPointer addViews  PictureModel  list ");
        lms = list;
        if(lms != null && lms.size() != 0) {
            for (int i = 0; i < lms.size(); i++) {
                final PictureModel lm = lms.get(i);
                UIHelper.Size size;
                size = new UIHelper.Size(UIHelper.zoomW(lm.getWidth(), UIHelper.ZoomMode.KeepHV),
                        UIHelper.zoomH(lm.getHeight(), UIHelper.ZoomMode.KeepHV));
//                System.out.println("====== width ======== height  ==========>"+size.width+ " "+size.height);
                LayoutParams lp = new LayoutParams(size.width, size.height);

                if (lm.getTopid() != 0)
                    lp.addRule(RelativeLayout.BELOW, lm.getTopid());
                if (lm.getLeftid() != 0)
                    lp.addRule(RelativeLayout.RIGHT_OF, lm.getLeftid());
                margin = lm.getMargin();
                int marginW = UIHelper.zoomW(margin, UIHelper.ZoomMode.KeepHV);
                int marginH = UIHelper.zoomH(margin, UIHelper.ZoomMode.KeepHV);
//                System.out.println("====== marginW ====== marginH ==========>"+marginW+ " "+marginH);
//                System.out.println("================>"+lm.getId()+"  "+lm.getTopid()+ "  "+lm.getLeftid());

                lp.setMargins(marginW, marginH, marginW, marginH);

                LayoutParams lpc = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//                lpc.setMargins(6, 6, 6, 6);
                final RelativeLayout rl = lm.getRootView();
                rl.setId(lm.getId());
                ///////////////
                if(lm.getContentBean() == null) {
                    TextView tv = new TextView(mContext);
                    tv.setFocusable(false);
                    tv.setId(lm.getId() + 1000);
                    tv.setText(lm.getId() + "");
                    tv.setTextSize(30f);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(Color.parseColor("#E6000000"));
                    if (i % 2 == 0) {
                        tv.setBackgroundColor(Color.parseColor("#FF86C739"));
                    } else {
                        tv.setBackgroundColor(Color.parseColor("#FFCCCCCC"));
                    }
                    rl.addView(tv,lpc);
                } else {
                    ImageView iv = new ImageView(mContext);
                    iv.setFocusable(false);
                    iv.setId(lm.getId() + 1000);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    rl.addView(iv,lpc);
                    LayoutModel.LayoutBean.ContentBean cb = null;
                    if("tag_content".equals(lm.getTypeRrc())){// 显示内容时，TypeRrc = “tag_content”
                        cb = lm.getContentBean().get(i);
                    } else {
                        cb = lm.getContentBean().get(0);
                    }
                    if( cb != null) {
                         Glide.with(mContext).load(cb.getSurfaceimage())
                                    .asBitmap() //必须
                                    .transform(new RotateShadowTransformation(mContext))
                                    .into(iv);

                    }

                }
                ///////////////
                addView(rl, lp);
                addFocusItem(lm);

                //////
                rl.setTag(R.id.tag_to_below,lm.getTopid());
                rl.setTag(R.id.tag_to_right,lm.getLeftid());
                rl.setTag(R.id.tag_index,i);

            }
        }

    }

    public static Bitmap createReflectedImage(Bitmap originalImage)
    {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        // 实现图片翻转90度
        matrix.preScale(1, -1);
        // 创建倒影图片（是原始图片的一半大小）
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height , width, height , matrix, false);
        // 创建总图片（原图片 + 倒影图片）
//        Bitmap finalReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.RGB_565);
        // 创建画布
        Canvas canvas = new Canvas(reflectionImage);
        canvas.drawBitmap(originalImage, 0, 0, null);
        //把倒影图片画到画布上
        canvas.drawBitmap(reflectionImage, 0, height + 1, null);
        Paint shaderPaint = new Paint();
        //创建线性渐变LinearGradient对象
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, reflectionImage.getHeight() + 1, 0x70ffffff,
                0x00ffffff, Shader.TileMode.MIRROR);
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画布画出反转图片大小区域，然后把渐变效果加到其中，就出现了图片的倒影效果。
        canvas.drawRect(0, height + 1, width, reflectionImage.getHeight(), shaderPaint);
        return reflectionImage;
    }
}
