package com.funs.appreciate.art.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.SparseArray;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

public final class ImageHelper {
	
	/**
	 * inSampleSize：折中计算
	 */
	public static final int Stretch_Auto = 0;
	
	/**
	 * inSampleSize：图片不会超出显示范围
	 */
	public static final int Stretch_Uniform = 1;
	
	/**
	 * inSampleSize：图片可能超出显示范围
	 */
	public static final int Stretch_UniformToFill = 2;
	
	
	private static ImageHelper instance;

    private SparseArray<SoftReference<Bitmap>> mImageCacheMap = new SparseArray<SoftReference<Bitmap>>();
    
	private ImageHelper(){
		
	}
	
	public synchronized static ImageHelper getInstance(){
		if(instance == null)
			instance = new ImageHelper();
		return instance;
	}
	
	public static void clear(){
		try {
			if(instance != null){
				for(int i=0,count=instance.mImageCacheMap.size();i<count;i++){
					SoftReference<Bitmap> soft = instance.mImageCacheMap.valueAt(i);
					if(soft != null){
						Bitmap bm = soft.get();
						if(bm != null && !bm.isRecycled()){
							bm.recycle();
							bm = null;
						}
					}
				}
				instance.mImageCacheMap.clear();
			}
		} catch (Exception ex) {
			
		}
	}
	
	
	public synchronized Bitmap getBitmapWithResource(Resources res, int id){
		return getBitmapWithResource(res, id, 1);
	}
	
	public synchronized Bitmap getBitmapWithResource(Resources res, int id, int inSampleSize){
		SoftReference<Bitmap> softReference = mImageCacheMap.get(id);
		if (softReference != null && softReference.get() != null) {
		    return softReference.get();
		}

		Bitmap bitmap = decodeResource(res, id, inSampleSize);
		if(bitmap != null){
			SoftReference<Bitmap> d = new SoftReference<Bitmap>(bitmap);
			mImageCacheMap.put(id, d);
		}
		
		return bitmap;
	}
	
