package com.funs.appreciate.art.utils;

import android.content.Context;
import android.os.Handler;

import static com.funs.appreciate.art.base.ArtConstants.START_STATUS;


/**
 * Created by yc on 2017/6/28.
 * 屏保定时器
 */

public class UtilTimer {
    //定时器

    private static UtilTimer utilTimer;
    private static long times ; //定时时间
    private static boolean startTimer = true;// 开启定时
    private Context mContext;
    private static Handler mHandler;


    // 构造函数私有化
    private UtilTimer(Context context, Handler handle) {
        this.mContext = context.getApplicationContext();//单例的生命周期和应用的一样长，这样就防止了内存泄漏。
        this.mHandler = handle;


    }

    // 提供一个全局的静态方法
    public static UtilTimer getUtilTimer(Context context , Handler handle) {
        if (utilTimer == null) {
            synchronized (UtilTimer.class) {
                if (utilTimer == null) {
                    utilTimer = new UtilTimer(context , handle);
                }
            }
        }
        return utilTimer;
    }


    public static void timerStart(){
        times = ArtResourceUtils.getScreenSaverTime(60)  * 1000;
        if(mHandler != null) {
            mHandler.removeMessages(START_STATUS);
            if(startTimer) {
                mHandler.sendEmptyMessageDelayed(START_STATUS , times);
            }
        }
    }
    public static void timerRemove(){
        if(mHandler != null) {
            mHandler.removeMessages(START_STATUS);
        }
    }

    public static void setTimes(long times) {
        UtilTimer.times = times;
    }

    public static void setStartTimer(boolean startTimer) {
        UtilTimer.startTimer = startTimer;
    }
}
