package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BasePresenter;
import com.funs.appreciate.art.base.BaseView;

/**
 * Created by yc on 2017/6/15.
 *  内容 contract
 */

public interface ContentContract {
    interface Presenter extends BasePresenter {
        void loadLayout(String m, String id);
    }

    interface View extends BaseView {
        void loadLayoutSuccess(String lay);
        void loadLayoutFailed(Throwable throwable);
    }
}
