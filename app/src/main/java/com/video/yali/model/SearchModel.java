package com.video.yali.model;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.video.yali.Neturl;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

public class SearchModel extends RequestModel {

    public void keywordSearch(Activity mContext, String keyword, int page,RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.SEARCHQUERY)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("keyword", keyword)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        parseJson((Activity) mContext, response, callback, "搜索结果:");
                    }
                });

    }

    public void videoSearch(Activity mContext, int area, int categoryid, int channelid, int duration, int publishtime, int sort, int page, RequestCallback callback) {

        (Neturl.CHANNELMOVIES)
                .params("area", area)
                .params("category_id", categoryid)
                .params("duration", duration)
                .params("publish_time", publishtime)
                .params("sort", sort)
                .params("page", page)
                .params("channel_id", channelid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "视频搜索:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("视频搜索:" + response.code());
                    }
                });
    }

    public void getArea(Activity mContext, int channelid, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.i("channelid:" + channelid);
        OkGo.<String>get(Neturl.CHANNELAREA)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("channel_id", channelid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "视频地区:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("视频地区:" + response.code());
                    }
                });


    }


    public void getCategory(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.CHANNELCATEGORY)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "视频分类:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("视频分类:" + response.code());
                    }
                });


    }


    public void getduration(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        (Neturl.CHANNELDURATION)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "时长:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("时长:" + response.code());
                    }
                });


    }


    public void getPublishtime(Activity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.MOVEPUBLISHTIME)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "发布时间:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("发布时间:" + response.code());
                    }
                });


    }


    public void getMovesort(Activity mContext, int channelid, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(Neturl.MOVIESORT)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("channel_id", channelid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "排序方式:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("排序方式:" + response.code());
                    }
                });


    }


}
