package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.model.api.ApiService;

import javax.inject.Inject;

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
    public void loadLayout() {
        view.loadLayoutSuccess("");
    }
}
