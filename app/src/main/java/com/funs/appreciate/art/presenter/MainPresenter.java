package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.model.api.ApiService;
import com.funs.appreciate.art.model.entitys.CommonEntity;
import com.google.gson.Gson;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.funs.appreciate.art.presenter.MainContract.TYPECONTENT;
import static com.funs.appreciate.art.presenter.MainContract.TYPELAYOUT;

/**
 * Created by yc on 2017/6/15.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private ApiService apiService;

    @Inject
    public MainPresenter(MainContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void loadLayout(String m , String columnId) {
        try {
            apiService.getColumnList(m,columnId, BaseActivity.map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            CommonEntity commonEntity = new Gson().fromJson(s , CommonEntity.class);
                            String status = commonEntity.getStatus();
                            if("1".equals(status)) {
                                view.loadLayoutSuccess(s);
                            } else {
                                view.loadLayoutFailed(new Throwable(commonEntity.getMsg()) , TYPELAYOUT);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.loadLayoutFailed(throwable , TYPELAYOUT);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadContent(String m, String id , final String type) {
        try {
            apiService.getContentDetail(m ,id, BaseActivity.map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            CommonEntity commonEntity = new Gson().fromJson(s , CommonEntity.class);
                            String status = commonEntity.getStatus();
                            if("1".equals(status)) {
                                view.loadContentSuccess(s , type);
                            } else {
                                view.loadLayoutFailed(new Throwable(commonEntity.getMsg()) , TYPECONTENT);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.loadLayoutFailed(throwable , TYPECONTENT);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
