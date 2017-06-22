package com.funs.appreciate.art.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.utils.ImageHelper;
import com.funs.appreciate.art.utils.UIHelper;

import java.util.List;


/**
 * Created by yc on 2017/6/12.
 *  水平滑动
 */

public class PictureFocusRelative extends FocusRelative {

    private List<PictureModel> lms;
    private boolean penultimate = false; // 布局倒数第二个是否靠边
    private PictureFocusKeyEvent mPictureFocusKeyEvent;
    public PictureFocusRelative(Context context) {
        super(context);
    }

    public PictureFocusRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        margin = 10;
    }

    public PictureFocusRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void addViews(final List<PictureModel> list){
        if (list == null)
            throw new IllegalArgumentException("nullPointer addViews  PictureModel  list ");
        lms = list;
        if(lms != null && lms.size() != 0) {
            for (int i = 0; i < lms.size(); i++) {
                final PictureModel lm = lms.get(i);
                UIHelper.Size size = new UIHelper.Size(UIHelper.zoomW(lm.getWidth(), UIHelper.ZoomMode.KeepHV), UIHelper.zoomH(lm.getHeight(), UIHelper.ZoomMode.KeepHV));
//                System.out.println("====== width ======== height  ==========>"+size.width+ " "+size.height);
                LayoutParams lp = new LayoutParams(size.width, size.height);

                if (lm.getTopid() != 0)
                    lp.addRule(RelativeLayout.BELOW, lm.getTopid());
                if (lm.getLeftid() != 0)
                    lp.addRule(RelativeLayout.RIGHT_OF, lm.getLeftid());
                int marginW = UIHelper.zoomW(margin, UIHelper.ZoomMode.KeepHV);
                int marginH = UIHelper.zoomH(margin, UIHelper.ZoomMode.KeepHV);
//                System.out.println("====== marginW ====== marginH ==========>"+marginW+ " "+marginH);
//                System.out.println("================>"+lm.getId()+"  "+lm.getTopid()+ "  "+lm.getLeftid());

                lp.setMargins(marginW, marginH, marginW, marginH);

                ///////////////
                TextView tv = new TextView(mContext);
                tv.setFocusable(false);
                tv.setId(lm.getId()+ 1000);
                tv.setText(lm.getId()+"");
                tv.setTextSize(30f);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#E6000000"));
                if( i % 2 == 0) {
                    tv.setBackgroundColor(Color.parseColor("#FF86C739"));
                } else {
                    tv.setBackgroundColor(Color.parseColor("#FFCCCCCC"));
                }
                LayoutParams lpc = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//                lpc.setMargins(6, 6, 6, 6);
                final RelativeLayout rl = lm.getFocusView();
                rl.setId(lm.getId());
                rl.addView(tv,lpc);
                ///////////////
                // 阴影
                StateListDrawable bg = ImageHelper.makeSelector(mContext.getResources(), null ,
                        mContext.getResources().getDrawable(R.drawable.not_foucs_shadow),
                        mContext.getResources().getDrawable(R.drawable.foucs_bg2), null);
                rl.setBackground(bg);
                addView(rl, lp);
                addFocusItem(lm);

                //////
                rl.setTag(R.id.tag_to_below,lm.getTopid());
                rl.setTag(R.id.tag_to_right,lm.getLeftid());
                rl.setTag(R.id.tag_index,i);
                rl.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            switch (keyCode) {
                                case KeyEvent.KEYCODE_DPAD_LEFT:
                                case KeyEvent.KEYCODE_DPAD_RIGHT:
                                case KeyEvent.KEYCODE_DPAD_UP:
                                    // 布局位于 top 边界
                                    if (keyCode == KeyEvent.KEYCODE_DPAD_UP && (int) rl.getTag(R.id.tag_to_below) == 0) {
//                                        System.out.println("== top === >" + rl.getId());
                                        mPictureFocusKeyEvent.pictureListener("top",lm);
                                        return true;
                                    }

                                    // 布局位于 left边界
                                    if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && (int) rl.getTag(R.id.tag_to_right) == 0) {
//                                        System.out.println("== left === >" + rl.getId());
                                        mPictureFocusKeyEvent.pictureListener("left",lm);
                                        return true;
                                    }
                                    // 布局位于 right 边界  ：数据倒数第一个 （或  倒数第二个）
                                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                                        int tag_index = (int) rl.getTag(R.id.tag_index);
                                        int size = lms.size();
                                        if (size >= 1 && tag_index == size - 1) {//数据倒数第一个
//                                            System.out.println("== right === >" + rl.getId());
                                            mPictureFocusKeyEvent.pictureListener("right",lm);
                                            return true;
                                        }

                                        if(penultimate(rl)){//倒数第二个是靠边的
//                                            System.out.println("== right === >" + rl.getId());
                                            mPictureFocusKeyEvent.pictureListener("right",lm);
                                            return true;
                                        }
                                    }
                                    break;

                                case KeyEvent.KEYCODE_DPAD_CENTER:
                                case KeyEvent.KEYCODE_ENTER:
                                    mPictureFocusKeyEvent.pictureListener("center",lm);
                                    return true;
                            }
                        }
                        return false;
                    }
                });
            }
        }

    }

    public boolean penultimate(RelativeLayout rl){
        int size = lms.size();
        int tag_index = (int) rl.getTag(R.id.tag_index);
        if(size >= 2 && tag_index == size -2){
            PictureModel lm1 = lms.get(size -1);
            PictureModel lm2 = lms.get(size -2);
            //(在同一元素的右边)
            if(lm2.getLeftid() == lm1.getLeftid()){
                penultimate = true;
            }

        } else {
            penultimate = false;
        }
        return penultimate;
    }
    public void setIndexFocus(final int index){
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                lms.get(index).getFocusView().requestFocus();
            }
        },200);
    }

    public interface PictureFocusKeyEvent{
        void pictureListener(String keyType, PictureModel lm);
    }

    public void setmPictureFocusKeyEvent(PictureFocusKeyEvent mPictureFocusKeyEvent) {
        this.mPictureFocusKeyEvent = mPictureFocusKeyEvent;
    }
}
