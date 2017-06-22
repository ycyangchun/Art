package com.funs.appreciate.art.utils;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
		super.onFocusChange(v,hasFocus);
		if( v instanceof  RelativeLayout) {
			RelativeLayout rl = (RelativeLayout) v;
			focusRelative.recordFocus(hasFocus, rl);
		}
	}

}
