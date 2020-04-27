package com.video.yali.param;

public class RegisterParas {


    /**
     * code :
     * mobile :
     * password :
     * verify_code :
     */

    private String code;        //区号
    private String phone;
    private String password;
    private String verify_code;     //验证码
    private String invite_code;     //邀请码

    public RegisterParas(String code, String phone, String password, String verify_code, String invite_code) {
        this.code = code;
        this.phone = phone;
        this.password = password;
        this.verify_code = verify_code;
        this.invite_code = invite_code;
    }
}
