package com.funs.appreciate.art.model.api;


import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * api
 */
public interface ApiService {

    /**
     *
     */
    @POST("column")
    Observable<String> getColumnList(@Query("m") String getColumnList);
}
