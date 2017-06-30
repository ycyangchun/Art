package com.funs.appreciate.art.di.components;


import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.di.scopes.UserScope;
import com.funs.appreciate.art.view.SpecialActivity;

import dagger.Component;

/**
 * 专题
 */
@UserScope
@Component(modules = MainModule.class,dependencies = NetComponent.class)
public interface SpecialComponent {
    void inject(SpecialActivity mainActivity);
}
