package com.green.kinsomy.interview.model;

/**
 * Created by kinsomy on 2017/10/19.
 */

public class ResponseResult<T> extends BaseResult {
    private int status_code;
    private String msg;
    private T data;

    public ResponseResult(){

    }

    public ResponseResult(int status_code, String msg, T data) {
        this.status_code = status_code;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return status_code == 0;
    }

    public boolean noResult(){
        return status_code == 1;
    }
}
