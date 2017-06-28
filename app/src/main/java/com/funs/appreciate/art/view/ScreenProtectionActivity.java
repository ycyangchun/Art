package com.funs.appreciate.art.view;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.funs.appreciate.art.R;
import com.funs.appreciate.art.utils.UIHelper;

/**
 * Created by yc on 2017/6/23.
 *  屏保
 */

public class ScreenProtectionActivity extends FragmentActivity{

    PowerManager.WakeLock wakeLock;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.initialize(this, false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        // 屏蔽系统的屏保
        KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager
                .newKeyguardLock("KeyguardLock");
        lock.disableKeyguard();
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
        setContentView(R.layout.activity_splash);
        System.out.println("=============== 屏保 ===============>");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((wakeLock != null) && (wakeLock.isHeld() == false)) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((wakeLock != null) && (wakeLock.isHeld() == false)) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
    }

}
