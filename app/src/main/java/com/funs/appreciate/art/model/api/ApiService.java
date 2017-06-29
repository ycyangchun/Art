package com.funs.appreciate.art.model.api;


import com.funs.appreciate.art.model.entitys.SplashPictureEntity;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * api
 */
public interface ApiService {

    /**
     * 获取应用启动信息 type = 0
     * 获取待机屏保信息 type = 1
     */
    @POST("appconfig")
    Observable<SplashPictureEntity> getAppConfig(@Query("m") String getAppConfig , @Query("type") String type);

    /**
     * 3、获取栏目列表和第一个栏目的布局、内容
     */
    @POST("column")
    Observable<String> getColumnList(@Query("m") String getColumnList);
}
