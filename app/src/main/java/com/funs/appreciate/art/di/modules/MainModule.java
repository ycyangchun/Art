package com.funs.appreciate.art.di.modules;


import com.funs.appreciate.art.presenter.MainContract;
import com.funs.appreciate.art.presenter.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class MainModule {
    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }
    @Provides
    public MainContract.View provideView(){
        return view;
    }
}
