package com.video.yali.model;

import android.os.Build;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.param.IdsParas;
import com.video.yali.param.OpinionPutParas;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

import java.io.File;

public class MyModel extends RequestModel {

    /**
     * 我的喜欢
     */
    public void getMyLikesData(BaseActivity mContext, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>get(Neturl.MY_LIKE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("page", page + "")
                .params("page_size", ConstantUtils.pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "我的喜欢--数据:");
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
     * 我的历史
     */
    public void getMyHistoryData(BaseActivity mContext, int type, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        LogUtils.e("我的历史--参数--" + type + "--" + page);
        OkGo.<String>get(Neturl.MY_HISTORY)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("type", type + "")        //type==1 今日 ； 2  7日； 3更多
                .params("page", page)
                .params("page_size", ConstantUtils.pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "我的历史--数据:");
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
     * 删除历史
     */
    public void getMyHistoryDeleteData(BaseActivity mContext, Object[] arrayIds, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
//        LogUtils.e("删除历史--参数11--"+ids.toString());
        mContext.showProgress();
        IdsParas mIdsParas = new IdsParas(arrayIds);
        Gson gs = new Gson();
        String paraString = gs.toJson(mIdsParas);//把数据转为JSON格式的字符串
        LogUtils.e("删除历史--参数--" + paraString);
        OkGo.<String>post(Neturl.MY_HISTORY_DELETE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "删除历史--数据:");
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
     * 获取反馈类型
     */
    public void getBackTypeData(BaseActivity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>get(Neturl.MY_OPINION_TYPE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "获取反馈类型--数据:");
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
     * 提交建议--上传图片
     */
    public void postOpinionImageData(BaseActivity mContext, File imageFile, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>post(Neturl.MY_FILE_IMAGE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .params("file",imageFile)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "提交建议--上传图片--数据:");
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
     * 提交建议
     */
    public void getOpinionPutData(BaseActivity mContext, String imagesString,String inputContent,int type, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OpinionPutParas mOpinionPutParas = new OpinionPutParas(imagesString, inputContent, type+"");
        Gson gs = new Gson();
        String paraString = gs.toJson(mOpinionPutParas);//把数据转为JSON格式的字符串
        LogUtils.e("提交建议--参数--" + paraString);
        OkGo.<String>post(Neturl.MY_OPINION_PUT)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "提交建议--数据:");
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
     * 公告列表
     */
    public void getMyNotifyData(BaseActivity mContext, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>get(Neturl.MY_NOTIFY)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("page", page + "")
                .params("page_size", ConstantUtils.pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "公告列表--数据:");
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
     * 读取消息
     */
    public void readNotifyData(BaseActivity mContext, Object[] ids, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        IdsParas mIdsParas = new IdsParas(ids);
        Gson gs = new Gson();
        String paraString = gs.toJson(mIdsParas);//把数据转为JSON格式的字符串
        LogUtils.e("读取消息--参数--" + paraString);
        OkGo.<String>post(Neturl.MY_NOTIFY_READ)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .upJson(paraString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "读取消息--数据:");
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
     * 问题列表
     */
    public void getOpionQuestionListData(BaseActivity mContext, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>get(Neturl.MY_OPINION_QUESTION)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("page", page)
                .params("page_size", ConstantUtils.pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "问题列表--数据:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        mContext.dismissProgress();
                        super.onError(response);
                        ToastUtils.showShort(mContext.getString(R.string.request_error));
                        callback.onError(response.code());
                    }
                });
    }

    /**
     * 反馈列表
     */
    public void getOpionListData(BaseActivity mContext, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>get(Neturl.MY_OPINION_LIST)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("page", page)
                .params("page_size", ConstantUtils.pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "反馈列表--数据:");
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
     * 生成推广码
     */
    public void getPromoteCodeData(BaseActivity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>post(Neturl.MY_PROMOTE_CODE)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "生成推广码--数据:");
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
     * 下载推广码--文件下载
     */
    public void downPromoteData(BaseActivity mContext, String imageUrl, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        String imageNmae = System.currentTimeMillis() + ".png";
        String imagePath = null;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            imagePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/";
        } else {  // Meizu 、Oppo
            imagePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/";
        }
        OkGo.<File>get(imageUrl).tag(mContext).execute(new FileCallback(imagePath, imageNmae) { //文件下载时指定下载的路径以及下载的文件的名称
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
//                LogUtils.e("开始下载文件" + "DDDDD");
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
//                LogUtils.e("下载文件成功" + "DDDDD" + response.body().length());

            }

            @Override
            public void onFinish() {
                super.onFinish();
//                LogUtils.e("下载文件完成" + "DDDDD");
                mContext.dismissProgress();
                callback.onSuccess("下载成功");
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                mContext.dismissProgress();
//                LogUtils.e("下载文件出错" + "DDDDD" + response.message());
//                callback.onError(404);

            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                float dLProgress = progress.fraction;
//                LogUtils.e("文件下载的进度" + "DDDDD" + dLProgress);
            }
        });
    }

    /**
     * 下载成功后的回调
     */
    public void downPromoteSuccessBackData(BaseActivity mContext, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>post(Neturl.MY_PROMOTE_CODE_BACK)
                .headers(YlUtils.getHttpHeaders(mContext))
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "下载成功后的回调--数据:");
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
     * 推广记录
     */
    public void getPromoteRecordData(BaseActivity mContext, int page, RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        mContext.showProgress();
        OkGo.<String>get(Neturl.MY_PROMOTE_RECORD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("page", page + "")
                .params("page_size", ConstantUtils.pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "推广记录--数据:");
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
     * 获取个人中心广告
     */
    public void myAdData(BaseActivity mContext,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>get(Neturl.MY_AD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "获取个人中心广告-数据:");
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
     * 任务读取广告
     */
    public void taskReadAdData(BaseActivity mContext,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>post(Neturl.TASK_ADREAD)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "任务读取广告-数据:");
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
     * 推广任务列表
     */
    public void getPromoteTaskData(BaseActivity mContext,  RequestCallback callback) {
        if (!isIntentConnect(mContext)) {
            return;
        }
        OkGo.<String>get(Neturl.MY_PROMOTE_TASK)
                .headers(YlUtils.getHttpHeaders(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mContext.dismissProgress();
                        parseJson(mContext, response, callback, "推广任务列表-数据:");
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
