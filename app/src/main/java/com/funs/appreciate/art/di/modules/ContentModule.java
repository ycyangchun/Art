package com.funs.appreciate.art.di.modules;


import com.funs.appreciate.art.presenter.ContentContract;
import com.funs.appreciate.art.presenter.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ContentModule {
    private ContentContract.View view;

    public ContentModule(ContentContract.View view) {
        this.view = view;
    }
    @Provides
    public ContentContract.View provideView(){
        return view;
    }
}
