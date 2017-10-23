package com.liketry.ministore.model.request;

/**
 * author Simon
 * create 2017/10/23
 * 登录
 */
public class RegisterBody {
    private String phone;//手机
    private String code;//验证码
    private int type;//0注册

    public RegisterBody() {
    }

    public RegisterBody(String phone, String code, int type) {
        this.phone = phone;
        this.code = code;
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
