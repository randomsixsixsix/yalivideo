package com.video.yali.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpHeaders;
import com.video.yali.GlobConstant;
import com.video.yali.MyApplication;
import com.video.yali.R;
import com.video.yali.bean.UserBean;
import com.video.yali.event.OrangeEvent;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.ui.activity.colum.ColumnListActivity;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.ui.activity.login.LoginMobileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

/**
 * 项目逻辑流程使用工具类
 */
public class YlUtils {


    public static void saveUserInfo(UserBean mUserBean) {
        if (mUserBean == null) {
            SPStaticUtils.put("userInfo", "");
        } else {
            Gson gs = new Gson();
            SPStaticUtils.put("userInfo", gs.toJson(mUserBean));
        }

    }

    public static UserBean getUserInfo() {
        String userJson = SPStaticUtils.getString("userInfo");
        Gson gson = new Gson();
        return gson.fromJson(userJson, UserBean.class);
    }

    public static boolean judgeUserExist() {
        boolean userExist = false;
        UserBean mUserBean = getUserInfo();
        if (mUserBean != null && !TextUtils.isEmpty(mUserBean.getToken())) {     //存在用户
//        if (mUserBean != null && !TextUtils.isEmpty(mUserBean.getToken()) && !TextUtils.isEmpty(mUserBean.getPhone())) {     //存在用户
            userExist = true;
        }
        return userExist;
    }

    public static void loginUser(Activity activity) {
        ToolUtils.startActivity(activity, LoginMobileActivity.class);
        YlUtils.saveUserInfo(null);
        //发布事件(获取下单的通知)
        EventBus.getDefault().post(new OrangeEvent(ConstantUtils.EVENT_FRESHUSERINFO));
    }

    public static String getDeviceCode(Context context) {
        return MobileUtils.getOnlyNumb(context);
    }


    public static HttpHeaders getHttpHeaders(Activity activity) {
        String language = SPStaticUtils.getString(ConstantUtils.KEY_LANGUAGE);
        if (TextUtils.isEmpty(language)) {
            language = "ZHONGWEN";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("app-version", ToolUtils.getVersion(activity));
        headers.put("app-name", "ylsp");
        headers.put("app-language", language);
        headers.put("platform", "android");
        headers.put("phone-system", "Android " + android.os.Build.VERSION.RELEASE);
        headers.put("phone-type", android.os.Build.MODEL + " Build/" + android.os.Build.ID);
        if (!TextUtils.isEmpty(getDeviceCode(activity))) {
            headers.put("uuid", getDeviceCode(activity));//APP唯一值(设备号)
        }
        if (YlUtils.getUserInfo() != null && !TextUtils.isEmpty(YlUtils.getUserInfo().getToken())) {
            headers.put("Authorization", "Bearer " + YlUtils.getUserInfo().getToken());//token
        } else {
            headers.remove("Authorization");//token
        }
        return headers;
    }


    /**
     * 判断广告跳转逻辑
     *
     * @param activity
     * @param viewtype   三种情况 ConstantUtils的 adtype_guide  adtype_banner adtype_colum
     * @param adtype     广告类型跳转不同页面
     * @param target_url 链接
     * @param id         内部页面所需要的id
     */
    public static void startAdDetails(Activity activity, int viewtype, int adtype, String target_url, int id) {
        if (adtype == 1) {  //内部链接
            Intent webIntent = new Intent(activity, WebActivity.class);
            webIntent.putExtra("webType", viewtype);
            webIntent.putExtra("webUrl", target_url);
            activity.startActivity(webIntent);
        } else if (adtype == 2) {  //外部链接
            Intent intent3 = new Intent();
            intent3.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(target_url);
            intent3.setData(content_url);
            ActivityUtils.startActivity(intent3);
//            Intent webIntent = new Intent(activity, WebActivity.class);
//            webIntent.putExtra("webType", viewtype);
//            webIntent.putExtra("webUrl", target_url);
//            activity.startActivity(webIntent);
        } else if (adtype == 3) {  //电影详情
            Intent webIntent = new Intent(activity, VideoDetailsActivity.class);
            webIntent.putExtra(GlobConstant.VIDEOTYPE, viewtype);
            webIntent.putExtra(GlobConstant.VIDEOID, id);
            activity.startActivity(webIntent);
        } else if (adtype == 4) {  //专题详情
            Intent webIntent = new Intent(activity, ColumnListActivity.class);
            webIntent.putExtra("id", id);
            activity.startActivity(webIntent);
        }

    }
}
