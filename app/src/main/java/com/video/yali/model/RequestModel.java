package com.video.yali.model;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.video.yali.GlobConstant;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.utils.RequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestModel {


    /**
     * 密码登录
     *
     * @param mContext
     * @param mobile
     * @param pwd
     * @param callback
     */
    public void pwdLogin(final Activity mContext, String mobile, String pwd, final RequestCallback callback) {

        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showLong("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showLong("请输入密码");
            return;
        }

        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>post(Neturl.PWDlOGIN)
                .params("phone", mobile)
                .params("password", pwd)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson(mContext, response, callback, "密码登录:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        callback.onError(response.code());
                    }
                });
    }


    /**
     * 解析json
     *
     * @param mContext
     * @param response
     * @param callback
     * @param s
     */
    protected void parseJson(Activity mContext, Response<String> response, RequestCallback callback, String s) {

        LogUtils.i(s + response.body());

        try {
            JSONObject jsonObject = new JSONObject(response.body());
            if (jsonObject.has("code")) {
                String code = jsonObject.getString("code");
                switch (code) {
                    case GlobConstant.REQUESTSUCCESS:
                        if (jsonObject.has("data") && "200".equals(code)) {
                            String data = jsonObject.getString("data");
                            callback.onSuccess(data);
                        } else {
                            callback.onError(response.code());
                            if (jsonObject.has("message")) {
                                String mgs = jsonObject.getString("message");
                                ToastUtils.showShort(mgs);
                            }
                        }
                        break;
                    case GlobConstant.REQUESTERROR://token过期
                        if (jsonObject.has("message")) {
                            String mgs = jsonObject.getString("message");
                            ToastUtils.showShort(mgs);
                        }
                        break;
                    default:
                        if (jsonObject.has("message")) {
                            callback.onError(response.code());
                            String mgs = jsonObject.getString("message");
                            ToastUtils.showShort(mgs);
                        }
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 网络是否连接
     *
     * @param mContext
     */
    protected boolean isIntentConnect(Context mContext) {

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLong(mContext.getString(R.string.neterror));
            return false;
        }

        return true;
    }


}
