package com.video.yali.model;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

public class MainModel extends RequestModel{

    /**
     * 启动页广告
     */
    public void getSplashData(BaseActivity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>get(Neturl.SPLASH_AD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "启动页广告-数据:");
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
     * 获取主页面公告
     */
    public void getMainNoticeData(BaseActivity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>get(Neturl.MAIN_NOTICE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "获取主页面公告-数据:");
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
     * 用户信息
     */
    public void getUserInfoData(BaseActivity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>get(Neturl.MY_USERINFO)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "用户信息--数据:");
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
