package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.funs.appreciate.art.ArtApp;
import com.funs.appreciate.art.R;
import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.di.components.DaggerSplashComponent;
import com.funs.appreciate.art.di.modules.SplashModule;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        count_down_tv = (TextView) findViewById(R.id.count_down_tv);
        DaggerSplashComponent.builder()
                .netComponent(ArtApp.get(this).getNetComponent())
                .splashModule(new SplashModule(this))
                .build().inject(this);

        presenter.loadSplash();
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
        CountDownTimer countDownTimer = new CountDownTimer(5 * 1000 ,1000){
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
        countDownTimer.start();

    }

    @Override
    public void loadSplashFailed(Throwable throwable) {

    }
}
