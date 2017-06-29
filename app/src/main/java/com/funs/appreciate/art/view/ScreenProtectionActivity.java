package com.funs.appreciate.art.view;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.di.components.DaggerScreenProtectionComponent;
import com.funs.appreciate.art.di.components.DaggerSplashComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.presenter.SplashContract;
import com.funs.appreciate.art.presenter.SplashPresenter;
import com.funs.appreciate.art.utils.UIHelper;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/23.
 *  屏保
 */

public class ScreenProtectionActivity extends FragmentActivity implements SplashContract.View{

    PowerManager.WakeLock wakeLock;
    ImageView splash_iv;
    String urls[];
    ScreenProtectionActivity instance;
    static int picIndex;
    int duration;

    @Inject
    SplashPresenter presenter;

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
        splash_iv = (ImageView) findViewById(R.id.splash_iv);
        findViewById(R.id.count_down_tv).setVisibility(View.GONE);
        instance = this;
        System.out.println("=============== 屏保 ===============>");
        DaggerScreenProtectionComponent.builder()
                .netComponent(ArtApp.get(this).getNetComponent())
                .splashModule(new SplashModule(this))
                .build().inject(this);

        presenter.loadSplash("1");
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

    ///////////////////////////////////////////////////////
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void loadSplashSuccess(SplashPictureEntity splash) {
        SplashPictureEntity.ConfigBean  cb = splash.getConfig();
        String picUrl = cb.getDataJson();
        if(picUrl.contains(";")){
            urls = picUrl.split(";");
        }
        duration = 5;//默认
        try {
            duration = Integer.parseInt(cb.getDuration());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if(urls != null) {
                picIndex  = 0;
                Glide.with(instance).load(urls[picIndex]).error(R.drawable.bg_splash).into(splash_iv);
                if(urls.length > 1){
                    picIndex = getCurrentShow();
                    handler.sendEmptyMessageDelayed(0 , duration * 1000);
                }
            }
        }
    }

    @Override
    public void loadSplashFailed(Throwable throwable) {

    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
//                    System.out.println("======== picIndex =========>"+picIndex);
                    Glide.with(instance).load(urls[picIndex]).error(R.drawable.bg_splash).into(splash_iv);
                    picIndex = getCurrentShow();
                    handler.sendEmptyMessageDelayed(0 , duration * 1000);
                    break;
            }

        }
    };

    private int getCurrentShow(){
        int temp = ++picIndex;
        if(temp > urls.length -1){
            picIndex = 0;
        } else{
            picIndex = temp;
        }
        return picIndex;
    }
}
