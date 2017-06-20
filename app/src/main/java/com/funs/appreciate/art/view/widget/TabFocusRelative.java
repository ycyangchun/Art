package com.funs.appreciate.art.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.ArtConstants;
import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.utils.AnimFocusManager;
import com.funs.appreciate.art.utils.AnimFocusTabManager;
import com.funs.appreciate.art.utils.ImageHelper;
import com.funs.appreciate.art.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yc on 2017/6/12.
 *  水平滑动
 */

public class TabFocusRelative extends FocusRelative {

    private List<PictureModel> lms;
    public List<RelativeLayout> childViews;
    private int itemFocusIndex, lastFocusChangeIndex;// 获取焦点 item ;  最后焦点变化view的index
    public RelativeLayout lastFocusChangeView , lastFocusTextChangeView; // 最后焦点变化view ; 最后text变化view( 按下键，焦点转移到fragment时)
    public TabFocusRelative(Context context) {
        super(context);
    }

    public TabFocusRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        margin = 10;
        childViews = new ArrayList<>();
    }

    public TabFocusRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void addViews(List<PictureModel> list){
        if (list == null)
            throw new IllegalArgumentException("nullPointer addViews  PictureModel  list ");
        lms = list;
        if(lms != null && lms.size() != 0) {
            for (int i = 0; i < lms.size(); i++) {
                PictureModel lm = lms.get(i);
                UIHelper.Size size = new UIHelper.Size(UIHelper.zoomW(lm.getWidth(), UIHelper.ZoomMode.KeepHV), UIHelper.zoomH(lm.getHeight(), UIHelper.ZoomMode.KeepHV));
                LayoutParams lp = new LayoutParams(size.width, size.height);

                if (lm.getToBelowId() != 0)
                    lp.addRule(RelativeLayout.BELOW, lm.getToBelowId());
                if (lm.getToRightId() != 0)
                    lp.addRule(RelativeLayout.RIGHT_OF, lm.getToRightId());
                int marginW = UIHelper.zoomW(margin, UIHelper.ZoomMode.KeepHV);
                int marginH = UIHelper.zoomH(margin, UIHelper.ZoomMode.KeepHV);
                lp.setMargins(marginW, marginH, marginW, marginH);

                ///////////////
                TextView tv = new TextView(mContext);
                tv.setFocusable(false);
                tv.setId(lm.getId()+ 1000);
                tv.setText(lm.getTypeRrc());
                tv.setTextSize(marginW * 2.2f);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.parseColor("#E6000000"));
                LayoutParams lpc = new LayoutParams(size.width,size.height);

                RelativeLayout rl = lm.getFocusView();
                rl.setId(lm.getId());
                rl.addView(tv,lpc);
                ///////////////
                // 阴影
                StateListDrawable bg = ImageHelper.makeSelector(mContext.getResources(), null ,
                        mContext.getResources().getDrawable(R.drawable.not_foucs_shadow),
                        mContext.getResources().getDrawable(R.drawable.tab_foucs_bg2), null);
                rl.setBackground(bg);
                addView(rl, lp);
                childViews.add(rl);
                addFocusItem(lm);

                rl.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {//导航失去焦点
                                v.setTag("lastDown");
                            }
                        }
                        return false;
                    }
                });
            }
        }
    }

    // 记录焦点变化
    public void recordFocus(boolean hasFocus ,RelativeLayout rl ,TextView tv){
        if(hasFocus) { // 字体颜色
            tv.setTextColor(Color.parseColor("#01FFFF"));

            // 上次 最后text变化view，和再次获取焦点不是同一个 view
            if(lastFocusTextChangeView != null && rl != lastFocusTextChangeView){
                int count = lastFocusTextChangeView.getChildCount();
                if (count > 0) {
                    TextView tv2 = (TextView) lastFocusTextChangeView.getChildAt(0);
                    tv2.setTextColor(Color.parseColor("#E6000000"));
                }
            }
        } else {
            tv.setTextColor(Color.parseColor("#E6000000"));
        }
        lastFocusChangeView = rl;

        //字体最后变化view
        String tag = (String) rl.getTag();
        if(tag != null && tag.equals("lastDown")){
            rl.setTag("");
            lastFocusTextChangeView = rl;
            tv.setTextColor(Color.parseColor("#01FFFF"));
        }

    }

    // 焦点最后变化 view
    public RelativeLayout getLastFocusChangeView() {
        return lastFocusChangeView;
    }

    // 左右翻页
    public RelativeLayout switchPage(int switchType){
        int nextIndex = 0;
        switch(switchType){
            case ArtConstants.KEYLEFT:
                nextIndex = lastFocusChangeIndex - 1 >= 0 ? lastFocusChangeIndex - 1 : 0;
                System.out.println("========== tab 左翻页 ==>"+ nextIndex + "  lastFocusChangeIndex "+lastFocusChangeIndex);
                break;
            case ArtConstants.KEYRIGHT:
                nextIndex = lastFocusChangeIndex +1  ==  childViews.size()?  lastFocusChangeIndex   : lastFocusChangeIndex +1;
                System.out.println("========== tab 右翻页 ==>"+nextIndex+ "  lastFocusChangeIndex "+lastFocusChangeIndex);
                break;
        }
        return  childViews.get(nextIndex);
    }

    // v 是否属于 childViews
    public boolean isChileView(View v){
        boolean is = false;
        if(v instanceof  RelativeLayout){
            RelativeLayout rv = (RelativeLayout)v;
            if(childViews.contains(rv)){
                is = true;
            }
        }
        return is;
    }

    // lastFocusChangeIndex
    public void setLastFocusChangeIndex(int lastFocusChangeIndex) {
        this.lastFocusChangeIndex = lastFocusChangeIndex;
    }

    // itemFocus
    public void setIndexFocus(int index){
        itemFocusIndex = index;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                lms.get(itemFocusIndex).getFocusView().requestFocus();
            }
        },200);
    }
    public void setmAnimationFocusController() {
        mAnimationFocusController = new AnimFocusTabManager(getContext());
    }

    public void setTabSelect(AnimFocusTabManager.TabSelect tabSelect) {
        ((AnimFocusTabManager)mAnimationFocusController).setTabSelect(tabSelect);
    }
    /**
     *
     * @param lock  true  else  ,false DEF:false
     */
    public void setAnimationFocusLock(boolean lock) {
        mAnimationFocusController.setAnimationFocusLock(lock);
    }

    public void addFocusItem(PictureModel lm) {

        OnFocusChangeListener l = lm.getFocusView().getOnFocusChangeListener();
        if(l != null) {
            mAnimationFocusController.add(lm.getFocusView(), l);
        }
        lm.getFocusView().setOnFocusChangeListener(mAnimationFocusController);
    }


}
