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
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.di.components.DaggerScreenProtectionComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.model.util.NoNetworkException;
import com.funs.appreciate.art.presenter.SplashContract;
import com.funs.appreciate.art.presenter.SplashPresenter;
import com.funs.appreciate.art.utils.ArtResourceUtils;
import com.funs.appreciate.art.utils.UIHelper;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/23.
 *  屏保
 */

public class ScreenProtectionActivity extends FragmentActivity implements SplashContract.View{

    private PowerManager.WakeLock wakeLock;
    private ImageView splash_iv;
    private Context instance;
    private static int picIndex;
    private int duration;
    private final static int webPic = 0;

    @Inject
    SplashPresenter presenter;
    List<SplashPictureEntity.ConfigBean.DataJsonBean>  dataJsonBeen;
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
        instance = this.getApplicationContext();
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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ((wakeLock != null) && (wakeLock.isHeld() == false)) {
            wakeLock.acquire();
        }
        MobclickAgent.onPause(this);
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
    public void loadSplashSuccess(String splash) {
        ArtResourceUtils.setScreenRes(splash);
        loadData(splash);
    }

    @Override
    public void loadSplashFailed(Throwable throwable) {
        if(throwable instanceof NoNetworkException){
            String splash = ArtResourceUtils.getSplashRes();
            if(splash != null)
                loadData(splash);
        }
    }
    private void loadData(String splash) {
        SplashPictureEntity se = new Gson().fromJson(splash , SplashPictureEntity.class);
        SplashPictureEntity.ConfigBean  cb = se.getConfig();
        dataJsonBeen = cb.getImageArray();
        duration =  5 ;//默认
        try {
            duration = Integer.parseInt(cb.getScreenSaverTime());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if(dataJsonBeen != null) {
                picIndex  = 0;
                showPic();
                if(dataJsonBeen.size()> 1){
                    picIndex = getCurrentShow();
                    handler.sendEmptyMessageDelayed(webPic , duration * 1000);
                }
            }
        }
    }

    private void showPic() {
        String url = getPicUrl();
//        System.out.println("======== url =========>"+url+" picIndex "+picIndex);
        Glide.with(instance)
                .load(url)
                .override(1980, 1080)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.2f)
                .error(R.drawable.bg_splash)
                .decoder(new ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper>() {
                    @Override
                    public Resource<GifBitmapWrapper> decode(ImageVideoWrapper source, int width, int height) throws IOException {

                        return null;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }
                })
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(splash_iv)
        ;
    }

    private String getPicUrl() {
        return dataJsonBeen.get(picIndex).getImgUrl();
    }


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case webPic:
                    if(dataJsonBeen != null) {
                        showPic();
                        picIndex = getCurrentShow();
                        handler.sendEmptyMessageDelayed(webPic, duration * 1000);
                    }
                    break;
            }

        }
    };

    private int getCurrentShow(){
        int temp = ++picIndex;
        if(temp > dataJsonBeen.size() -1){
            picIndex = 0;
        } else{
            picIndex = temp;
        }
        return picIndex;
    }
}
