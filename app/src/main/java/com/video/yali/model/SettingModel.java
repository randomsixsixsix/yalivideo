package com.video.yali.model;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.param.PersonalAgeParas;
import com.video.yali.param.PersonalHeadParas;
import com.video.yali.param.PersonalMateParas;
import com.video.yali.param.PersonalNickParas;
import com.video.yali.param.PersonalSexParas;
import com.video.yali.param.PersonalWorkParas;
import com.video.yali.param.SmsCodeParas;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

import java.io.File;

public class SettingModel extends RequestModel {


    public void checkVersion(BaseActivity mContext, RequestCallback callback){
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>post(Neturl.VERSIONCHECK)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "版本更新:");
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
     * 修改密码--获取短信验证码
     */
    public void sendPswdModefySmsCodeData(BaseActivity mContext, String areaCode, String inputPhone, String inputPswd, String inputInvite, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        SmsCodeParas mLoginParas = new SmsCodeParas(areaCode + "", inputPhone, inputPswd, inputInvite);
        Gson gs = new Gson();
        String paraString = gs.toJson(mLoginParas);//把数据转为JSON格式的字符串
        LogUtils.e("获取验证码参数--" + areaCode + "==" + inputPhone + "==" + inputPswd + "\n" + paraString);
        OkGo.<String>post(Neturl.PSWDMODIFY_SMSCODE)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "修改密码--获取验证码数据:");
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
     * 修改密码--设置新密码
     */
    public void pswdModifyData(BaseActivity mContext, String areaCode, String inputPhone, String inputPswd, String smsCode, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        SmsCodeParas mLoginParas = new SmsCodeParas(areaCode + "", inputPhone, inputPswd, smsCode);
        Gson gs = new Gson();
        String paraString = gs.toJson(mLoginParas);//把数据转为JSON格式的字符串
        LogUtils.e("修改密码--设置新密码--" + areaCode + "==" + inputPhone + "==" + inputPswd + "\n" + paraString);
        OkGo.<String>post(Neturl.PSWDMODIFY_NEWPSWD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "修改密码--设置新密码--数据:");
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
     * 个人信息--设置昵称
     */
    public void personalNickData(BaseActivity mContext, String nickname, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PersonalNickParas mPersonalNickParas = new PersonalNickParas(nickname);
        Gson gs = new Gson();
        String paraString = gs.toJson(mPersonalNickParas);//把数据转为JSON格式的字符串
        LogUtils.e("个人信息--设置昵称-参数-" + nickname);
        OkGo.<String>post(Neturl.PERSONAL_NICK)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                mContext.dismissProgress();
                parseJson(mContext, response, callback, "个人信息--设置昵称数据:");
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
     * 个人信息--修改性别
     */
    public void personalSexData(BaseActivity mContext, int sex, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PersonalSexParas mPersonalSexParas = new PersonalSexParas(sex+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mPersonalSexParas);//把数据转为JSON格式的字符串
        LogUtils.e("个人信息--修改性别-参数-" + sex);
        OkGo.<String>post(Neturl.PERSONAL_SEX)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "个人信息--修改性别--数据:");
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
     * 个人信息--修改年龄
     */
    public void personalAgeData(BaseActivity mContext, int age, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PersonalAgeParas mPersonalAgeParas = new PersonalAgeParas(age+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mPersonalAgeParas);//把数据转为JSON格式的字符串
        LogUtils.e("个人信息--修改年龄-参数-" + age);
        OkGo.<String>post(Neturl.PERSONAL_AGE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "个人信息--修改年龄--数据:");
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
     * 个人信息--修改职业
     */
    public void personalWorkData(BaseActivity mContext, String work, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PersonalWorkParas mPersonalWorkParas = new PersonalWorkParas(work+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mPersonalWorkParas);//把数据转为JSON格式的字符串
        LogUtils.e("个人信息--修改职业-参数-" + work);
        OkGo.<String>post(Neturl.PERSONAL_WORK)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "个人信息--修改职业--数据:");
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
     * 个人信息--伴侣修改
     */
    public void personalMateData(BaseActivity mContext, int mate, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PersonalMateParas mPersonalMateParas = new PersonalMateParas(mate+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mPersonalMateParas);//把数据转为JSON格式的字符串
        LogUtils.e("个人信息--伴侣修改-参数-" + mate);
        OkGo.<String>post(Neturl.PERSONAL_MATE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "个人信息--伴侣修改--数据:");
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
     * 个人信息--头像修改
     */
    public void personalHeadLogoData(BaseActivity mContext, String headImage, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        PersonalHeadParas mPersonalHeadParas = new PersonalHeadParas(headImage+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mPersonalHeadParas);//把数据转为JSON格式的字符串
        LogUtils.e("个人信息--头像修改-参数-" + headImage);
        OkGo.<String>post(Neturl.PERSONAL_HEADLOGO)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
//                .params("avatar",imageFile)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "个人信息--头像修改--数据:");
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
