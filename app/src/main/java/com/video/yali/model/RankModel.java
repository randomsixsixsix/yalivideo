package com.video.yali.model;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.video.yali.Neturl;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

public class RankModel extends RequestModel {


    public void getRankList(Activity mContext, String url, String type, int page, RequestCallback callback) {

        if (!isIntentConnect(mContext)) {
            return;
        }

        OkGo.<String>get(url)
                .headers(YlUtils.getHttpHeaders(mContext))
                .params("type", type)
                .params("page", page)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        parseJson((Activity) mContext, response, callback, "排行榜:");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onError(response.code());
                        LogUtils.i("排行榜:" + response.code());
                    }
                });

    }
}
