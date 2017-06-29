package com.funs.appreciate.art.di.components;


import com.funs.appreciate.art.di.modules.MainModule;
import com.funs.appreciate.art.di.scopes.UserScope;
import com.funs.appreciate.art.view.MainActivity;
import com.funs.appreciate.art.view.RecommendFragment;

import dagger.Component;

/**
 *
 */
@UserScope
@Component(modules = MainModule.class,dependencies = NetComponent.class)
public interface RecommendFragmentComponent {
    void inject(RecommendFragment recommendFragment);
}
