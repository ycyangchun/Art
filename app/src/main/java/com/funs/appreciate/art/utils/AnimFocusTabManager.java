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
import com.funs.appreciate.art.view.widget.TabFocusRelative;

import java.util.HashMap;

/**
 *  tab
 *  焦点控制动画控制器.
 * @author Liuyongkui
 *
 */
public class AnimFocusTabManager extends  AnimFocusManager{

	TabFocusRelative focusRelative;
	public AnimFocusTabManager(Context c) {
		super(c);
	}
	public AnimFocusTabManager(Context c, TabFocusRelative tabFocusRelative) {
		super(c);
		this.focusRelative = tabFocusRelative;
	}

	public void onFocusChange(View v, boolean hasFocus) {
		super.onFocusChange(v,hasFocus);

		//获取焦点时，设置字体颜色
		if( v instanceof  RelativeLayout) {
			RelativeLayout rl = (RelativeLayout) v;
			int count = rl.getChildCount();
			if (count > 0) {
				TextView tv = (TextView) rl.getChildAt(0);
				focusRelative.recordFocus(hasFocus, rl ,tv);
			}
		}
	}

}
