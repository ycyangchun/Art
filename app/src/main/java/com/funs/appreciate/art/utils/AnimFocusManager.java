package com.funs.appreciate.art.utils;

import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.HashMap;

/**
 * 焦点控制动画控制器.
 * @author Liuyongkui
 *
 */
public class AnimFocusManager implements OnFocusChangeListener{
	public int animationIn = -1;
	public int animationOut = -1;
	public boolean animationFocusLock = false;
	public View focusView = null;
	public HashMap<View, OnFocusChangeListener> focusPool = new HashMap<View, OnFocusChangeListener>();
	public Context mContext;
	
	public AnimFocusManager(Context c) {
		if(c == null) {
			throw new IllegalArgumentException("the context is null");
		}
		mContext = c;
	}
	
	/**
	 *  it is Animation is locked ;
	 *  set AnimationFocusLocked ,
	 *  To facilitate the other animation not to perform
	 * @param lock
	 */
	public void setAnimationFocusLock(boolean lock) {
		boolean oldLock = animationFocusLock;
		if(oldLock == lock) {
			return;
		}
		animationFocusLock = lock;
		if(animationFocusLock && animationIn != -1 && focusView != null) {
			focusView.startAnimation(AnimationUtils.loadAnimation(mContext, animationIn));
		}else if(!animationFocusLock && animationOut != -1 && focusView != null) {
			focusView.bringToFront();
			focusView.startAnimation(AnimationUtils.loadAnimation(mContext, animationOut));
		}
	}
	
	public void setAnimation(int in, int out) {
		this.animationIn = in;
		this.animationOut = out;
	}
	
	public boolean isAvailability() {
		return !animationFocusLock && animationOut != -1  && animationIn != -1;
	}
	
	public void add(View v, OnFocusChangeListener l) {
		focusPool.put(v, l);
	}
	
	public void delete(View v) {
		focusPool.remove(v);
	}
	
	public void clear() {
		focusPool.clear();
		focusView = null;
	}
	
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus) {
			focusView = v;
		}
		if(hasFocus && isAvailability()) {
			
			Animation anim = AnimationUtils.loadAnimation(mContext, animationOut);
			v.bringToFront();
			v.startAnimation(anim);
		}else if(isAvailability()){
			Animation anim = AnimationUtils.loadAnimation(mContext, animationIn);
			v.startAnimation(anim);
		}
		
		if(focusPool.containsKey(v)) {
			focusPool.get(v).onFocusChange(v, hasFocus);
		}
	}

}
