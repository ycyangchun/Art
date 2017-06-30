package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BasePresenter;
import com.funs.appreciate.art.base.BaseView;

/**
 * Created by yc on 2017/6/15.
 *  主页 contract
 */

public interface MainContract {
    int TYPELAYOUT = 0;
    int TYPECONTENT = 1;
    interface Presenter extends BasePresenter {
        void loadLayout(String m , String columnId);
        void loadContent(String m, String id , String type);
    }

    interface View extends BaseView {
        void loadLayoutSuccess(String lay);
        void loadContentSuccess(String content , String type);
        void loadLayoutFailed(Throwable throwable , int type);
    }
}
