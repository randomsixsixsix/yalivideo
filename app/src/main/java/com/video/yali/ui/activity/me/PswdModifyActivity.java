package com.video.yali.ui.activity.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.UserBean;
import com.video.yali.model.LoginModel;
import com.video.yali.model.SettingModel;
import com.video.yali.ui.activity.login.SmsCodeActivity;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.MyCount;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class PswdModifyActivity extends BaseActivity {

    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.iv_pswdmodify_headerpic)
    ImageView ivPswdmodifyHeaderpic;
    @BindView(R.id.tv_pswdmodify_name)
    TextView tvPswdmodifyName;
    @BindView(R.id.tv_pswdmodify_rank)
    TextView tvPswdmodifyRank;
    @BindView(R.id.tv_pswdmodify_phone)
    TextView tvPswdmodifyPhone;
    @BindView(R.id.et_pswdmodify_smscode)
    EditText etPswdmodifySmscode;
    @BindView(R.id.sb_pswdmodify_smssend)
    SuperButton sbPswdmodifySmssend;
    @BindView(R.id.et_pswdmodify_newpswd)
    EditText etPswdmodifyNewpswd;
    @BindView(R.id.bt_pswdmodify_ok)
    Button btPswdmodifyOk;

    private SettingModel model = new SettingModel();

    private MyCount mc;
    private UserBean mUserBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pswd_modify;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.pswd_modify));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        mUserBean = YlUtils.getUserInfo();
        if (mUserBean != null) {
            if (!TextUtils.isEmpty(mUserBean.getAvatar())) {
                Glide.with(this)
                        .load(mUserBean.getAvatar())
                        .apply(centerCropTransform()
                                .placeholder(R.mipmap.default_header_logo)
                                .error(R.mipmap.default_header_logo)
                                .priority(Priority.HIGH)
                                .transform(new GlideCircleTransform())  //圆形头像,自定义类
                                .diskCacheStrategy(DiskCacheStrategy.NONE)  //跳过磁盘缓存
                                .skipMemoryCache(false))     //跳过内存缓存
                        .into(ivPswdmodifyHeaderpic);
            }
            if (!TextUtils.isEmpty(mUserBean.getName())) {
                tvPswdmodifyName.setText(mUserBean.getName());
            }
            if (!TextUtils.isEmpty(mUserBean.getCurrent_level_name())) {
                tvPswdmodifyRank.setText(mUserBean.getCurrent_level_name());
            }
            tvPswdmodifyPhone.setText(String.format(getString(R.string.phone_desc), mUserBean.getName()));
        }
    }


    @OnClick({R.id.ib_title_left, R.id.sb_pswdmodify_smssend, R.id.bt_pswdmodify_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.sb_pswdmodify_smssend:
                sendPswdModifySmsCodeNet();
                break;
            case R.id.bt_pswdmodify_ok:
                String inputSmsCode = etPswdmodifySmscode.getText().toString().trim();
                String inputPswd = etPswdmodifyNewpswd.getText().toString().trim();
                if (TextUtils.isEmpty(inputSmsCode)) {
                    ToastUtils.showShort(getString(R.string.smscode_empty_tip));
                    return;
                }
                if (TextUtils.isEmpty(inputPswd)) {
                    ToastUtils.showShort(getString(R.string.pswd_empty_tip));
                    return;
                }
                pswdModifyNet(inputSmsCode, inputPswd);
                break;
        }
    }


    private void sendPswdModifySmsCodeNet() {
        model.sendPswdModefySmsCodeData(this, mUserBean.getPhone_code(), mUserBean.getPhone(), "", "", new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("修改密码--验证码发送成功--返回数据----" + data);
                        sendCodeSuccess();
                    }

                    @Override
                    public void onError(int code) {
                        PswdModifyActivity.this.dismissProgress();
                    }
                }
        );
    }

    private void pswdModifyNet(String inputSmsCode, String inputPswd) {
        model.pswdModifyData(this, mUserBean.getPhone_code(), mUserBean.getPhone(), inputPswd, inputSmsCode, new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("修改密码--修改成功--成功返回数据----" + data);
                        ToastUtils.showShort(getString(R.string.pswd_modify_success_tip));
                        finish();

                    }

                    @Override
                    public void onError(int code) {
                        PswdModifyActivity.this.dismissProgress();
                    }
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mc != null) {
            mc.cancel();
            mc = null;
        }
    }

    /**
     * 请求发送验证码成功
     */
    public void sendCodeSuccess() {
        if (mc == null) {
            mc = new MyCount(60000, 1000, this, sbPswdmodifySmssend);
        }
        mc.start();
        sbPswdmodifySmssend.setEnabled(true);
    }
}
