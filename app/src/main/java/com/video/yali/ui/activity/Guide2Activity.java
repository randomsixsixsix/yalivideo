package com.video.yali.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.AdBean;
import com.video.yali.model.MainModel;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;


/**
 * Created by Administrator on 2018/6/21.
 * 引导页
 */

public class Guide2Activity extends BaseActivity {


    @BindView(R.id.iv_guide2_image)
    ImageView ivGuide2Image;
    @BindView(R.id.ll_jump)
    LinearLayout llJump;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.jump)
    TextView tvJump;
    private MainModel model = new MainModel();
    private MyCount mc;
    private AdBean mVideoAdBean;


    @Override
    public int getLayoutId() {
        return R.layout.activity_guide2;
    }

    @Override
    public void initView() {
        llJump.setVisibility(View.VISIBLE);
        if (mc == null) {
            mc = new MyCount(6000, 1000);
        }
        mc.start();
        getSplashNet();
        String imageUrl = SPUtils.getInstance().getString(GlobConstant.GUIDEIMAGE);
        if (!TextUtils.isEmpty(imageUrl)) {
            showImage(imageUrl);
        }

    }

    private void showImage(String imageUrl) {
//        LogUtils.e("引导图图片--"+imageUrl);
        Glide.with(Guide2Activity.this)
                .load(imageUrl)
                .apply(centerCropTransform()
                        .placeholder(R.drawable.default_cover_xhsp)
                        .error(R.drawable.default_cover_xhsp)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false))
                .into(ivGuide2Image);
    }

    //启动页广告
    private void getSplashNet() {
        model.getSplashData(Guide2Activity.this, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                mVideoAdBean = gson.fromJson(data, AdBean.class);
                if (mVideoAdBean != null && !TextUtils.isEmpty(mVideoAdBean.getImage())) {
                    SPUtils.getInstance().put(GlobConstant.GUIDEIMAGE, mVideoAdBean.getImage());
                    showImage(mVideoAdBean.getImage());
                }


            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @OnClick({R.id.iv_guide2_image, R.id.jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_guide2_image:      //点击广告图，进行跳转
                if (mVideoAdBean != null) {
                    YlUtils.startAdDetails(this, ConstantUtils.adtype_guide, mVideoAdBean.getType(), mVideoAdBean.getTarget_url(), mVideoAdBean.getId());
                }
                break;
            case R.id.jump:
                startActivity(new Intent(Guide2Activity.this, MainActivity.class));
                finish();
                break;
        }
    }

    /* 定义一个倒计时的内部类 */
    class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tvCountDown.setVisibility(View.GONE);
            tvJump.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvCountDown.setText(millisUntilFinished / 1000 + "");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mc != null) {
            mc.cancel();
            mc = null;
        }
    }
}
