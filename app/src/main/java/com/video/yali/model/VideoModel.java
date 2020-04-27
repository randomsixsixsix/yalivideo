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
import com.video.yali.param.CommentPutParas;
import com.video.yali.param.IdParas;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

public class VideoModel extends RequestModel{

    /**
     * 视频详情
     */
    public void getVideoInfoData(BaseActivity mContext, int videoId,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        LogUtils.e("视频详情参数--" + videoId);
        OkGo.<String>get(Neturl.VIDEO_DETAILS)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("id", videoId+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "视频详情--数据:");
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
     * 视频详情--猜你喜欢
     */
    public void getVideoAboutData(BaseActivity mContext, int videoId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("视频详情--猜你喜欢--参数--" + videoId);
        OkGo.<String>get(Neturl.VIDEO_ABOUT)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("movie_id", videoId+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "视频详情-猜你喜欢-数据:");
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
     * 评论列表
     */
    public void commentListData(BaseActivity mContext, int videoId, int commentType, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("评论列表--参数--" + videoId);
        OkGo.<String>get(Neturl.VIDEO_COMMENT_LIST)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("movie_id", videoId+"")
                .params("type", commentType+"")
                .params("page", "1")
                .params("page_size", Integer.MAX_VALUE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "评论列表-数据:");
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
     * 评论提交
     */
    public void commentPutData(BaseActivity mContext, int videoId,String content,int firstCommentId, int targetCommentId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        CommentPutParas mCommentPutParas =null;
        if (firstCommentId==0){
            mCommentPutParas = new CommentPutParas(videoId+"",content,"","");
        }else{
            mCommentPutParas = new CommentPutParas(videoId+"",content,firstCommentId+"",targetCommentId+"");
        }
        LogUtils.e("评论提交--参数--" + videoId+"=="+firstCommentId+"=="+targetCommentId);
        Gson gs = new Gson();
        String paraString = gs.toJson(mCommentPutParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.VIDEO_COMMENT_PUT)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "评论提交-数据:");
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
     * 视频点赞
     */
    public void videoZanData(BaseActivity mContext, int videoId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("视频点赞--参数--" + videoId);
        IdParas mVideoZanParas = new IdParas(videoId+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mVideoZanParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.VIDEO_ZAN)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "视频点赞-数据:");
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
     * 视频添加播放历史
     */
    public void videoAddHistoryData(BaseActivity mContext, int videoId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("视频添加播放历史--参数--" + videoId);
        IdParas mVideoZanParas = new IdParas(videoId+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mVideoZanParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.VIDEO_ADD_HISTORY)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "视频添加播放历史-数据:");
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
     * 评论点赞
     */
    public void commentZanData(BaseActivity mContext, int commentId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("评论点赞--参数--" + commentId);
        IdParas mVideoZanParas = new IdParas(commentId+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mVideoZanParas);//把数据转为JSON格式的字符串
        OkGo.<String>post(Neturl.VIDEO_COMMENT_ZAN)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "评论点赞-数据:");
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
     * 评论详情
     */
    public void commentDetailsData(BaseActivity mContext, int commentId,int videoId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("评论详情--参数--" + videoId);
        OkGo.<String>get(Neturl.VIDEO_COMMENT_DETAILS)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("id", commentId+"")
                .params("movie_id", videoId+"")
                .params("page", "1")
                .params("page_size", Integer.MAX_VALUE+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "评论详情-数据:");
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
     * 通知服务器该视频已经点击下载
     */
    public void videoDownNotifyData(BaseActivity mContext, int videoId, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        LogUtils.e("通知服务器该视频已经点击下载--参数--" + videoId);
        OkGo.<String>get(Neturl.VIDEO_DOWN_NOTIFY)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("id", videoId+"")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "通知服务器该视频已经点击下载-数据:");
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
     * 获取视频广告
     */
    public void videoAdData(BaseActivity mContext,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>get(Neturl.VIDEO_AD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "获取视频广告-数据:");
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
