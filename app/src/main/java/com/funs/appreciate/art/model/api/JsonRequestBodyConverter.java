package com.funs.appreciate.art.model.api;

import com.funs.appreciate.art.ArtConfig;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by YangChun .
 * on 2017/3/11.
 * 自定义请求RequestBody
 */

public class JsonRequestBodyConverter<T> implements Converter<T , RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public RequestBody convert(T value) throws IOException {
        String data = gson.toJson(value);
        ArtConfig.Log("request中传递的json数据：", data);
//        try {
//            data = Base64Util.encode(Des3Util.getInstance(ApiService.SECRET_KEY, ApiService.SECRET_VALUE).encode(data));
//        } catch (Exception e) {
//            throw new IOException("Encryption failed");
//        }
//        ArtConfig.Log("转化后的数据：" ,data);
        return RequestBody.create(MEDIA_TYPE, data);
    }

}
