package com.zzq.beauty.util;

public enum  RestCode {
    _200(200,"成功"),
    _201(201,"账号已存在！"),
    _300(300,"修改成功！"),
    _301(301,"参数错误，请重新选择！"),
    _302(302,"库存不足！"),
    _303(303,"账号或密码错误！"),
    _304(304,"账号已经被冻结，请联系管理员!");
    private int code;
    private String message;

    RestCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
