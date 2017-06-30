package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.model.api.ApiService;

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
        apiService.getColumnList(m,columnId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        view.loadLayoutSuccess(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.loadLayoutFailed(throwable , TYPELAYOUT);
                    }
                });
    }

    @Override
    public void loadContent(String m, String id , final String type) {
        apiService.getContentDetail(m ,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        view.loadContentSuccess(s , type);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.loadLayoutFailed(throwable , TYPECONTENT);
                    }
                });
    }
}
