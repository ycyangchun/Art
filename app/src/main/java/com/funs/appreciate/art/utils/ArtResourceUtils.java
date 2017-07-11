package com.funs.appreciate.art.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.funs.appreciate.art.model.entitys.LayoutModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yc on 2017/6/30.
 *  资源保存
 */

public class ArtResourceUtils {
//    private static String splashRes;
//    private static String layoutRes;
//    private static int screenSaverTime;
//    private static String screenSaverRes;
//     private static String layoutColumnIds; // layout column id

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


    public static int getScreenSaverTime(int defaultTime) {
        return SharedPreferencesUtils.getInt("screenSaverTime",defaultTime);
    }

    public static void setScreenSaverTime(int screenSaverTime) {
        SharedPreferencesUtils.put("screenSaverTime",screenSaverTime);
    }

    public static String getScreenSaverRes() {
        return SharedPreferencesUtils.getString("screenSaverRes");
    }

    public static void setScreenSaverRes(String screenSaverRes) {
        SharedPreferencesUtils.put("screenSaverRes",screenSaverRes);
    }


    public static String getLayoutColumnIds() {
        return SharedPreferencesUtils.getString("layoutColumnIds");
    }

    public static void setLayoutColumnIds(String layoutColumnIds) {
        SharedPreferencesUtils.put("layoutColumnIds",layoutColumnIds);
    }

    // 清除屏保
    public static void removeScreenSaverRes() {
        SharedPreferencesUtils.remove("screenSaverRes");
    }

    //清除layout
    public static void removeLayouts() {
        String lay = SharedPreferencesUtils.getString("layoutColumnIds");
        if(!TextUtils.isEmpty(lay)){
            Type type = new TypeToken<List<String>>(){}.getType();
            List<String> list = new Gson().fromJson(lay ,type);
            for(String id : list){
                SharedPreferencesUtils.remove("layoutRes_"+id);
            }
        }
    }

}
