package com.video.yali.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.lzy.okgo.model.HttpHeaders;
import com.video.yali.MyApplication;
import com.video.yali.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Calendar;

import androidx.annotation.NonNull;

/**
 * 项目开发使用工具类
 */
public class ToolUtils {

    /**
     * dp 转 px
     *
     * @param context {@link Context}
     * @param dpValue {@code dpValue}
     * @return {@code pxValue}
     */
    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code dpValue}
     */
    public static int pix2dip(@NonNull Context context, int pxValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param context {@link Context}
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code spValue}
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp 转 dp
     *
     * @param context {@link Context}
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2dp(@NonNull Context context, float spValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * dp 转 sp
     *
     * @param context {@link Context}
     * @param dpValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int dp2sp(@NonNull Context context, float dpValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (dpValue * fontScale + 0.5f);
    }


    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }


    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }


    /**
     * 跳转界面
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }


    public static void setBottomNavigationItem(Context mContext,BottomNavigationBar bottomNavigationBar, int space, int imgLen, int textSize){
        Class barClass = bottomNavigationBar.getClass();
        Field[] fields = barClass.getDeclaredFields();
        for(int i = 0; i < fields.length; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.getName().equals("mTabContainer")){
                try{
                    //反射得到 mTabContainer
                    LinearLayout mTabContainer = (LinearLayout) field.get(bottomNavigationBar);
                    for(int j = 0; j < mTabContainer.getChildCount(); j++){
                        //获取到容器内的各个Tab
                        View view = mTabContainer.getChildAt(j);
                        //获取到Tab内的各个显示控件
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext,56));
                        FrameLayout container = (FrameLayout) view.findViewById(R.id.fixed_bottom_navigation_container);
                        container.setLayoutParams(params);
                        container.setPadding(dip2px(mContext,12), dip2px(mContext,0), dip2px(mContext,12), dip2px(mContext,0));

                        //获取到Tab内的文字控件
                        TextView labelView = (TextView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_title);
                        //计算文字的高度DP值并设置，setTextSize为设置文字正方形的对角线长度，所以：文字高度（总内容高度减去间距和图片高度）*根号2即为对角线长度，此处用DP值，设置该值即可。
                        labelView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                        labelView.setIncludeFontPadding(false);
                        labelView.setPadding(0,0,0,dip2px(mContext,20-textSize - space/2));

                        //获取到Tab内的图像控件
                        ImageView iconView = (ImageView) view.findViewById(com.ashokvarma.bottomnavigation.R.id.fixed_bottom_navigation_icon);
                        //设置图片参数，其中，MethodUtils.dip2px()：换算dp值
                        params = new FrameLayout.LayoutParams(dip2px(mContext,imgLen), dip2px(mContext,imgLen));
                        params.setMargins(0,0,0,space/2);
                        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                        iconView.setLayoutParams(params);
                    }
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断字符串是否包含
     *
     * @param bigStr
     * @param smallStr
     * @return
     */
    public static boolean isStrInString(String bigStr, String smallStr) {
        return bigStr.toUpperCase().contains(smallStr.toUpperCase());
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 显示背景透明度
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public static int getSysYear() {
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        return year;
    }
    public static void setTextImage(Context mContext, TextView textView, int pic, int position) {
        Drawable img = mContext.getResources().getDrawable(pic);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        if (position == 1) {
            textView.setCompoundDrawables(img, null, null, null); //设置左图标
        } else if (position == 2) {
            textView.setCompoundDrawables(null, img, null, null); //设置上图标
        } else if (position == 3) {
            textView.setCompoundDrawables(null, null, img, null); //设置右图标
        } else if (position == 4) {
            textView.setCompoundDrawables(null, null, null, img); //设置下图标
        }
    }

    //sim卡是否可读
    public static String getModelInfo() {
        String modelInfo = null;
        String manufacturer = android.os.Build.MANUFACTURER;
        String brand = android.os.Build.BRAND;
        String board = android.os.Build.BOARD;
        String device = android.os.Build.DEVICE;
        String model = android.os.Build.MODEL;
        String product = android.os.Build.PRODUCT;
        String version = Build.VERSION.SDK_INT + "";
        String release = android.os.Build.VERSION.RELEASE;
        String ID = android.os.Build.ID;
        modelInfo = "硬件制造商：" + manufacturer + "；品牌名称：" + brand + "；主板名称：" + board + "；设备名：" + device + "；型号："
                + model + "；手机厂商：" + product + "；安卓系统版本号：" + release + "-" + version + "；ID：" + ID;
        Log.e("设备信息", modelInfo);
        //设备信息: 硬件制造商：Xiaomi；品牌名称：xiaomi；主板名称：sdm660；设备名：lavender；型号：Redmi Note 7；手机厂商：lavender；安卓系统版本号：9-28；ID：PKQ1.180904.001
        //设备信息: 硬件制造商：Meizu；品牌名称：Meizu；主板名称：PRO7Plus；设备名：PRO7Plus；型号：PRO 7 Plus；手机厂商：meizu_PRO7Plus_CN；安卓系统版本号：7.0-24；ID：NRD90M
        //设备信息: 硬件制造商：HUAWEI；品牌名称：HONOR；主板名称：JSN-AL10；设备名：HWJSN-HM；型号：JSN-AL00a；手机厂商：JSN-AL00a；安卓系统版本号：9-28；ID：HONORJSN-AL00a
        return modelInfo;
    }
}
