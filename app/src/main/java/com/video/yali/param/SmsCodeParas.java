package com.video.yali.param;

public class SmsCodeParas {

    public String code;
    public String mobile;
    public String password;
    public String verify_code;

    public SmsCodeParas(String code, String mobile, String password, String verify_code) {
        this.code = code;
        this.mobile = mobile;
        this.password = password;
        this.verify_code = verify_code;
    }
}
