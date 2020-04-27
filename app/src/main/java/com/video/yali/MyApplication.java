package com.video.yali;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {

    private static Application mApplication;

    /**
     * @return Context
     * @Description: 获取全局上下文对象
     */
    public static Context getContext() {
        return mApplication;
    }

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //layout.setPrimaryColorsId(R.color.theme, R.color.theme);//全局设置主题颜色
                return new MaterialHeader(context).setColorSchemeColors(getContext().getResources().getColor(R.color.app_theme));//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(16);
            }
        });
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);
        LogUtils.getConfig().setLogSwitch(true);//全局日志开关
        LogUtils.getConfig().setGlobalTag(GlobConstant.APPLOG);
        OkGo.getInstance().init(this);
        CrashReport.initCrashReport(this, "402f43d375", true);
    }



}
