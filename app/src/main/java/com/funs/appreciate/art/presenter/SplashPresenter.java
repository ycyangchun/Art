package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.model.api.ApiService;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yc on 2017/6/14.
 * 开屏页 presenter
 */

public class SplashPresenter implements  SplashContract.Presenter{
    private SplashContract.View view;
    private ApiService apiService;

    @Inject
    public SplashPresenter(SplashContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }


    @Override
    public void loadSplash() {
        view.loadSplashSuccess("");
    }
}
