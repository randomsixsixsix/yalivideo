package com.video.yali.model;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.param.LoginParas;
import com.video.yali.param.PswdForgetCheckCodeParas;
import com.video.yali.param.PswdForgetResetParas;
import com.video.yali.param.RegisterParas;
import com.video.yali.param.SmsCodeParas;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

public class LoginModel extends RequestModel {


    /**
     *  登陆网络请求
     */
    public void loginMobileData(BaseActivity mContext, int areaCode, String inputPhone, String inputPswd, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        LoginParas mLoginParas = new LoginParas(areaCode + "", inputPhone, inputPswd);
        Gson gs = new Gson();
        String paraString = gs.toJson(mLoginParas);//把数据转为JSON格式的字符串
//        LogUtils.e("登陆参数--" + areaCode + "==" + inputPhone + "==" + inputPswd+"\n"+paraString);
        OkGo.<String>post(Neturl.LOGIN_USER)
                .headers(YlUtils.getHttpHeaders(mContext))
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "登录数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mContext.dismissProgress();
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
//                        callback.onError(response.code());
                    }
                });

    }


    /**
     *  注册获取短信验证码
     */
    public void sendRegisterSmsCodeData(BaseActivity mContext, int areaCode, String inputPhone, String inputPswd, String inputInvite, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        SmsCodeParas mLoginParas = new SmsCodeParas(areaCode + "", inputPhone, inputPswd, inputInvite);
        Gson gs = new Gson();
        String paraString = gs.toJson(mLoginParas);//把数据转为JSON格式的字符串
        LogUtils.e("获取验证码参数--" + areaCode + "==" + inputPhone + "==" + inputPswd + "\n" + paraString);
        OkGo.<String>post(Neturl.REGISTER_SMSCODE)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "注册获取验证码数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mContext.dismissProgress();
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
                        callback.onError(response.code());
                    }
                });
    }

    /**
     *  注册
     */
    public void registerNet(BaseActivity mContext,  String smsCode,int areaCode, String inputPhone, String inputPswd, String inputInvite, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        RegisterParas mLoginParas = new RegisterParas(areaCode+"", inputPhone, inputPswd, smsCode,inputInvite);
        Gson gs = new Gson();
        String paraString = gs.toJson(mLoginParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.LOGIN_REGISTER)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "注册数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mContext.dismissProgress();
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
                        callback.onError(response.code());
                    }
                });
    }

    /**
     *  忘记密码--获取短信验证码
     */
    public void sendPswdSmsCodeData(BaseActivity mContext, int areaCode, String inputPhone, String inputPswd, String inputInvite, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        SmsCodeParas mLoginParas = new SmsCodeParas(areaCode + "", inputPhone, inputPswd, inputInvite);
        Gson gs = new Gson();
        String paraString = gs.toJson(mLoginParas);//把数据转为JSON格式的字符串
        LogUtils.e("获取验证码参数--" + areaCode + "==" + inputPhone + "==" + inputPswd + "\n" + paraString);
        OkGo.<String>post(Neturl.PSWDFORGET_SMSCODE)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "忘记密码--获取验证码数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mContext.dismissProgress();
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
                        callback.onError(response.code());
                    }
                });
    }

    /**
     *  忘记密码--校验验证码
     */
    public void pswdForgetCheckCodeNet(BaseActivity mContext,  String smsCode,int areaCode, String inputPhone, String inputPswd,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PswdForgetCheckCodeParas mPswdNewParas = new PswdForgetCheckCodeParas(areaCode+"", inputPhone, inputPswd, smsCode);
        Gson gs = new Gson();
        String paraString = gs.toJson(mPswdNewParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.PSWDFORGET_CHECKCODE)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "忘记密码--校验验证码:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mContext.dismissProgress();
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
                        callback.onError(response.code());
                    }
                });
    }
    /**
     *  忘记密码--重置密码
     */
    public void pswdForgetResetNet(BaseActivity mContext,  String smsCode,int areaCode, String inputPhone, String inputPswd,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PswdForgetResetParas mPswdForgetResetParas = new PswdForgetResetParas(areaCode+"", inputPhone, inputPswd, smsCode);
        Gson gs = new Gson();
        String paraString = gs.toJson(mPswdForgetResetParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.PSWDFORGET_RESET)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "忘记密码--校验验证码:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mContext.dismissProgress();
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
                        callback.onError(response.code());
                    }
                });
    }
}
