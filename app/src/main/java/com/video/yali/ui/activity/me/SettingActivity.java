package com.video.yali.ui.activity.me;

import android.app.LauncherActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.CheckVersionBean;
import com.video.yali.bean.UserBean;
import com.video.yali.download.MyDownloadActivity;
import com.video.yali.model.SettingModel;
import com.video.yali.ui.activity.MainActivity;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.utils.CacheDataUtils;
import com.video.yali.utils.DownLoadApkUtils;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.sb_setting_logout)
    SuperButton sbSettingLogout;
    @BindView(R.id.ll_mysetting_info)
    LinearLayout llMysettingInfo;
    @BindView(R.id.tv_mysetting_phone)
    TextView tvMysettingPhone;
    @BindView(R.id.ll_mysetting_phone)
    LinearLayout llMysettingPhone;
    @BindView(R.id.ll_mysetting_pswd)
    LinearLayout llMysettingPswd;
    @BindView(R.id.tv_mysetting_cache)
    TextView tvMysettingCache;
    @BindView(R.id.ll_mysetting_cache)
    LinearLayout llMysettingCache;
    @BindView(R.id.tv_mysetting_language)
    TextView tvMysettingLanguage;
    @BindView(R.id.ll_mysetting_language)
    LinearLayout llMysettingLanguage;
    @BindView(R.id.tv_mysetting_version)
    TextView tvMysettingVersion;
    @BindView(R.id.ll_mysetting_version)
    LinearLayout llMysettingVersion;
    @BindView(R.id.ll_mysetting_agree)
    LinearLayout llMysettingAgree;
    private UserBean mUserBean;
    private SettingModel model;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        model = new SettingModel();
        mUserBean = YlUtils.getUserInfo();
        tvTitleMiddle.setText(R.string.text_setting);
        tvTitleMiddle.setVisibility(View.VISIBLE);

        if (mUserBean != null) {
            if (!TextUtils.isEmpty(mUserBean.getPhone())) {
                tvMysettingPhone.setText(mUserBean.getPhone());
            }
        }

        tvMysettingLanguage.setText(getString(R.string.language_china));
        tvMysettingVersion.setText(ToolUtils.getVersion(this));
        tvMysettingCache.setText(CacheDataUtils.getTotalCacheSize(this));

    }


    @OnClick({R.id.ib_title_left, R.id.ll_mysetting_info, R.id.ll_mysetting_phone, R.id.ll_mysetting_pswd, R.id.ll_mysetting_cache, R.id.ll_mysetting_language,
            R.id.ll_mysetting_version, R.id.ll_mysetting_agree, R.id.sb_setting_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.ll_mysetting_info:
//                ActivityUtils.startActivity(PersonalActivity.class);
                ToolUtils.startActivity(SettingActivity.this, PersonalActivity.class);
                break;
            case R.id.ll_mysetting_phone:
                break;
            case R.id.ll_mysetting_pswd:
                ToolUtils.startActivity(SettingActivity.this, PswdModifyActivity.class);
                break;
            case R.id.ll_mysetting_cache:
                CacheDataUtils.clearAllCache(SettingActivity.this);
                tvMysettingCache.setText(CacheDataUtils.getTotalCacheSize(this));
                ToastUtils.showShort(getString(R.string.clear_success));
                break;
            case R.id.ll_mysetting_language:
                ToolUtils.startActivity(SettingActivity.this, LanguageActivity.class);
                break;
            case R.id.ll_mysetting_version:
                checkVersion();
                break;
            case R.id.ll_mysetting_agree:
                Intent webIntent = new Intent(this, WebActivity.class);
                webIntent.putExtra("webType", WebActivity.WebAgree);
                startActivity(webIntent);
                break;
            case R.id.sb_setting_logout:
                YlUtils.loginUser(this);
                finish();
                break;
        }
    }


    private void checkVersion() {

        SettingModel model = new SettingModel();
        model.checkVersion(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                if (TextUtils.isEmpty(data)|| data.equals("null")) {
                    ToastUtils.showLong(getString(R.string.version));
                    return;
                }
                CheckVersionBean checkVersionBean = GsonUtils.fromJson(data, CheckVersionBean.class);
                int status = checkVersionBean.getStatus();
                String currentVersion = ToolUtils.getVersion(SettingActivity.this);
                String serverVersion = checkVersionBean.getVersion().replace("v", "");
                if (checkVersionBean == null || currentVersion.equals(serverVersion)) {
                    return;
                }
                if (status == 1||status==4) {
                    ToastUtils.showLong(getString(R.string.version));
                    return;
                }
                String description = checkVersionBean.getDescription();
                String downloadUrl = checkVersionBean.getDownloadUrl();
                String version = checkVersionBean.getVersion();
                DownLoadApkUtils.downLoadApk(SettingActivity.this, downloadUrl, description, status,version);

            }

            @Override
            public void onError(int code) {

            }
        });
    }


}
