package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BasePresenter;
import com.funs.appreciate.art.base.BaseView;

/**
 * Created by yc on 2017/6/15.
 *  主页 contract
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        void loadLayout();
    }

    interface View extends BaseView {
        void loadLayoutSuccess(String lay);
        void loadLayoutFailed(Throwable throwable);
    }
}
