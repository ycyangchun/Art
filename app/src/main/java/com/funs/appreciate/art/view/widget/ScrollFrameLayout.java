package com.funs.appreciate.art.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollFrameLayout extends LinearLayout {
	
	private Scroller mScroller;
	private Runnable runnable;
	private boolean started = false;

	public ScrollFrameLayout(Context context) {
		super(context);
	}
	
	public ScrollFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScroller(Scroller mScroller){
		this.mScroller = mScroller;
	}
	
	public void addOnComplete(Runnable runnable){
		this.runnable = runnable;
	}
	
	public void removeOnCompete(){
		runnable = null;
		started = false;
	}

	@Override
	public void computeScroll() {
		if(mScroller == null){
			super.computeScroll();
		}
		else{
			if(mScroller.computeScrollOffset()){
				started = true;
				scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
				postInvalidate();
			}
			else if(started){ 
				if (runnable != null)
					runnable.run();
				started = false;
			}
		}
	}

	
	
	
	
	
}
