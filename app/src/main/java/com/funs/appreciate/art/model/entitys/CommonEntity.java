package com.funs.appreciate.art.model.entitys;

/**
 * Created by YangChun .
 * on 2017/3/13.
 */

public class CommonEntity<T> {

    private String status;
    private String msg;
    private T data;


    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
