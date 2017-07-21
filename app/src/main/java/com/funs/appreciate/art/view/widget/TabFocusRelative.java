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
    private int itemFocusIndex ,marginS;// 获取焦点 item
    public RelativeLayout focusView , selectView; // 记录焦点view ; 记录tab 选中view( 按下方向键焦点转移到fragment时  ；fragment 左右切换时 )
    private int colorDefault, colorSelect;
    TabSelect tabSelect;
    public TabFocusRelative(Context context) {
        super(context);
    }

    public TabFocusRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        margin = 2;
        childViews = new ArrayList<>();
        colorDefault = Color.parseColor("#ABAEB7");
        colorSelect = Color.parseColor("#FFFEFF");
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
                final UIHelper.Size size = new UIHelper.Size(UIHelper.zoomW(lm.getWidth(), UIHelper.ZoomMode.KeepHV), UIHelper.zoomH(lm.getHeight(), UIHelper.ZoomMode.KeepHV));
                LayoutParams lp = new LayoutParams(size.width, size.height);

                if (lm.getTopid() != 0)
                    lp.addRule(RelativeLayout.BELOW, lm.getTopid());
                if (lm.getLeftid() != 0)
                    lp.addRule(RelativeLayout.RIGHT_OF, lm.getLeftid());
                int marginW = UIHelper.zoomW(margin, UIHelper.ZoomMode.KeepHV);
                int marginH = UIHelper.zoomH(margin, UIHelper.ZoomMode.KeepHV);
                marginS = UIHelper.zoomW(10, UIHelper.ZoomMode.KeepHV);
//                lp.setMargins(marginW, marginH, marginW, marginH);

                ///////////////
                TextView tv = new TextView(mContext);
                tv.setFocusable(false);
                tv.setId(lm.getId()+ 1000);
                tv.setText(lm.getTypeRrc());
                tv.setTextSize(marginS * 3f);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(colorDefault);
                LayoutParams lpc = new LayoutParams(size.width,size.height);

                RelativeLayout rl = lm.getRootView();
                rl.setId(lm.getId());
                rl.addView(tv,lpc);
                ///////////////
                // 阴影
                StateListDrawable bg = ImageHelper.makeSelector(mContext.getResources(),
                        null ,
                        null ,
                        mContext.getResources().getDrawable(R.drawable.tab_foucs_bg2), null);
                rl.setBackground(bg);
                addView(rl, lp);
                childViews.add(rl);
                addFocusItem(lm);

                final int finalI = i;
                rl.setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if(event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {//导航失去焦点
                                v.setTag("downFragment");
                            }
                            // 焦点右移最后一项
                            if( (finalI == lms.size()-1) && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                                return true;
                            }
                            // 焦点最左边
                            if( (finalI == 0) && keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
                                return true;
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
        focusView = rl;

        if(hasFocus) { // 字体颜色
//            tv.setTextColor(colorSelect);
            setChildTextColor(rl , colorSelect);
            // 上次选择的view
            if(selectView != null && rl != selectView){
                setChildTextColor(selectView , colorDefault);
            }
        } else {// 默认颜色
            //记录tab 选中view( 按下方向键焦点转移到fragment时)
            String tag = (String) rl.getTag();
            if(tag != null && tag.equals("downFragment")){
                rl.setTag("");
                selectView = rl;
                setChildTextColor(selectView , colorSelect);
            } else {
//                tv.setTextColor(colorDefault);
                setChildTextColor(rl , colorDefault);
            }
        }

        // tab 切换
       if(hasFocus && colorSelect == tv.getCurrentTextColor()){
           tabSelect.tabChangeListener(tv.getText().toString());
       }
    }

    // 焦点最后变化 view
    public RelativeLayout getLastFocusChangeView() {
        return focusView;
    }

    // 左右翻页
    public int switchPageNext(int switchType , int index){
        int nextIndex = 0;
        switch(switchType){
            case ArtConstants.KEYLEFT:
                nextIndex = index - 1 >= 0 ? index - 1 : 0;
//                System.out.println("========== tab 左翻页 ==>"+ nextIndex + "  lastFocusChangeIndex "+index);
                break;
            case ArtConstants.KEYRIGHT:
                nextIndex = index +1  ==  childViews.size()?  index   : index +1;
//                System.out.println("========== tab 右翻页 ==>"+nextIndex+ "  lastFocusChangeIndex "+index);
                break;
        }
        return  nextIndex;
    }

    //左右翻页设置text颜色
    public void setTextColorByPageChange(int index){
        RelativeLayout rl = childViews.get(index);
        if(rl != null){
            setChildTextColor(rl , colorSelect);
        }
        // 上次选择的view
        if(selectView != null && rl != selectView){
            setChildTextColor(selectView , colorDefault);
        }
        selectView = rl;
        focusView = rl;
    }

    // 设置 relative child的文本颜色
    private void setChildTextColor(RelativeLayout rl , int textColor) {
        int count = rl.getChildCount();
        if (count > 0) {
            TextView tv = (TextView) rl.getChildAt(0);
            tv.setTextColor(textColor);
            if(textColor == colorDefault){
                tv.setTextSize(marginS * 3f );
            } else if(textColor == colorSelect){
                tv.setTextSize(marginS * 3f * 1.3f);
            }
        }
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


    // itemFocus
    public void setIndexFocus(int index){
        itemFocusIndex = index;
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                lms.get(itemFocusIndex).getRootView().requestFocus();
            }
        },100);
    }
    public void setmAnimationFocusController() {
        mAnimationFocusController = new AnimFocusTabManager(getContext(), this);
    }

    /**
     *
     * @param lock  true  else  ,false DEF:false
     */
    public void setAnimationFocusLock(boolean lock) {
        mAnimationFocusController.setAnimationFocusLock(lock);
    }

    public void addFocusItem(PictureModel lm) {

        OnFocusChangeListener l = lm.getRootView().getOnFocusChangeListener();
        if(l != null) {
            mAnimationFocusController.add(lm.getRootView(), l);
        }
        lm.getRootView().setOnFocusChangeListener(mAnimationFocusController);
    }

    // tab 切换
    public interface TabSelect{
        void tabChangeListener(String tab);
    }

    public void setTabSelect(TabSelect tabSelect) {
        this.tabSelect = tabSelect;
    }


}
