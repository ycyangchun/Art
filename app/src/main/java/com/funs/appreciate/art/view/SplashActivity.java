package com.funs.appreciate.art.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.funs.appreciate.art.ArtApp;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loadSplashFailed(Throwable throwable) {

    }
}
