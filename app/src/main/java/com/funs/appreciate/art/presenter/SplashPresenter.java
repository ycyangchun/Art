package com.funs.appreciate.art.presenter;

import android.text.TextUtils;

import com.funs.appreciate.art.model.api.ApiService;
import com.funs.appreciate.art.model.entitys.CommonEntity;
import com.funs.appreciate.art.model.entitys.SplashPictureEntity;
import com.funs.appreciate.art.utils.ArtResourceUtils;
import com.google.gson.Gson;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.funs.appreciate.art.presenter.MainContract.TYPELAYOUT;

/**
 * Created by yc on 2017/6/14.
 * 开屏页  or 屏保 presenter
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
    public void loadSplash(final String type) {
        try {
            apiService.getAppConfig("getAppConfig",type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                                CommonEntity commonEntity = new Gson().fromJson(s, CommonEntity.class);
                                String status = commonEntity.getStatus();
                                if ("1".equals(status)) {
                                    if ("0".equals(type))//启动页
                                        view.loadSplashSuccess(s);
                                    else { // 屏保
                                        ArtResourceUtils.setScreenSaverRes(s);
                                        SplashPictureEntity se = new Gson().fromJson(s , SplashPictureEntity.class);
                                        SplashPictureEntity.ConfigBean  cb = se.getConfig();
                                        String screenTime = cb.getScreenSaverTime();
                                        if(!TextUtils.isEmpty(screenTime)){
                                            ArtResourceUtils.setScreenSaverTime(Integer.parseInt(screenTime));
                                        }
                                    }
                                } else {
                                    if ("0".equals(type))//启动页
                                    view.loadSplashFailed(new Throwable(commonEntity.getMsg()));
                                }
                        }

                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.loadSplashFailed(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
