package com.funs.appreciate.art.model.entitys;

/**
 * Created by YangChun .
 * on 2017/3/13.
 */

public class CommonEntity<T> {

    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
