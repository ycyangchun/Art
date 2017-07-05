package com.funs.appreciate.art.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.view.widget.PictureFocusRelative;
import com.funs.appreciate.art.view.widget.TabFocusRelative;

/**
 *  content
 *  焦点控制动画控制器.
 * @author Liuyongkui
 *
 */
public class AnimFocusContentManager extends  AnimFocusManager{

	PictureFocusRelative focusRelative;
	public AnimFocusContentManager(Context c) {
		super(c);
	}
	public AnimFocusContentManager(Context c, PictureFocusRelative tabFocusRelative) {
		super(c);
		this.focusRelative = tabFocusRelative;
	}

	public void onFocusChange(View v, boolean hasFocus) {
        ////////////////////////////
        final RelativeLayout rl = (RelativeLayout) v;
        if(hasFocus) {
            focusView = v;
        }
        if(hasFocus && isAvailability()) {

            Animation anim = AnimationUtils.loadAnimation(mContext, animationOut);
            v.bringToFront();
            v.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }else if(isAvailability()){
            Animation anim = AnimationUtils.loadAnimation(mContext, animationIn);
            v.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
               @Override
               public void onAnimationStart(Animation animation) {

               }

               @Override
               public void onAnimationEnd(Animation animation) {

               }

               @Override
               public void onAnimationRepeat(Animation animation) {

               }
           });
        }

        if(focusPool.containsKey(v)) {
            focusPool.get(v).onFocusChange(v, hasFocus);
        }
        focusRelative.recordFocus(hasFocus, rl);
	}

}
