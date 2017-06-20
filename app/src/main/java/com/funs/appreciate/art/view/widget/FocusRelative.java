package com.funs.appreciate.art.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.funs.appreciate.art.model.entitys.PictureModel;
import com.funs.appreciate.art.utils.AnimFocusManager;

import java.util.List;


/**
 * Created by yc on 2017/6/12.
 *  水平滑动
 */

public class FocusRelative  extends RelativeLayout{
    /**
     * The view for animation effect of focus and the lost focus event
     */

    private int mSreenWidth;
    private int mSreenHight;
    private float mDensity;

    private Scroller mScroller;
    private int mTouchSlop;
    public AnimFocusManager mAnimationFocusController;
    public Context mContext;
    public int margin;
    public FocusRelative(Context context) {
        super(context);
        mContext = context;
    }

    public FocusRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getDefDisplay();
        initViewGroup(context);
        setmAnimationFocusController();
    }

    public FocusRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void getDefDisplay() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wmManager=(WindowManager) (getContext()). getSystemService(Context.WINDOW_SERVICE);

        mSreenWidth = wmManager.getDefaultDisplay().getWidth();
        mSreenHight = wmManager.getDefaultDisplay().getHeight();
        mDensity = metric.density;
    }

    private void initViewGroup(Context context) {
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    // set Animation
    public void setmAnimationFocusController() {
        mAnimationFocusController = new AnimFocusManager(getContext());
    }

    public void addViews(List<PictureModel> list){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
//        System.out.println("======onMeasure====== width >"+width+"  height "+height);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        System.out.println("======onLayout======>");
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Focus on animation must pass the animation resource Xml ID.
     * @param in Focus disappears animation.
     * @param out Get focus animation.
     */
    public void setAnimation(int in, int out) {
        mAnimationFocusController.setAnimation(in, out);
        setClipChildren(this);
    }
    /**
     * @param view
     */
    private void setClipChildren(ViewParent view) {

        if(view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup)view;
            vg.setClipChildren(false);
            vg.setClipToPadding(false);

            if(!vg.equals(vg.getParent())) {
                setClipChildren(view.getParent());
            }
        }else {
            return;
        }
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
