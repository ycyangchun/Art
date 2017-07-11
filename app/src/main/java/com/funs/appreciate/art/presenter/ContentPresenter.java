package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BaseActivity;
import com.funs.appreciate.art.model.api.ApiService;
import com.funs.appreciate.art.model.entitys.CommonEntity;
import com.funs.appreciate.art.utils.AppUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yc on 2017/6/30.
 *  内容的详情
 */

public class ContentPresenter implements ContentContract.Presenter {
    private ContentContract.View view;
    private ApiService apiService;

    @Inject
    public ContentPresenter(ContentContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void loadLayout(String m, String id) {
        try {
            apiService.getContentDetail(m ,id , BaseActivity.map)
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
