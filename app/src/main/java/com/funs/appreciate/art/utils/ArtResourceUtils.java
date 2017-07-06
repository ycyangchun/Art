package com.funs.appreciate.art.utils;

/**
 * Created by yc on 2017/6/30.
 *  资源保存
 */

public class ArtResourceUtils {
//    private static String splashRes;
//    private static String layoutRes;
//    private static String screenRes;
//    private static int screenSaverTime;

    public static String getSplashRes() {
        return SharedPreferencesUtils.getString("splashRes");
    }

    public static void setSplashRes(String splashRes) {
        SharedPreferencesUtils.put("splashRes",splashRes);
    }

    public static String getLayoutRes(String id) {
        return  SharedPreferencesUtils.getString("layoutRes_"+id);
    }

    public static void setLayoutRes(String layoutRes,String id) {
        SharedPreferencesUtils.put("layoutRes_"+id,layoutRes);
    }

    public static String getScreenRes() {
        return SharedPreferencesUtils.getString("screenRes");
    }

    public static void setScreenRes(String screenRes) {
        SharedPreferencesUtils.put("screenRes",screenRes);
    }

    public static int getScreenSaverTime(int defaultTime) {
        return SharedPreferencesUtils.getInt("screenSaverTime",defaultTime);
    }

    public static void setScreenSaverTime(int screenSaverTime) {
        SharedPreferencesUtils.put("screenSaverTime",screenSaverTime);
    }
}
