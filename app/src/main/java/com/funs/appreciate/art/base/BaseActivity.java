package com.funs.appreciate.art.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.funs.appreciate.art.service.ScreenProtectionService;
import com.funs.appreciate.art.utils.UIHelper;

/**
 * Created by yc on 2017/6/14.
 * Base Activity
 */

public class BaseActivity extends FragmentActivity {

    public Intent sps_intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.initialize(this, false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        //屏保
        sps_intent = new Intent(this, ScreenProtectionService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sps_intent.putExtra("screen_status","start");
        startService(sps_intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == KeyEvent.ACTION_DOWN) {
            sps_intent.putExtra("screen_status","start");
            startService(sps_intent);
        }
        return super.dispatchTouchEvent(ev);
    }

    // dispatchKeyEvent ↓↓↓↓↓↓↓
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if(keyCode != KeyEvent.KEYCODE_BACK) {
                sps_intent.putExtra("screen_status", "start");
                startService(sps_intent);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        sps_intent.putExtra("screen_status","remove");
//        startService(sps_intent);
    }
}
