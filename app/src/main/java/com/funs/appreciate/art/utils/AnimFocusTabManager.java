package com.funs.appreciate.art.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funs.appreciate.art.presenter.MainContract;

import java.util.HashMap;

/**
 *  tab
 *  焦点控制动画控制器.
 * @author Liuyongkui
 *
 */
public class AnimFocusTabManager extends  AnimFocusManager{

	private TabSelect tabSelect;
	public AnimFocusTabManager(Context c) {
		super(c);
	}

	public void onFocusChange(View v, boolean hasFocus) {
		super.onFocusChange(v,hasFocus);

		//获取焦点时，设置字体颜色
		if( v instanceof  RelativeLayout) {
			RelativeLayout rl = (RelativeLayout) v;
			int count = rl.getChildCount();
			if (count > 0) {
				TextView tv = (TextView) rl.getChildAt(0);
				tabSelect.itemListener(hasFocus, rl ,tv);
			}
		}
	}

	public void setTabSelect(TabSelect tabSelect) {
		this.tabSelect = tabSelect;
	}

	public interface TabSelect{
		void itemListener(boolean hasFocus ,RelativeLayout rl , TextView tv);
	}


}
