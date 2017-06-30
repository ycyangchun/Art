package com.funs.appreciate.art.di.components;


import com.funs.appreciate.art.di.modules.ContentModule;
import com.funs.appreciate.art.di.scopes.UserScope;
import com.funs.appreciate.art.view.DetailActivity;
import com.funs.appreciate.art.view.SpecialActivity;

import dagger.Component;

/**
 * 专题
 */
@UserScope
@Component(modules = ContentModule.class,dependencies = NetComponent.class)
public interface DetailComponent {
    void inject(DetailActivity mainActivity);
}