	public synchronized Bitmap getBitmapWithResourceNinePath(Resources res, int id){
		SoftReference<Bitmap> softReference = mImageCacheMap.get(id);
		if (softReference != null && softReference.get() != null) {
		    return softReference.get();
		}

		Bitmap bitmap = decodeResourceNinePatch(res, id);
		if(bitmap != null){
			SoftReference<Bitmap> d = new SoftReference<Bitmap>(bitmap);
			mImageCacheMap.put(id, d);
		}
		
		return bitmap;
	}
	
	
	public synchronized static StateListDrawable makeSelector(Resources res, int id,
                                                              boolean normalImage, int normalResid,
                                                              boolean pressedImage, int pressedResid,
                                                              boolean focusedImage, int focusedResid,
                                                              boolean unableImage, int unableResid){
		

		try {
			StateListDrawable drawable = new StateListDrawable();
			
			Drawable normal = normalResid <= 0 ? null
					: (normalImage ? new BitmapDrawable(res, getInstance().getBitmapWithResource(res, normalResid)) : res.getDrawable(normalResid));

			Drawable pressed = pressedResid <= 0 ? null
					: (normalImage ? new BitmapDrawable(res, getInstance().getBitmapWithResource(res, pressedResid)) : res.getDrawable(pressedResid));

			Drawable focused = focusedResid <= 0 ? null
					: (normalImage ? new BitmapDrawable(res, getInstance().getBitmapWithResource(res, focusedResid)) : res.getDrawable(focusedResid));

			Drawable unable = unableResid <= 0 ? null
					: (normalImage ? new BitmapDrawable(res, getInstance().getBitmapWithResource(res, unableResid)) : res.getDrawable(unableResid));

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
	
	public synchronized static StateListDrawable makeSelector(Resources res, int id,
                                                              boolean normalImage, int normalResid,
                                                              boolean pressedImage, int pressedResid,
                                                              boolean focusedImage, int focusedResid){
		return makeSelector(res, id, normalImage, normalResid, pressedImage, pressedResid, focusedImage, focusedResid, false, 0);
	}

	public synchronized static StateListDrawable makeSelector(Resources res, Bitmap normalImage, Bitmap pressedImage, Bitmap focusedImage, Bitmap unableImage){
		try {
			StateListDrawable drawable = new StateListDrawable();
			
			Drawable normal = normalImage == null ? null : new BitmapDrawable(res, normalImage);

			Drawable pressed = pressedImage == null ? null : new BitmapDrawable(res, pressedImage);

			Drawable focused = focusedImage == null ? null : new BitmapDrawable(res, focusedImage);

			Drawable unable = unableImage == null ? null : new BitmapDrawable(res, unableImage);

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
	
	public synchronized static StateListDrawable makeSelector(Resources res, Drawable normal, Drawable pressed, Drawable focused, Drawable unable){
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

	public synchronized static StateListDrawable makeSelector2(Resources res, Drawable normal, Drawable pressed, Drawable focused, Drawable unable){
		try {
			StateListDrawable drawable = new StateListDrawable();

			drawable.addState(new int[] { android.R.attr.state_pressed }, pressed);
			drawable.addState(new int[] { android.R.attr.state_focused }, focused);
			drawable.addState(new int[] { android.R.attr.state_enabled }, normal);
			drawable.addState(new int[] { android.R.attr.state_focused }, focused);
			drawable.addState(new int[] { android.R.attr.state_window_focused }, unable);
			drawable.addState(new int[] {}, normal);

			return drawable;
		} catch (Exception ex) {
			return null;
		}
	}
	public synchronized static StateListDrawable makeSelectorNinePatch(Resources res, int id,
                                                                       boolean normalImage, int normalResid,
                                                                       boolean pressedImage, int pressedResid,
                                                                       boolean focusedImage, int focusedResid){
		

		try {
			StateListDrawable drawable = new StateListDrawable();
			Bitmap bm;
			
			Drawable normal = null;
			if(normalResid > 0){
				if(normalImage){
					bm = getInstance().getBitmapWithResourceNinePath(res, normalResid);
					if(bm != null)
						normal = new NinePatchDrawable(res, bm, bm.getNinePatchChunk(), new Rect(), null);
				}
				else{
					normal = res.getDrawable(normalResid);
				}
			}
			
			Drawable pressed = null;
			if(pressedResid > 0){
				if(pressedImage){
					bm = getInstance().getBitmapWithResourceNinePath(res, pressedResid);
					if(bm != null)
						pressed = new NinePatchDrawable(res, bm, bm.getNinePatchChunk(), new Rect(), null);
				}
				else{
					pressed = res.getDrawable(pressedResid);
				}
			}
			
			Drawable focused = null;
			if(focusedResid > 0){
				if(focusedImage){
					bm = getInstance().getBitmapWithResourceNinePath(res, focusedResid);
					if(bm != null)
						focused = new NinePatchDrawable(res, bm, bm.getNinePatchChunk(), new Rect(), null);
				}
				else{
					focused = res.getDrawable(focusedResid);
				}
			}
			
			Drawable unable = null;
			
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
	
	public synchronized static BitmapDrawable makeBitmapDrawable(Resources res, int resid){
		if(resid <= 0) return null;
		return new BitmapDrawable(res, getInstance().getBitmapWithResource(res, resid));
	}
	
	
	public static Bitmap decodeResource(Resources res, int id){
		return BitmapFactory.decodeResource(res, id);
	}
	
	public static Bitmap decodeResource(Resources res, int id, Options opts){
		try{
			return BitmapFactory.decodeResource(res, id, opts);
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public static Bitmap decodeResource(Resources res, int id, int inSampleSize){
		try{
			Options opts = new Options();
			opts.inPreferredConfig = Config.RGB_565;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			opts.inSampleSize = inSampleSize;
			opts.inJustDecodeBounds = false;
			return BitmapFactory.decodeResource(res, id, opts);
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public static Bitmap decodeResourceNinePatch(Resources res, int id){
		Bitmap bm = BitmapFactory.decodeResource(res, id);
		if(bm != null){
			if(NinePatch.isNinePatchChunk(bm.getNinePatchChunk()))
				return bm;

			if(!bm.isRecycled()){
				bm.recycle();
				bm = null;
			}
		}
		return null;
	}

    public static Bitmap toRoundCorner(Bitmap bitmap, float rx, float ry) {

    	if(bitmap == null || bitmap.isRecycled())
    		return null;

    	try{
	        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	        Canvas canvas = new Canvas(output);
	        final Paint paint = new Paint();
	        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	        final RectF rectF = new RectF(rect);
	        paint.setAntiAlias(true);
	        canvas.drawRoundRect(rectF, rx, ry, paint);
	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);
	        return output;
    	} catch(Exception ex){
    		ex.printStackTrace();
    		return null;
    	}
    }

    public static Bitmap downloadImage(String url, int inSampleSize){
    	URL m;
		InputStream is = null;
		try {
			m = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) m.openConnection();
	        conn.setConnectTimeout(10 * 1000);
	        conn.setRequestMethod("GET");
	        if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
	        	return null;
	        is = conn.getInputStream();

			Options opts = new Options();
			opts.inSampleSize = inSampleSize<=0?1:inSampleSize;			
			opts.inJustDecodeBounds = false;
			
			Bitmap bm = BitmapFactory.decodeStream(is, null, opts);
  
			return bm;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			
            if (is != null) {
            	try {
                    is.close();
				} catch (Exception ex) {

				}
            }
		}
    }

    
}
