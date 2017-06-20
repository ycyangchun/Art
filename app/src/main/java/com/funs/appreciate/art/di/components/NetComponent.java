package com.funs.appreciate.art.di.components;

import com.funs.appreciate.art.di.modules.NetModule;
import com.funs.appreciate.art.model.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 *
 */
@Component(modules = NetModule.class)
@Singleton
public interface NetComponent {

    ApiService getApiService();

    OkHttpClient getOkHttp();

    Retrofit getRetrofit();
}
