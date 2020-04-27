package com.video.yali.authority;


import android.annotation.SuppressLint;
import android.os.Build;

import androidx.fragment.app.FragmentActivity;


public class PermissionUtils {
    /**
     * 一次性申请一个 或者多个权限
     * 只要其中有一个没有通过,返回为未通过
     *
     * @param activity
     * @param listener
     * @param permissions
     */
    public static void permissionApply(FragmentActivity activity, PermissionListener listener, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (listener != null) {
                listener.onAllowPermission();
            }
            return;
        }
        SquRabRxPermissions rxPermissions = new SquRabRxPermissions(activity);
        apply(listener, rxPermissions, permissions);
    }


    @SuppressLint("CheckResult")
    private static void apply(final PermissionListener listener, SquRabRxPermissions rxPermissions, String[] permissions) {
        rxPermissions
                .request(permissions)
                .subscribe(new PermissionConsumer(listener), new PermissionErrorConsumer(listener));
    }

}
