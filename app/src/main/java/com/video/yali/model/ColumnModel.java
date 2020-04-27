package com.video.yali.model;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.video.yali.Neturl;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

public class ColumnModel extends RequestModel {

    public void starList(Activity mContext, String sort, String cup, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.i(sort + "," + cup + "," + page);

        OkGo.<String>get(Neturl.STARS)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("sort", sort)
                .params("cup", cup)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "明星列表:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("明星列表失败:" + response.code());
                    }
                });

    }

    public void starDetails(Context mContext, int starid, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.STAR)
                .params("id", starid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "明星详情:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("明星详情失败:" + response.code());
                    }
                });


    }


    public void starVideos(Context mContext, int starid, int sort, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        GetRequest<String> stringGetRequest = OkGo.<String>get(Neturl.STARMOVIES);
        stringGetRequest.params("star_id", starid);
        stringGetRequest.params("sort", sort);
        stringGetRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                parseJson((Activity) mContext, response, callback, "明星视频:");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                callback.onError(response.code());
                LogUtils.i("明星视频失败:" + response.code());
            }
        });


    }

    public void columnStar(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.COLUMNSTAR)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson(mContext, response, callback, "专栏明星:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    public void columnHot(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.COLUMNHOT)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson(mContext, response, callback, "热门专栏:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    public void columnAd(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.COLUMNAD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson(mContext, response, callback, "专栏轮播图:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    public void columnYear(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.COLUMNYEAR)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson(mContext, response, callback, "专栏年度推荐:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    public void getZhuantiDetail(Activity mContext, int id, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.COLUMNDETAILS)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson(mContext, response, callback, "专栏详情:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void getColumnList(Activity mContext, int columnid, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.i("当前页面:" + page);
        (Neturl.HOMEMOVIES)
                .params("column_id", columnid)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        parseJson(mContext, response, callback, "专题视频列表:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

}
