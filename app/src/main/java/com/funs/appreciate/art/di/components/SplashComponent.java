package com.funs.appreciate.art.di.components;


import com.funs.appreciate.art.di.modules.SplashModule;
import com.funs.appreciate.art.di.scopes.UserScope;
import com.funs.appreciate.art.view.SplashActivity;

import dagger.Component;

/**
 *
 */
@UserScope
@Component(modules = SplashModule.class,dependencies = NetComponent.class)
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
