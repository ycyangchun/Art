package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerSplashComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.model.util.NoNetworkException;
import com.funs.appreciate.art.presenter.SplashContract;
import com.funs.appreciate.art.presenter.SplashPresenter;
import com.funs.appreciate.art.utils.AppUtil;
import com.funs.appreciate.art.utils.ArtResourceUtils;
import com.funs.appreciate.art.view.widget.DialogErr;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

/**
 * Created by yc on 2017/6/14.
 *  开屏页
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter presenter;

    TextView count_down_tv;
    ImageView splash_iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        count_down_tv = (TextView) findViewById(R.id.count_down_tv);
        splash_iv = (ImageView) findViewById(R.id.splash_iv);
        DaggerSplashComponent.builder()
                .netComponent(ArtApp.get(this).getNetComponent())
                .splashModule(new SplashModule(this))
                .build().inject(this);

        presenter.loadSplash("0");
        ////////////////
        Runtime rt=Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
        Log.i("=====>maxMemory:",Long.toString(maxMemory/(1024*1024)));
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
        ArtResourceUtils.setSplashRes(splash);
        loadData(splash);
    }

    @Override
    public void loadSplashFailed(Throwable throwable) {
        if(throwable instanceof NoNetworkException){
            String splash = ArtResourceUtils.getSplashRes();
            if(splash != null)
                loadData(splash);
        }
        try {
            new DialogErr(this,throwable.getMessage()).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeScreenService();//
    }
    private void loadData(String splash) {
        SplashPictureEntity se = new Gson().fromJson(splash , SplashPictureEntity.class);
        SplashPictureEntity.ConfigBean  cb = se.getConfig();
        String picUrl = cb.getDataJson();
        String screenTime = cb.getScreenSaverTime();
        if(!TextUtils.isEmpty(screenTime)){
            ArtResourceUtils.setScreenSaverTime(Integer.parseInt(screenTime));
        }

        if(picUrl.contains(";")){

        } else {
            Glide.with(this).load(picUrl).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.bg_splash).into(splash_iv);
        }

        int duration = 5;//默认
        try {
            duration = Integer.parseInt(cb.getDuration());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {

            CountDownTimer countDownTimer = new CountDownTimer( duration * 1000 ,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    count_down_tv.setText( "时间剩余"+millisUntilFinished / 1000 +"\"");
                }

                @Override
                public void onFinish() {
                    count_down_tv.setText( "时间剩余0\"");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            };
            countDownTimer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
