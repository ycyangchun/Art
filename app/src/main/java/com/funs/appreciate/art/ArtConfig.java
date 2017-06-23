package com.funs.appreciate.art;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.funs.appreciate.art.base.BaseActivity;

/**
 *
 * 配置类
 */
public class ArtConfig {
    /**
     * true 为debug状态，打印日志;false为上线发布状态
     */
    public static boolean IS_DEBUG = true;
    public static void Log(String tag, String msg){
        if(IS_DEBUG)
            Log.d(tag , msg);
    }

    private static BaseActivity mainActivity;

    /**
     * 测试环境
     */
    public static final int TEST_TYPE = 0;
    /**
     * 生产环境
     */
    public static final int PRODUCT_TYPE = 1;
    /**
     * 通过修改该常量改变测试|生产环境
     */
    public static int RELEASE_TYPE = TEST_TYPE;

    /**
     * 测试环境根路径
     */
    public static final String SERVER_ROOT_TEST = "http://211.99.241.10:7082/ad-art-service/";
    /**
     * 生产版本根路径
     */
    public static final String SERVER_ROOT_PRODUCT = "";

    public static String getServerUrl() {
        StringBuilder serverUrl = new StringBuilder("");
        if (RELEASE_TYPE == PRODUCT_TYPE) {// 正式环境
            serverUrl.append(SERVER_ROOT_PRODUCT);
        } else if (RELEASE_TYPE == TEST_TYPE) {// 测试环境
            serverUrl.append(SERVER_ROOT_TEST);
        }
        return serverUrl.toString();

    }

    public static BaseActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(BaseActivity mainActivity) {
        ArtConfig.mainActivity = mainActivity;
    }
}
