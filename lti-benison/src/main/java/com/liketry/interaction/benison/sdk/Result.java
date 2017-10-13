package com.liketry.interaction.benison.sdk;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.util.PropertiesUtils;

public class Result<T> {
    private String code;
    private String msg;
    private int error;
    private T data;
    private String token;

    public Result() {
    }

    public Result(int error, String msg) {
        this(SystemConstants.RESULT_FALSE, msg, null, error);
    }

    public Result(String code) {
        this(code, PropertiesUtils.getInstance().getValue(code), null);
    }

    public Result(String code, String msg, T data) {
        this(code, msg, data, 0);
    }
    
    public Result(String code, String msg, T data, String token) {
        this(code, msg, data, 0, token);
    }

    public Result(String code, String msg, T data, int error) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.error = error;
    }
    
    public Result(String code, String msg, T data, int error, String token) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.error = error;
        this.token = token;
    }

    public Result(String code, T data) {
        this(code, PropertiesUtils.getInstance().getValue(code), data);
    }
    
    public Result(String code, T data, String token,int flag) {
        this(code, PropertiesUtils.getInstance().getValue(code), data, token);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
}
