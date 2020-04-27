package com.video.yali.ui.activity.me;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.ui.activity.MainActivity;
import com.video.yali.utils.ConstantUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageActivity extends BaseActivity {


    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.tv_language_zhrch)
    TextView tvLanguageZhrch;
    @BindView(R.id.Language_zhrtw)
    TextView LanguageZhrtw;
    @BindView(R.id.tv_language_enrus)
    TextView tvLanguageEnrus;

    @Override
    public int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.ib_title_left, R.id.tv_language_zhrch, R.id.Language_zhrtw, R.id.tv_language_enrus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_language_zhrch:
                changeZhrch();
                break;
            case R.id.Language_zhrtw:
                changeZhrtw();
                break;
            case R.id.tv_language_enrus:
                changeEnrus();
                break;
        }
    }

    private void changeEnrus() {        //切换英文
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.ENGLISH; // 英文
        resources.updateConfiguration(config, dm);
        finish();//如果不重启当前界面，是不会立马修改的
        startActivity(new Intent(this, MainActivity.class));
        SPStaticUtils.put(ConstantUtils.KEY_LANGUAGE, "ENGLISH");
    }

    private void changeZhrtw() {        //切换繁体
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.TAIWAN; // 繁体中文
        resources.updateConfiguration(config, dm);
        finish();////如果不重启当前界面，是不会立马修改的
        startActivity(new Intent(this,MainActivity.class));
        SPStaticUtils.put(ConstantUtils.KEY_LANGUAGE, "FANTI");
    }

    private void changeZhrch() {        //切换简体
        Resources resources = getResources();// 获得res资源对象
        Configuration config = resources.getConfiguration();// 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = Locale.CHINA; // 简体中文
        resources.updateConfiguration(config, dm);
        finish();////如果不重启当前界面，是不会立马修改的
        startActivity(new Intent(this,MainActivity.class));
        SPStaticUtils.put(ConstantUtils.KEY_LANGUAGE, "ZHENGWENG");
    }
}
