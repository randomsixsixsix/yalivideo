package com.video.yali.param;

public class PswdForgetCheckCodeParas {


    /**
     * code :
     * mobile :
     * password :
     * verify_code :
     */

    private String code;        //区号
    private String mobile;
    private String password;
    private String verify_code;     //验证码

    public PswdForgetCheckCodeParas(String code, String mobile, String password, String verify_code) {
        this.code = code;
        this.mobile = mobile;
        this.password = password;
        this.verify_code = verify_code;

    }
}
