package com.video.yali.utils;

/**
 * Created
 */
public interface RequestCallback {

    void onSuccess(String data);

    void onError(int code);

}
