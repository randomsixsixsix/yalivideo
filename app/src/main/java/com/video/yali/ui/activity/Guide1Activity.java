package com.video.yali.ui.activity;

import android.content.Intent;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;


import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.authority.PermissionListener;
import com.video.yali.authority.PermissionUtils;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.AdBean;
import com.video.yali.model.MainModel;
import com.video.yali.utils.RequestCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;


/**
 * Created by Administrator on 2018/6/21.
 * 引导页
 */

public class Guide1Activity extends BaseActivity implements PermissionListener {
    @BindView(R.id.guide1_banner)
    BGABanner guideBanner;
    private MainModel model = new MainModel();

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide1;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();

        ButterKnife.bind(this);
        PermissionUtils.permissionApply(this, this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                , android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.CAMERA);

        BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
        guideBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP, R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3);
        guideBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide1_enter, 0, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(Guide1Activity.this, MainActivity.class));
                finish();
            }
        });
    }


    //启动页广告
    private void getSplashNet() {
        model.getSplashData(Guide1Activity.this, new RequestCallback() {

            @Override
            public void onSuccess(String data) {

            }

            @Override
            public void onError(int code) {

            }
        });
    }


    @Override
    public void onAllowPermission() {
        Log.d("Guide1Activity", "申请成功");
        getSplashNet();
    }

    @Override
    public void onRefusePermission() {
        System.exit(0);

    }

    @Override
    public void onError(Throwable throwable) {
        System.exit(0);
    }
}
