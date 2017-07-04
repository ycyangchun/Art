package com.funs.appreciate.art.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public final class UIHelper {

	private static float density = 1.0f;
	
	private static int statusBarHeight = 0;

	private static int standardResolutionX = 1080;
	private static int standardResolutionY = 1920;

	private static int currentResolutionX = 0;
	private static int currentResolutionY = 0;
	
	private static float zoomFactorX = 1.0f;
	private static float zoomFactorY = 1.0f;
	
	private static boolean initialized = false;
	
	public static final int Unit_PX = TypedValue.COMPLEX_UNIT_PX;


	public static void initialize(Activity activity, boolean hLarge){
		if(!initialized){
			initialized = true;
			getResolution(activity, hLarge);
			measureResolutionZoomFactor();
		}
	}

	public static int dip2px(float dipValue) {
		return (int) (dipValue * density + 0.5f);
	}

	public static int px2dip(float pxValue) {
		return (int) (pxValue / density + 0.5f);
	}

	public static int getCurrentResolutionX(){
		return currentResolutionX;
	}
	
	public static int getCurrentResolutionY(){
		return currentResolutionY;
	}
	
	public static int getStandardResolutionX(){
		return standardResolutionX;
	}
	
	public static int getStandardResolutionY(){
		return standardResolutionY;
	}
	
 	private static void getResolution(Activity activity, boolean hLarge){
		if(activity != null){
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			currentResolutionX = dm.widthPixels;
			currentResolutionY = dm.heightPixels;
			density = dm.density;
			System.out.println("====== UIHelper =======>"+dm.widthPixels+" "+dm.heightPixels+" "+dm.density);
			if(hLarge){
				if(currentResolutionY > currentResolutionX){
					int temp = currentResolutionX;
					currentResolutionX = currentResolutionY;
					currentResolutionY = temp;
				}
			}
			else{
				if(currentResolutionX > currentResolutionY){
					int temp = currentResolutionX;
					currentResolutionX = currentResolutionY;
					currentResolutionY = temp;
				}
			}
			
			Rect rect = new Rect();
			activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
			statusBarHeight = rect.top;
			if (statusBarHeight <= 0) {
				Class<?> c = null;
				Object obj = null;
				Field field = null;
				int x = 0;
				try {
					c = Class.forName("com.android.internal.R$dimen");
					obj = c.newInstance();
					field = c.getField("status_bar_height");
					x = Integer.parseInt(field.get(obj).toString());
					statusBarHeight = activity.getResources().getDimensionPixelSize(x);					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	private static void measureResolutionZoomFactor(){
		zoomFactorX = (float)getCurrentResolutionX()/standardResolutionX  * (1 + density / 100);
		zoomFactorY = (float)getCurrentResolutionY()/standardResolutionY  * (1 + density / 100);
	}
	
	
	public static int getStatusBarHeight(){
		return statusBarHeight;
	}
	
	public static float getZoomFactorX(){
		return zoomFactorX;
	}
	
	public static float getZoomFactorY(){
		return zoomFactorY;
	}
	
	public static float getMinZoomFactor(){
		return Math.min(zoomFactorX, zoomFactorY);
	}
	
	public static float getMaxZoomFactor(){
		return Math.max(zoomFactorX, zoomFactorY);
	}
	
	public static int zoomInt(float zoomFactor, int value){
		return (int) Math.floor(value*zoomFactor);
	}
	
	public static Rect zoomRect(float zoomFactor, Rect value) {
		if(value != null){
			Rect rect = new Rect(value);
			rect.left *= zoomFactor;
			rect.right *= zoomFactor;
			rect.top *= zoomFactor;
			rect.bottom *= zoomFactor;
			return rect;
		}
		
		return null;
	}
	
	public static Rect zoomRect(Rect value) {
		if(value != null){
			Rect rect = new Rect(value);
			rect.left *= zoomFactorX;
			rect.right *= zoomFactorX;
			rect.top *= zoomFactorY;
			rect.bottom *= zoomFactorY;
			return rect;
		}
		
		return null;
	}
	
	public static Rect zoomRect(int l, int t, int r, int b) {
		Rect rect = new Rect(l, t, r, b);
		rect.left *= zoomFactorX;
		rect.right *= zoomFactorX;
		rect.top *= zoomFactorY;
		rect.bottom *= zoomFactorY;
		return rect;
	}

	public static Rect zoomRect2(int l, int t, int w, int h) {
		Rect rect = new Rect();
		rect.left = (int)(l*zoomFactorX);
		rect.right =  rect.left + zoomW(w, ZoomMode.KeepHV);
		rect.top = (int)(t*zoomFactorY);
		rect.bottom = rect.top + zoomH(h, ZoomMode.KeepHV);
		return rect;
	}

	public static int zoomIntMin(int value){
		return (int)(value* Math.min(zoomFactorX, zoomFactorY));
	}
	
	public static int zoomIntHorizontal(int value){
		return (int) Math.floor(value*zoomFactorX);
	}
	
	public static int zoomIntVertical(int value){
		return (int) Math.floor(value*zoomFactorY);
	}
	
	public static float zoomFloatHorizontal(int value){
		return value*zoomFactorX;
	}
	
	public static float zoomFloatVertical(int value){
		return value*zoomFactorY;
	}

	public static float zoomFontHorizontal(int value){
		return value*zoomFactorX;
	}
	public static float zoomFontVertical(int value){
		return value*zoomFactorY;
	}
	public static float zoomFontMin(int value){
		return value* Math.min(zoomFactorX, zoomFactorY);
	}
	public static float zoomFontMax(int value){
		return value* Math.max(zoomFactorX, zoomFactorY);
	}
	public static float zoomFontHorizontal(float value){
		return value*zoomFactorX;
	}
	public static float zoomFontVertical(float value){
		return value*zoomFactorY;
	}
	public static float zoomFontMin(float value){
		return value* Math.min(zoomFactorX, zoomFactorY);
	}
	public static float zoomFontMax(float value){
		return value* Math.max(zoomFactorX, zoomFactorY);
	}

	/**
	 * 设置在RelativeLayout中的View的位置
	 */
	public static void layoutWithRelative(View view, int x, int y){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin = x;
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在RelativeLayout中的View的位置、大小
	 */
	public static void layoutWithRelative(View view, int x, int y, int width, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(width, height);
			}
			else{
				layoutParams.width = width;
				layoutParams.height = height;
			}
			layoutParams.leftMargin = x;
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在RelativeLayout中的View的位置、大小
	 */
	public static void layoutWithRelative(View view, Rect rect){
		if(view != null && rect != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(rect.width(), rect.height());
			}
			else{
				layoutParams.width = rect.width();
				layoutParams.height = rect.height();
			}
			layoutParams.leftMargin = rect.left;
			layoutParams.topMargin = rect.top;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在RelativeLayout中的View的left
	 */
	public static void layoutLeftWithRelative(View view, int x){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin = x;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在RelativeLayout中的View的top
	 */
	public static void layoutTopWithRelative(View view, int y){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在RelativeLayout中的View的大小
	 */
	public static void layoutSizeWithRelative(View view, int width, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(width, height);
			}
			else{
				layoutParams.width = width;
				layoutParams.height = height;
			}
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在RelativeLayout中的View的width
	 */
	public static void layoutWidthWithRelative(View view, int width){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			else{
				layoutParams.width = width;
			}
			view.setLayoutParams(layoutParams);
		}
	}

	
	/**
	 * 设置在RelativeLayout中的View的height
	 */
	public static void layoutHeightWithRelative(View view, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
			}
			else{
				layoutParams.height = height;
			}
			view.setLayoutParams(layoutParams);
		}
	}
	

	/**
	 * 获取在RelativeLayout中的View的位置、大小
	 */
	public static Rect layoutWithRelative(View view){
		Rect rect = null;
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams != null){
				rect = new Rect();
				rect.left = layoutParams.leftMargin;
				rect.top = layoutParams.topMargin;
				rect.right = rect.left + layoutParams.width;
				rect.bottom = rect.top + layoutParams.height;
			}
		}
		return rect;
	}	
	

	/**
	 * 设置在FrameLayout中的View的位置
	 */
	public static void layoutWithFrame(View view, int x, int y){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin = x;
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}	

	/**
	 * 设置在FrameLayout中的View的位置、大小
	 */
	public static void layoutWithFrame(View view, int x, int y, int width, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(width, height);
			}
			else{
				layoutParams.width = width;
				layoutParams.height = height;
			}
			layoutParams.leftMargin = x;
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在FrameLayout中的View的位置、大小
	 */
	public static void layoutWithFrame(View view, Rect rect){
		if(view != null && rect != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(rect.width(), rect.height());
			}
			else{
				layoutParams.width = rect.width();
				layoutParams.height = rect.height();
			}
			layoutParams.leftMargin = rect.left;
			layoutParams.topMargin = rect.top;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在FrameLayout中的View的left
	 */
	public static void layoutLeftWithFrame(View view, int x){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin = x;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在FrameLayout中的View的left
	 */
	public static void layoutByLeftWithFrame(View view, int dx){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin += dx;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在FrameLayout中的View的top
	 */
	public static void layoutTopWithFrame(View view, int y){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}
	/**
	 * 设置在FrameLayout中的View的right
	 */
	public static void layoutRightWithFrame(View view, int r){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.rightMargin = r;
			view.setLayoutParams(layoutParams);
		}
	}
	
	
	/**
	 * 设置在FrameLayout中的View的top
	 */
	public static void layoutByTopWithFrame(View view, int dy){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.topMargin += dy;
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在FrameLayout中的View的大小
	 */
	public static void layoutSizeWithFrame(View view, int width, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(width, height);
			}
			else{
				layoutParams.width = width;
				layoutParams.height = height;
			}
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在FrameLayout中的View的width
	 */
	public static void layoutWidthWithFrame(View view, int width){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			else{
				layoutParams.width = width;
			}
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在FrameLayout中的View的height
	 */
	public static void layoutHeightWithFrame(View view, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
			}
			else{
				layoutParams.height = height;
			}
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 获取在FrameLayout中的View的位置、大小
	 */
	public static Rect layoutWithFrame(View view){
		Rect rect = null;
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams != null){
				rect = new Rect();
				rect.left = layoutParams.leftMargin;
				rect.top = layoutParams.topMargin;
				rect.right = rect.left + layoutParams.width;
				rect.bottom = rect.top + layoutParams.height;
			}
		}
		return rect;
	}
	
	
	/**
	 * 设置在LinearLayout中的View的位置
	 */
	public static void layoutWithLinear(View view, int x, int y){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin = x;
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}	

	/**
	 * 设置在LinearLayout中的View的位置、大小
	 */
	public static void layoutWithLinear(View view, int x, int y, int width, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(width, height);
			}
			else{
				layoutParams.width = width;
				layoutParams.height = height;
			}
			layoutParams.leftMargin = x;
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在LinearLayout中的View的位置、大小
	 */
	public static void layoutWithLinear(View view, Rect rect){
		if(view != null && rect != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(rect.width(), rect.height());
			}
			else{
				layoutParams.width = rect.width();
				layoutParams.height = rect.height();
			}
			layoutParams.leftMargin = rect.left;
			layoutParams.topMargin = rect.top;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在LinearLayout中的View的left
	 */
	public static void layoutLeftWithLinear(View view, int x){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin = x;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在LinearLayout中的View的left
	 */
	public static void layoutByLeftWithLinear(View view, int dx){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.leftMargin += dx;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在LinearLayout中的View的top
	 */
	public static void layoutTopWithLinear(View view, int y){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.topMargin = y;
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在LinearLayout中的View的top
	 */
	public static void layoutByTopWithLinear(View view, int dy){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			layoutParams.topMargin += dy;
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在LinearLayout中的View的大小
	 */
	public static void layoutSizeWithLinear(View view, int width, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(width, height);
			}
			else{
				layoutParams.width = width;
				layoutParams.height = height;
			}
			view.setLayoutParams(layoutParams);
		}
	}
	
	/**
	 * 设置在LinearLayout中的View的width
	 */
	public static void layoutWidthWithLinear(View view, int width){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
			}
			else{
				layoutParams.width = width;
			}
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 设置在LinearLayout中的View的height
	 */
	public static void layoutHeightWithLinear(View view, int height){
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams == null){
				layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
			}
			else{
				layoutParams.height = height;
			}
			view.setLayoutParams(layoutParams);
		}
	}

	/**
	 * 获取在LinearLayout中的View的位置、大小
	 */
	public static Rect layoutWithLinear(View view){
		Rect rect = null;
		if(view != null){
			ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
			if(layoutParams != null){
				rect = new Rect();
				rect.left = layoutParams.leftMargin;
				rect.top = layoutParams.topMargin;
				rect.right = rect.left + layoutParams.width;
				rect.bottom = rect.top + layoutParams.height;
			}
		}
		return rect;
	}
	
	
	public static Rect getRect(View view){
		Rect rect = new Rect();
		if(view != null){
			ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams)view.getLayoutParams();
			if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams){
				ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams)layoutParams;
				rect.left = lp.leftMargin;
				rect.top = lp.topMargin;
				rect.right = rect.left + lp.width;
				rect.bottom = rect.top + lp.height;
			}
		}
		return rect;
	}
	
	
	/************************************************************/
	/************************************************************/
	/*==========================================================*/
	/*                           缩放操作                                                                  */
	/*==========================================================*/
	/************************************************************/
	/************************************************************/
	
	public enum ZoomMode{
		KeepHV, 
		KeepXYSizeH, 
		KeepXYSizeV,
		KeepXYSizeMin,
		KeepXMinOther,
		KeepYMinOther,
		ForceH, 
		ForceV,
		Minimum;
	}
	
	public static class Size{
		public int width;
		public int height;
		
		public Size(int width, int height){
			this.width = width;
			this.height = height;
		}
	}
	
	public static int zoomX(int x, ZoomMode zoomMode){
		switch (zoomMode) {
		case KeepHV:
			x = zoomIntHorizontal(x);
			break;
		case KeepXYSizeH:
			x = zoomIntHorizontal(x);
			break;
		case KeepXYSizeV:
			x = zoomIntHorizontal(x);
			break;
		case KeepXYSizeMin:
			x = zoomIntHorizontal(x);
			break;
		case KeepXMinOther:
			x = zoomIntHorizontal(x);
			break;
		case KeepYMinOther:
			x = zoomIntMin(x);
			break;
		case ForceH:
			x = zoomIntHorizontal(x);
			break;
		case ForceV:
			x = zoomIntVertical(x);
			break;
		case Minimum:
			x = zoomIntMin(x);
			break;
		}
		
		return x;
	}
	
	public static int zoomY(int y, ZoomMode zoomMode){
		switch (zoomMode) {
		case KeepHV:
			y = zoomIntVertical(y);
			break;
		case KeepXYSizeH:
			y = zoomIntVertical(y);
			break;
		case KeepXYSizeV:
			y = zoomIntVertical(y);
			break;
		case KeepXYSizeMin:
			y = zoomIntVertical(y);
			break;
		case KeepXMinOther:
			y = zoomIntMin(y);
			break;
		case KeepYMinOther:
			y = zoomIntVertical(y);
			break;
		case ForceH:
			y = zoomIntHorizontal(y);
			break;
		case ForceV:
			y = zoomIntVertical(y);
			break;
		case Minimum:
			y = zoomIntMin(y);
			break;
		}
		
		return y;
	}
	
	public static int zoomW(int w, ZoomMode zoomMode){
		switch (zoomMode) {
		case KeepHV:
			if(w > 0) w = zoomIntHorizontal(w);
			break;
		case KeepXYSizeH:
			if(w > 0) w = zoomIntHorizontal(w);
			break;
		case KeepXYSizeV:
			if(w > 0) w = zoomIntVertical(w);
			break;
		case KeepXYSizeMin:
			if(w > 0) w = zoomIntMin(w);
			break;
		case KeepXMinOther:
			if(w > 0) w = zoomIntMin(w);
			break;
		case KeepYMinOther:
			if(w > 0) w = zoomIntMin(w);
			break;
		case ForceH:
			if(w > 0) w = zoomIntHorizontal(w);
			break;
		case ForceV:
			if(w > 0) w = zoomIntVertical(w);
			break;
		case Minimum:
			if(w > 0) w = zoomIntMin(w);
			break;
		}
		
		return w;
	}

	public static int zoomH(int h, ZoomMode zoomMode){
		switch (zoomMode) {
		case KeepHV:
			if(h > 0) h = zoomIntVertical(h);
			break;
		case KeepXYSizeH:
			if(h > 0) h = zoomIntHorizontal(h);
			break;
		case KeepXYSizeV:
			if(h > 0) h = zoomIntVertical(h);
			break;
		case KeepXYSizeMin:
			if(h > 0) h = zoomIntMin(h);
			break;
		case KeepXMinOther:
			if(h > 0) h = zoomIntMin(h);
			break;
		case KeepYMinOther:
			if(h > 0) h = zoomIntMin(h);
			break;
		case ForceH:
			if(h > 0) h = zoomIntHorizontal(h);
			break;
		case ForceV:
			if(h > 0) h = zoomIntVertical(h);
			break;
		case Minimum:
			if(h > 0) h = zoomIntMin(h);
			break;
		}
		
		return h;
	}

	public static Rect zoomRect(Rect rect, ZoomMode zoomMode){
		if(rect == null)
			return rect;
		
		int x = rect.left;
		int y = rect.top;
		int w = rect.width();
		int h = rect.height();

		Rect rect2 = new Rect();
		rect2.left = zoomX(x, zoomMode);
		rect2.top = zoomY(y, zoomMode);
		rect2.right = rect.left + zoomW(w, zoomMode);
		rect2.bottom = rect.top + zoomH(h, zoomMode);
		
		return rect2;
	}
	
	public static int zoomFont(int fontSize, ZoomMode zoomMode){
//		switch (zoomMode) {
//		case KeepHV:
//			fontSize = (int)(fontSize*Math.min(zoomFactorX, zoomFactorY));
//			break;
//		case ForceH:
//			fontSize = zoomIntHorizontal(fontSize);
//			break;
//		case ForceV:
//			fontSize = zoomIntVertical(fontSize);
//			break;
//		case ForceMin:
			fontSize = zoomInt(Math.min(zoomFactorX, zoomFactorY), fontSize);
//			break;
//		}
		
		return fontSize;
	}
	

	/**
	 * 缩放设置在RelativeLayout中的View的位置
	 */
 	public static Point layoutWithRelative(View view, int x, int y, ZoomMode zoomMode){
		Point point = new Point(zoomX(x, zoomMode), zoomY(y, zoomMode));
		layoutWithRelative(view, point.x, point.y);
		return point;
		
	}
	
	/**
	 * 缩放设置在RelativeLayout中的View的位置、大小
	 */
	public static Rect layoutWithRelative(View view, int x, int y, int width, int height, ZoomMode zoomMode){
		Rect rect = new Rect();
		rect.left = zoomX(x, zoomMode);
		rect.top = zoomY(y, zoomMode);
		rect.right = rect.left + zoomW(width, zoomMode);
		rect.bottom = rect.top + zoomH(height, zoomMode);
		layoutWithRelative(view, rect);
		return rect;
	}

	/**
	 * 缩放RelativeLayout中的View的位置、大小
	 */
	public static Rect layoutWithRelative(View view, Rect rect, ZoomMode zoomMode){
		rect = zoomRect(rect, zoomMode);
		layoutWithRelative(view, rect);	
		return rect;
	}

	/**
	 * 设置在RelativeLayout中的View的大小
	 */
	public static Size layoutSizeWithRelative(View view, int width, int height, ZoomMode zoomMode){
		Size size = new Size(zoomW(width, zoomMode), zoomH(height, zoomMode));
		layoutSizeWithRelative(view, size.width, size.height);
		return size;
	}

	/**
	 * 缩放RelativeLayout中的View的width
	 */
	public static int layoutWidthWithRelative(View view, int width, ZoomMode zoomMode){
		width = zoomW(width, zoomMode);
		layoutWidthWithRelative(view, width);
		return width;
	}

	/**
	 * 缩放RelativeLayout中的View的height
	 */
	public static int layoutHeightWithRelative(View view, int height, ZoomMode zoomMode){
		height = zoomH(height, zoomMode);
		layoutHeightWithRelative(view, height);
		return height;
	}

	
	/**
	 * 缩放FrameLayout中的View的位置
	 */
	public static Point layoutWithLinear(View view, int x, int y, ZoomMode zoomMode){
		Point point = new Point(zoomX(x, zoomMode), zoomY(y, zoomMode));
		layoutWithLinear(view, point.x, point.y);
		return point;
	}	

	/**
	 * 缩放FrameLayout中的View的位置、大小
	 */
	public static Rect layoutWithLinear(View view, int x, int y, int width, int height, ZoomMode zoomMode){
		Rect rect = new Rect();
		rect.left = zoomX(x, zoomMode);
		rect.top = zoomY(y, zoomMode);
		rect.right = rect.left + zoomW(width, zoomMode);
		rect.bottom = rect.top + zoomH(height, zoomMode);
		layoutWithLinear(view, rect);
		return rect;
	}

	/**
	 * 缩放FrameLayout中的View的位置、大小
	 */
	public static Rect layoutWithLinear(View view, Rect rect, ZoomMode zoomMode){
		rect = zoomRect(rect, zoomMode);
		layoutWithLinear(view, rect);
		return rect;
	}
	
	/**
	 * 缩放FrameLayout中的View的大小
	 */
	public static Size layoutSizeWithLinear(View view, int width, int height, ZoomMode zoomMode){
		Size size = new Size(zoomW(width, zoomMode), zoomH(height, zoomMode));
		layoutSizeWithLinear(view, size.width, size.height);
		return size;
	}
	
	/**
	 * 缩放FrameLayout中的View的width
	 */
	public static int layoutWidthWithLinear(View view, int width, ZoomMode zoomMode){
		width = zoomW(width, zoomMode);
		layoutWidthWithLinear(view, width);
		return width;
	}
	
	/**
	 * 缩放FrameLayout中的View的height
	 */
	public static int layoutHeightWithLinear(View view, int height, ZoomMode zoomMode){
		height = zoomH(height, zoomMode);
		layoutHeightWithLinear(view, height);
		return height;
	}
	
	

	/**
	 * 缩放FrameLayout中的View的位置
	 */
	public static Point layoutWithFrame(View view, int x, int y, ZoomMode zoomMode){
		Point point = new Point(zoomX(x, zoomMode), zoomY(y, zoomMode));
		layoutWithFrame(view, point.x, point.y);
		return point;
	}	

	/**
	 * 缩放FrameLayout中的View的位置、大小
	 */
	public static Rect layoutWithFrame(View view, int x, int y, int width, int height, ZoomMode zoomMode){
		Rect rect = new Rect();
		rect.left = zoomX(x, zoomMode);
		rect.top = zoomY(y, zoomMode);
		rect.right = rect.left + zoomW(width, zoomMode);
		rect.bottom = rect.top + zoomH(height, zoomMode);
		layoutWithFrame(view, rect);
		return rect;
	}

	/**
	 * 缩放FrameLayout中的View的位置、大小
	 */
	public static Rect layoutWithFrame(View view, Rect rect, ZoomMode zoomMode){
		rect = zoomRect(rect, zoomMode);
		layoutWithFrame(view, rect);
		return rect;
	}
	
	/**
	 * 缩放FrameLayout中的View的大小
	 */
	public static Size layoutSizeWithFrame(View view, int width, int height, ZoomMode zoomMode){
		Size size = new Size(zoomW(width, zoomMode), zoomH(height, zoomMode));
		layoutSizeWithFrame(view, size.width, size.height);
		return size;
	}
	
	/**
	 * 缩放FrameLayout中的View的width
	 */
	public static int layoutWidthWithFrame(View view, int width, ZoomMode zoomMode){
		width = zoomW(width, zoomMode);
		layoutWidthWithFrame(view, width);
		return width;
	}
	
	/**
	 * 缩放FrameLayout中的View的height
	 */
	public static int layoutHeightWithFrame(View view, int height, ZoomMode zoomMode){
		height = zoomH(height, zoomMode);
		layoutHeightWithFrame(view, height);
		return height;
	}
	

	
	/************************************************************/
	/************************************************************/
	/*==========================================================*/
	/*                           控件操作                                                                  */
	/*==========================================================*/
	/************************************************************/
	/************************************************************/
	
	/**
	 * 对齐x，相差dy，高宽分别为：width,height
	 */
	public static Rect alignLeftWithFrame(View view, View refView, int dy, int width, int height, ZoomMode zoomMode){
		if(view == null || refView == null)
			return null;
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.left;
		rect.top = refRect.top + zoomY(dy, zoomMode);
		rect.right = rect.left + zoomW(width, zoomMode);
		rect.bottom = rect.top + zoomH(height, zoomMode);
		layoutWithFrame(view, rect);
		
		return rect;
	}
	
	/**
	 * 对齐x,width,height，相差dy
	 */
	public static Rect alignLeftWithFrame(View view, View refView, int dy, ZoomMode zoomMode){
		if(view == null || refView == null)
			return null;
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.left;
		rect.top = refRect.top + zoomY(dy, zoomMode);
		rect.right = rect.left + refRect.width();
		rect.bottom = rect.top + refRect.height();
		layoutWithFrame(view, rect);
		
		return rect;
	}

	/**
	 * 对齐y，相差dx，高宽分别为：width,height
	 */
	public static Rect alignTopWithFrame(View view, View refView, int dx, int width, int height, ZoomMode zoomMode){
		if(view == null || refView == null)
			return null;
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.left + zoomX(dx, zoomMode);
		rect.top = refRect.top;
		rect.right = rect.left + zoomW(width, zoomMode);
		rect.bottom = rect.top + zoomH(height, zoomMode);
		layoutWithFrame(view, rect);
		
		return rect;
	}
	
	/**
	 * 对齐y,width,height，相差dx
	 */
	public static Rect alignTopWithFrame(View view, View refView, int dx, ZoomMode zoomMode){
		if(view == null || refView == null)
			return null;
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.left + zoomX(dx, zoomMode);
		rect.top = refRect.top;
		rect.right = rect.left + refRect.width();
		rect.bottom = rect.top + refRect.height();
		layoutWithFrame(view, rect);
		
		return rect;
	}

	/**
	 * 对齐bottom，相差dx，高宽分别为：width,height
	 */
	public static Rect alignBottomWithFrame(View view, View refView, int dx, int width, int height, ZoomMode zoomMode){
		if(view == null || refView == null)
			return null;
		
		width = zoomW(width, zoomMode);
		height = zoomH(height, zoomMode);
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.left + zoomX(dx, zoomMode);
		rect.top = refRect.bottom - height;
		rect.right = rect.left + width;
		rect.bottom = refRect.bottom;
		layoutWithFrame(view, rect);
		
		return rect;
	}
	
	/**
	 * 对齐bottom,width,height，相差dx
	 */
	public static Rect alignBottomWithFrame(View view, View refView, int dx, ZoomMode zoomMode){
		if(view == null || refView == null)
			return null;
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.left + zoomX(dx, zoomMode);
		rect.top = refRect.bottom - refRect.height();
		rect.right = rect.left + refRect.width();
		rect.bottom = rect.top + refRect.height();
		layoutWithFrame(view, rect);
		
		return rect;
	}

	/**
	 * 对齐x，y，高宽分别为：width,height
	 */
	public static Rect alignCenterWithFrame(View view, View refView, int width, int height, ZoomMode zoomMode){
		
		width = zoomW(width, zoomMode);
		height = zoomH(height, zoomMode);
		
		return alignCenterWithFrame(view, refView, width, height);
	}

	/**
	 * 对齐x，y，高宽分别为：width,height
	 */
	public static Rect alignCenterWithFrame(View view, View refView, int width, int height){
		if(view == null || refView == null)
			return null;		
		
		Rect refRect = layoutWithFrame(refView);
		Rect rect = new Rect();
		rect.left = refRect.centerX()-(width>>1);
		rect.top = refRect.centerY()-(height>>1);
		rect.right = rect.left + width;
		rect.bottom = rect.top + height;
		layoutWithFrame(view, rect);
		
		return rect;
	}
	

	/************************************************************/
	/************************************************************/
	/*==========================================================*/
	/*                           设置控件                                                                  */
	/*==========================================================*/
	/************************************************************/
	/************************************************************/
	
	
	/**
	 * 显示、隐藏
	 * @param view
	 * @param show
	 * 			true:View.VISIBLE；false:View.GONE
	 */
	public static void setVisible(View view, boolean show){
		setVisible(view, show? View.VISIBLE: View.GONE);
	}

	
	public static void setVisible(View view, int visible){
		if(view != null && view.getVisibility() != visible)
			view.setVisibility(visible);
	}

	public static boolean isVisible(View view){
		return view != null && view.getVisibility() == View.VISIBLE;
	}
	
	public static boolean isVisibles(View... views){
		if(views == null || views.length == 0)
			return false;
		
		for(int i=0,count=views.length;i<count;i++){
			if(!isVisible(views[i]))
				return false;
		}
		
		return true;
	}
	
	public static int setTextSize(TextView view, int fontSize){
		if(view != null)
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
		return fontSize;
	}
	
	public static float setTextSize(TextView view, float fontSize){
		if(view != null)
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
		return fontSize;
	}
	
	public static int setTextSize(TextView view, int fontSize, ZoomMode zoomMode){
		fontSize = zoomFont(fontSize, zoomMode);
		if(view != null)
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
		return fontSize;
	}
	
	public static void setTextBold(TextView view, boolean bold){
		if(view != null){
			TextPaint tpaint = view.getPaint();
			tpaint.setFakeBoldText(bold);
		}
	}
	
	public static void setTextPadding(TextView view, int left, int top, int right, int bottom, ZoomMode zoomMode){
		if(view != null)
			view.setPadding(zoomX(left, zoomMode), zoomY(top, zoomMode), zoomX(right, zoomMode), zoomY(bottom, zoomMode));
	}
	
	public static void setTextView(TextView view, int fontSize, ZoomMode zoomMode, boolean bold){
		setTextSize(view, fontSize, zoomMode);
		setTextBold(view, bold);
	}
	
	public static void setTextView(TextView view, int fontSize, ZoomMode fontZoomMode,
                                   int left, int top, int right, int bottom, ZoomMode paddingZoomMode){
		setTextSize(view, fontSize, fontZoomMode);
		setTextPadding(view, left, top, right, bottom, paddingZoomMode);
	}
	
	public static void setTextView(TextView view, int fontSize, ZoomMode fontZoomMode,
                                   int left, int top, int right, int bottom, ZoomMode paddingZoomMode, boolean bold){
		setTextSize(view, fontSize, fontZoomMode);
		setTextPadding(view, left, top, right, bottom, paddingZoomMode);
		setTextBold(view, bold);
	}
	
	public static void setLayoutGravity(View view, int gravity){
		if (view == null) 
			return;
		
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp != null){
			if (lp instanceof FrameLayout.LayoutParams){
				FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams)lp;
				lp2.gravity = gravity;
			}
			else if (lp instanceof LinearLayout.LayoutParams){
				LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams)lp;
				lp2.gravity = gravity;
			}
		}
	}
	
	public static void removeDrawable(View view){
		if(view != null){
			try {
				Drawable drawable = view.getBackground();
				if(drawable instanceof BitmapDrawable){
					BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
					view.setBackgroundResource(0);
					bitmapDrawable.setCallback(null);
					Bitmap bitmap = bitmapDrawable.getBitmap();
				    if(bitmap != null && !bitmap.isRecycled()){
				        bitmap.recycle();
				        bitmap = null;
				    }
				    bitmapDrawable = null;
				}
				else if(drawable instanceof StateListDrawable){
					StateListDrawable stateListDrawable = (StateListDrawable)drawable;
					int[] states = stateListDrawable.getState();
					if(states != null && states.length > 0){
						for(int i=0,count=states.length;i<count;i++){
							if(stateListDrawable.selectDrawable(i)){
								Drawable da = stateListDrawable.getCurrent();
								if(da instanceof BitmapDrawable){
									BitmapDrawable bitmapDrawable = (BitmapDrawable)da;
									Bitmap bitmap = bitmapDrawable.getBitmap();
								    if(bitmap != null && !bitmap.isRecycled()){
								        bitmap.recycle();
								        bitmap = null;
								    }
								    bitmapDrawable = null;
								}
							}
						}
					}
					states = null;
				}
				else if(drawable instanceof AnimationDrawable){
					AnimationDrawable animationDrawable = (AnimationDrawable)drawable;
					if(animationDrawable.isRunning())
						animationDrawable.stop();
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void removeImageViewDrawable(ImageView view){
		if(view != null){
			try {
				Drawable drawable = view.getDrawable();
				if(drawable instanceof BitmapDrawable){
					BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
					view.setImageBitmap(null);
					view.setImageDrawable(null);
					bitmapDrawable.setCallback(null);
					Bitmap bitmap = bitmapDrawable.getBitmap();
				    if(bitmap != null && !bitmap.isRecycled()){
				        bitmap.recycle();
				        bitmap = null;
				    }
				    bitmapDrawable = null;
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	/************************************************************/
	/************************************************************/
	/*==========================================================*/
	/*                           创建控件                                                                  */
	/*==========================================================*/
	/************************************************************/
	/************************************************************/
	
	public static TextView makeTextView(Context context, int textColor, float textSize){
		TextView textView = new TextView(context);
		textView.setTextColor(textColor);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		return textView;
	}
	
	public static TextView makeTextView(Context context, int textColor, float textSize, int gravity){
		TextView textView = makeTextView(context, textColor, textSize);
		textView.setGravity(gravity);
		return textView;
	}
	
	public static TextView makeTextView(Context context, int textColor, float textSize, int gravity, boolean singleLine){
		TextView textView = makeTextView(context, textColor, textSize);
		textView.setGravity(gravity);
		textView.setSingleLine();
		textView.setEllipsize(TruncateAt.END);
		return textView;
	}
	
	public static ImageView makeImageView(Context context, ScaleType scaleType){
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(scaleType);
		return imageView;
	}

	public static ImageView makeImageView(Context context, ScaleType scaleType, int resid){
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(scaleType);
		imageView.setBackgroundResource(resid);
		return imageView;
	}
	
	public static Button makeButton(Context context, int resid){
		Button button = new Button(context);
		button.setPadding(0, 0, 0, 0);
		button.setBackgroundResource(resid);
		return button;
	}
	
	public static Button makeButton(Context context, int resid, int textColor, float textSize){
		Button button = makeButton(context, resid);
		button.setTextColor(textColor);
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		button.setGravity(Gravity.CENTER);
		return button;
	}
	
	public static Button makeButton(Context context, int resid, OnClickListener l){
		Button button = makeButton(context, resid);
		button.setOnClickListener(l);
		return button;
	}
	
	public static Button makeButton(Context context, int resid, int textColor, float textSize, OnClickListener l){
		Button button = makeButton(context, resid, textColor, textSize);
		button.setOnClickListener(l);
		return button;
	}
	
	public static FrameLayout makeFrameLayout(Context context, Object tag, OnClickListener l){
		FrameLayout frameLayout = new FrameLayout(context);
		frameLayout.setTag(tag);
		frameLayout.setOnClickListener(l);
		return frameLayout;
	}

	public static FrameLayout makeFrameLayout(Context context, int resid){
		FrameLayout frameLayout = new FrameLayout(context);
		frameLayout.setBackgroundResource(resid);
		return frameLayout;
	}

	
	
	/************************************************************/
	/************************************************************/
	/*==========================================================*/
	/*                     创建背景、圆角、渐变                                                           */
	/*==========================================================*/
	/************************************************************/
	/************************************************************/

	public static class ShapeDrawableEx extends ShapeDrawable {

		private boolean strokeGradient;
		private int color;
		private int strokeColor;
		private int defColor = 0;
		private boolean setDefColor = false;
		private Shader shader;
		
		public ShapeDrawableEx(Shape s, int color, int strokeColor) {
			super(s);			
			this.color = color;
			this.strokeColor = strokeColor;
			this.strokeGradient = false;
		}
		
		public ShapeDrawableEx(Shape s, int color, float x0, float y0, float x1, float y1, int... strokeColors) {
			super(s);			
			this.color = color;
			this.strokeGradient = true;
			this.shader = new LinearGradient(x0, y0, x1, y1, strokeColors, null, Shader.TileMode.CLAMP);
		}

		@Override
		protected void onDraw(Shape s, Canvas c, Paint p) {
			
			if(!setDefColor){
				setDefColor = true;
				defColor = p.getColor();
			}

			// 边框部分
			if(!strokeGradient){
				p.setColor(strokeColor);
			}
			else if(shader != null){
				p.setColor(defColor);
				p.setShader(shader);
			}
			s.draw(c, p);

			// 内容部分
			if(s instanceof RoundRectShapeEx){
				RoundRectShapeEx ss = (RoundRectShapeEx)s;
				p.setColor(color);
				p.setShader(null);
				c.drawPath(ss.getPathEx(), p);
			}
		}
	}
	
	public static class RoundRectShapeEx extends RoundRectShape {

	    private float[] mInnerRadii;
	    private RectF mInset;
	    private Path mPath;
	    
		public RoundRectShapeEx(boolean isCenterTransparent, float[] outerRadii, RectF inset, float[] innerRadii) {
			super(outerRadii, isCenterTransparent?inset:null, isCenterTransparent?innerRadii:null);
			
			mInset = inset;
			mInnerRadii = new float[innerRadii.length];
			for(int i=0;i<outerRadii.length;i++){
				mInnerRadii[i] = innerRadii[i];
			}
			mPath = new Path();
			
			
		}

	    @Override
	    protected void onResize(float w, float h) {
	        super.onResize(w, h);

	        RectF r = new RectF(rect());
	        r.left += mInset.left;
	        r.right -= mInset.right;
	        r.top += mInset.top;
	        r.bottom -= mInset.bottom;
	        mPath.reset();
	        if (mInnerRadii != null) {
	            mPath.addRoundRect(r, mInnerRadii, Path.Direction.CW);
	        } else {
	            mPath.addRect(r, Path.Direction.CW);
	        }
	    }
	    
	    public Path getPathEx(){
	    	return mPath;
	    }
	}
	
	/**
	 * 圆角纯色背景，没有边框
	 */
	public static ShapeDrawable createRoundDrawable(
			int color, 
			float v00x, float v00y,
			float v10x, float v10y,
			float v11x, float v11y,
			float v01x, float v01y){
		
		float[] outerRadii = new float[] { v00x, v00y, v10x, v10y, v11x, v11y, v01x, v01y };		
		
		RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
		ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
		Paint paint = shapeDrawable.getPaint();
		if(paint != null){
			paint.setColor(color);
		}

		return shapeDrawable;
	}
	
	/**
	 * 圆角纯色背景，边框纯色
	 */
	public static ShapeDrawable createRoundDrawable(
			int color, 
			float v00x, float v00y,
			float v10x, float v10y,
			float v11x, float v11y,
			float v01x, float v01y,
			float cornerOffset,
			float strokeWidth,
			int scrokeColor){
		
		float[] outerRadii = new float[] { v00x, v00y, v10x, v10y, v11x, v11y, v01x, v01y };
		float[] innerRadii = new float[outerRadii.length];
		RectF inset = new RectF(strokeWidth,strokeWidth,strokeWidth,strokeWidth);
		
		cornerOffset = cornerOffset>0?cornerOffset:0;

		for(int i=0;i<outerRadii.length;i++){
			innerRadii[i] = outerRadii[i]>=cornerOffset?outerRadii[i]-cornerOffset:outerRadii[i];
		}
		
		RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
		ShapeDrawable shapeDrawable = strokeWidth > 0?new ShapeDrawableEx(new RoundRectShapeEx(color==0, outerRadii, inset, innerRadii), color, scrokeColor):new ShapeDrawable(roundRectShape);
		
		return shapeDrawable;
	}

	/**
	 * 圆角渐变背景，没有边框
	 */
	public static ShapeDrawable createRoundDrawable(
			float v00x, float v00y,
			float v10x, float v10y,
			float v11x, float v11y,
			float v01x, float v01y,
			int x0, int y0,
			int x1, int y1,
			int... colors){
		float[] outerRadii = new float[] { v00x, v00y, v10x, v10y, v11x, v11y, v01x, v01y };
		
		RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
		ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
		
		Shader shader = new LinearGradient(x0, y0, x1, y1, colors, null, Shader.TileMode.CLAMP);
		
		Paint paint = shapeDrawable.getPaint();
		if(paint != null){
			paint.setShader(shader);
		}
		
		return shapeDrawable;
	}
	
	/**
	 * 圆角纯色背景，边框渐变
	 */
	public static ShapeDrawable createRoundDrawable(
			int color,
			float v00x, float v00y,
			float v10x, float v10y,
			float v11x, float v11y,
			float v01x, float v01y,
			float cornerOffset,
			float strokeWidth,
			int x0, int y0,
			int x1, int y1,
			int... strokeColors){
		
		float[] outerRadii = new float[] { v00x, v00y, v10x, v10y, v11x, v11y, v01x, v01y };
		float[] innerRadii = new float[outerRadii.length];
		RectF inset = new RectF(strokeWidth,strokeWidth,strokeWidth,strokeWidth);
		
		cornerOffset = cornerOffset>0?cornerOffset:0;

		for(int i=0;i<outerRadii.length;i++){
			innerRadii[i] = outerRadii[i]>=cornerOffset?outerRadii[i]-cornerOffset:outerRadii[i];
		}
		
		RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
		ShapeDrawable shapeDrawable = strokeWidth > 0?new ShapeDrawableEx(new RoundRectShapeEx(color==0, outerRadii, inset, innerRadii), color, x0, y0, x1, y1, strokeColors):new ShapeDrawable(roundRectShape);
		
		return shapeDrawable;
	}
	
	/**
	 * 圆角纯色背景，边框渐变
	 */
	public static ShapeDrawable createRoundDrawable2(
			int color,
			float v00x, float v00y,
			float v10x, float v10y,
			float v11x, float v11y,
			float v01x, float v01y,
			float cornerOffset,
			float strokeWidth,
			int x0, int y0,
			int x1, int y1,
			int... strokeColors){
		
		float[] outerRadii = new float[] { v00x, v00y, v10x, v10y, v11x, v11y, v01x, v01y };
		float[] innerRadii = new float[outerRadii.length];
		RectF inset = new RectF(strokeWidth,strokeWidth,strokeWidth,strokeWidth);
		
		cornerOffset = cornerOffset>0?cornerOffset:0;

		for(int i=0;i<outerRadii.length;i++){
			innerRadii[i] = outerRadii[i]>=cornerOffset?outerRadii[i]-cornerOffset:outerRadii[i];
		}
		
		RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
		ShapeDrawable shapeDrawable = strokeWidth > 0?new ShapeDrawableEx(new RoundRectShapeEx(color==0, outerRadii, inset, innerRadii), color, x0, y0, x1, y1, strokeColors):new ShapeDrawable(roundRectShape);
		
		return shapeDrawable;
	}
	
	/**
	 * 圆角纯色背景，没有边框
	 */
	public static ShapeDrawable createRoundDrawable(int color, float round){
		return createRoundDrawable(color, round, round, round, round, round, round, round, round);
	}
	
	/**
	 * 圆角纯色背景，边框纯色
	 */
	public static ShapeDrawable createRoundDrawable(int color, float round, float cornerOffset, float strokeWidth, int scrokeColor){
		return createRoundDrawable(color, round, round, round, round, round, round, round, round, cornerOffset, strokeWidth, scrokeColor);
	}
	
	/**
	 * 圆角渐变背景，没有边框
	 */
	public static ShapeDrawable createRoundDrawable(float round, int x0, int y0, int x1, int y1, int... colors){
		return createRoundDrawable(round, round, round, round, round, round, round, round, x0, y0, x1, y1, colors);
	}
	
	/**
	 * 圆角纯色背景，边框渐变
	 */
	public static ShapeDrawable createRoundDrawable(int color, float round, float cornerOffset, float strokeWidth, int x0, int y0, int x1, int y1, int... strokeColors){
		return createRoundDrawable(color, round, round, round, round, round, round, round, round, cornerOffset, strokeWidth, x0, y0, x1, y1, strokeColors);
	}
	
	public static StateListDrawable createSelectorDrawable(Drawable normal, Drawable pressed, Drawable focused, Drawable unable){
		try {
			StateListDrawable drawable = new StateListDrawable();

			drawable.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
			drawable.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
			drawable.addState(new int[] { android.R.attr.state_enabled }, normal);
			drawable.addState(new int[] { android.R.attr.state_focused }, focused);
			drawable.addState(new int[] { android.R.attr.state_window_focused }, unable);
			drawable.addState(new int[] {}, normal);

			return drawable;
		} catch (Exception ex) {
			return null;
		}
	}


	

	
	  /** 对TextView设置不同状态时其文字颜色。 */  
    public static ColorStateList createColorStateList(int pressed, int focused, int normal) {
        int[] colors = new int[] { pressed, focused, normal};  
        int[][] states = new int[3][];  
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };  
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };  
        states[2] = new int[] {};  
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;  
    } 
}
