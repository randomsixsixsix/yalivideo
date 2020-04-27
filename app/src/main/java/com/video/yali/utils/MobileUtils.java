package com.video.yali.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

@SuppressLint("MissingPermission")
public class MobileUtils {

    //1.手机设备的串号DEVICE_ID
    private static String getDeviceId(Context mContext) {
        String DEVICE_ID = null;
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            DEVICE_ID= tm.getDeviceId();
        }catch (Exception e){
            e.getMessage();
        }
        return DEVICE_ID;
    }

    // 2.序列号（sn）
    private static String getSerialNumber(Context mContext) {
        String sn = null;
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            sn= tm.getSimSerialNumber();
        }catch (Exception e){
            e.getMessage();
        }
        return sn;
    }

    //3. wifi mac地址
    private static String getMacAddress(Context mContext) {
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        return wifiMac;
    }

    //4.16进制的字符串就是ANDROID_ID
    private static String getAndroidId(Context mContext) {
        String androidId = Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);
        return androidId;
    }

    //5.设备安装ID
    private static String getInstalltionId(Context mContext) {
        return Installation.id(mContext);
    }


    public static String getOnlyNumb(Context mContext) {
        if (!TextUtils.isEmpty(getDeviceId(mContext))){
//            Logger.e("设备唯一标识------串号:%s",getDeviceId(mContext));
            return getDeviceId(mContext);
        }
        if (!TextUtils.isEmpty(getSerialNumber(mContext))){
//            Logger.e("设备唯一标识------序列号:%s",getSerialNumber(mContext));
            return getSerialNumber(mContext);
        }
        if (!TextUtils.isEmpty(getMacAddress(mContext))){
//            Logger.e("设备唯一标识------MAC:%s",getMacAddress(mContext));
            return getMacAddress(mContext);
        }
        if (!TextUtils.isEmpty(getAndroidId(mContext))){
//            Logger.e("设备唯一标识------安卓号:%s",getAndroidId(mContext));
            return getAndroidId(mContext);
        }
        if (!TextUtils.isEmpty(getInstalltionId(mContext))){
//            Logger.e("设备唯一标识------安装号:%s",getInstalltionId(mContext));
            return getInstalltionId(mContext);
        }
        return null;
    }
}
