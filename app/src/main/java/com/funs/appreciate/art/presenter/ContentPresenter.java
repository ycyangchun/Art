package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.model.api.ApiService;
import com.funs.appreciate.art.model.entitys.CommonEntity;
import com.google.gson.Gson;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yc on 2017/6/30.
 *  内容的详情
 */

public class ContentPresenter implements ContentContract.Presenter {
    private ContentContract.View view;
    private ApiService apiService;
    CompositeSubscription compositeSubscription;
    @Inject
    public ContentPresenter(ContentContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadLayout(String m, String id) {
        try {
            Subscription subscription = apiService.getContentDetail(m ,id , BaseActivity.map)
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
                                view.loadLayoutFailed(new Throwable(commonEntity.getMsg()));
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            view.loadLayoutFailed(throwable);
                        }
                    });
            compositeSubscription.add(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unSubscribed() {
        compositeSubscription.clear();
    }
}
