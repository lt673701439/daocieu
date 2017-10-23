/**
 * @author Simon
 * @time 2017/6/6
 * 只封装了服务器返回的基本字段
 */
package com.liketry.ministore.model.common;

import java.io.Serializable;

@SuppressWarnings("unused")
public class BasicModel<T> implements Serializable {
    private int code;
    private String msg;
    private int error;
    protected T result = null;//真正的数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
