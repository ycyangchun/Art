package com.funs.appreciate.art.presenter;

import com.funs.appreciate.art.base.BasePresenter;
import com.funs.appreciate.art.base.BaseView;

/**
 * Created by yc on 2017/6/14.
 * 开屏页 Contract
 */

public interface SplashContract {
        interface Presenter extends BasePresenter {
            void loadSplash(String type ,boolean tagScreen);
        }

        interface View extends BaseView{
            void loadSplashSuccess(String splash);
            void loadSplashFailed(Throwable throwable);
        }
}
