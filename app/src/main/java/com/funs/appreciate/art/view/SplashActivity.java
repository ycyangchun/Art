package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerSplashComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.model.util.NoNetworkException;
import com.funs.appreciate.art.presenter.SplashContract;
import com.funs.appreciate.art.presenter.SplashPresenter;
import com.funs.appreciate.art.utils.ArtResourceUtils;
import com.funs.appreciate.art.view.widget.DialogErr;
import com.google.gson.Gson;

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
    CountDownTimer countDownTimer;
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
        presenter.loadSplash("1");
        ////////////////
        Runtime rt=Runtime.getRuntime();
        long maxMemory=rt.maxMemory();
        Log.i(" === art ==> maxMemory ",Long.toString(maxMemory/(1024*1024)));
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
        if(se != null ){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            return;
        }

        int duration = 5;//默认
        SplashPictureEntity.ConfigBean  cb = se.getConfig();
        String picUrl = cb.getDataJson();

        try {
            duration = Integer.parseInt(cb.getDuration());
        } catch (Exception e) {
//            e.printStackTrace();
            duration = 0;
        } finally {
            if(duration > 0) {
                count_down_tv.setVisibility(View.VISIBLE);
            }
            countDownTimer = new CountDownTimer( (duration + 1) * 1000 ,1000){
                @Override
                public void onTick(long millisUntilFinished) {
                    count_down_tv.setText( "时间剩余"+millisUntilFinished / 1000 +"\"");
                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            };
            Glide.with(this).load(picUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .thumbnail(0.2f)
                    .error(R.drawable.bg_err)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            countDownTimer.start();
                            return false;
                        }
                    })
                    .into(splash_iv);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
