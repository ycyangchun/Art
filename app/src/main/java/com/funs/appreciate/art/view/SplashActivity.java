package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerSplashComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.presenter.SplashContract;
import com.funs.appreciate.art.presenter.SplashPresenter;

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

        } else {
            Glide.with(this).load(picUrl).error(R.drawable.bg_splash).into(splash_iv);
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
    public void loadSplashFailed(Throwable throwable) {

    }
}
