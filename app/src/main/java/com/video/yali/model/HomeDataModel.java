package com.video.yali.model;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.video.yali.GlobConstant;
import com.video.yali.Neturl;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeDataModel extends RequestModel {

    public void getHomeMovies(Activity mContext, int columnid, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.i("page:" + page);
        OkGo.<String>get(Neturl.HOMEMOVIES)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("column_id", columnid)
                .params("page", page)
                .params("page_size", 6)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        parseJson(mContext, response, callback, "专题视频:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void getHomeData(Activity mContext, RequestCallback callback) {

        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.HOMEDATA)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        parseJson2(mContext, response, callback, "首页数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }


    public void getHomePagerData(Activity mContext, int channelid, RequestCallback callback) {

        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.HOMEPAGEDATA)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("channel_id", channelid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        parseJson2(mContext, response, callback, "首页子叶数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
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
    protected void parseJson2(Activity mContext, Response<String> response, RequestCallback callback, String s) {

        LogUtils.i(s + response.body());

        try {
            JSONObject jsonObject = new JSONObject(response.body());
            if (jsonObject.has("code")) {
                String code = jsonObject.getString("code");
                switch (code) {
                    case GlobConstant.REQUESTSUCCESS:
                        callback.onSuccess(response.body());
                        break;
                    case GlobConstant.REQUESTERROR://token过期

                        break;
                    default:
                        if (jsonObject.has("msg")) {
                            ToastUtils.showLong(jsonObject.getString("msg"));
                        }
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
