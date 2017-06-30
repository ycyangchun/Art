package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.model.api.ApiService;

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
            apiService.getContentDetail(m ,id)
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

                        }
                    });
    }
}
