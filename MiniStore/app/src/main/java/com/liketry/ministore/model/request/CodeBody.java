package com.liketry.ministore.model.request;


import java.io.Serializable;

public class CodeBody implements Serializable {
    private String mobile;
    private int type;

    public CodeBody() {

    }

    public CodeBody(String mobile, int type) {
        this.mobile = mobile;
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
