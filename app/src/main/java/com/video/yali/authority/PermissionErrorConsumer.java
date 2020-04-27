package com.video.yali.authority;

import io.reactivex.functions.Consumer;

class PermissionErrorConsumer implements Consumer<Throwable> {
    private PermissionListener listener;

    public PermissionErrorConsumer(PermissionListener listener) {
        this.listener = listener;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        if (listener != null) {
            listener.onError(throwable);
        }
    }
}
