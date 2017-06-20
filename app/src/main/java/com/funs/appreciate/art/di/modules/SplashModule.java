package com.funs.appreciate.art.di.modules;


import com.funs.appreciate.art.presenter.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class SplashModule {
    private SplashContract.View view;

    public SplashModule(SplashContract.View view) {
        this.view = view;
    }
    @Provides
    public SplashContract.View provideView(){
        return view;
    }
}
