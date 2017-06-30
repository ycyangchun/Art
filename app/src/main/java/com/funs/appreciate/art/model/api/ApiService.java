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
     * 1，获取应用启动信息 type = 0
     *,2，获取待机屏保信息 type = 1
     */
    @POST("appconfig")
    Observable<String> getAppConfig(@Query("m") String getAppConfig , @Query("type") String type);

    /**
     * 3、获取栏目列表和第一个栏目的布局、内容  m=getColumnList
     * 4、获取某一个栏目的布局、内容            m=getLayoutAndContent&columnid=**
     */
    @POST("column")
    Observable<String> getColumnList(@Query("m") String getColumnList , @Query("columnid") String columnId);

     /**
     * 5、获取某个内容的详情  m=getContentDetail&id=**
      *6、获取某个专题的详情  m=getSubject&id=**
     */
    @POST("content")
    Observable<String> getContentDetail(@Query("m") String getContentDetail , @Query("id") String id);


}
