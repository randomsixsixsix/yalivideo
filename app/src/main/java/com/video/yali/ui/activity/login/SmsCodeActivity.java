package com.video.yali.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dalimao.corelibrary.VerificationCodeInput;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.model.LoginModel;
import com.video.yali.utils.MyCount;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.widget.VerificationCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmsCodeActivity extends BaseActivity {


    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_smscode_title)
    TextView tvSmscodeTitle;
    @BindView(R.id.tv_smscode_phonedesc)
    TextView tvSmscodePhonedesc;
    @BindView(R.id.vci_smscode_input)
    VerificationCodeInput vciSmscodeInput;
    @BindView(R.id.bt_smscode_sendcode)
    Button btSmscodeSendcode;
    @BindView(R.id.bt_smscode_ok)
    Button btSmscodeOk;
    @BindView(R.id.tv_smscode_login)
    TextView tvSmscodeLogin;

    public static String activityTypeName = "activityTypekey";
    public static String activityAreaCode = "areaCodeKey";
    public static String activityInputPhone = "inputPhonekey";
    public static String activityInputPswd = "inputPswdkey";
    public static String activityInputInvite = "inputInvitekey";
    public static int registerType = 1;
    public static int findpswdType = 2;
    @BindView(R.id.verificationCodeView)
    VerificationCodeView verificationCodeView;
    private String smsCode;
    private LoginModel model = new LoginModel();
    private int areaCode;
    private String inputPhone;
    private String inputPswd;
    private String inputInvite;
    private Boolean isSmsCodeValid = false;
    private MyCount mc;
    private int activityType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sms_code;
    }

    @Override
    public void initView() {
        activityType = getIntent().getIntExtra(activityTypeName, registerType);
        if (activityType == registerType) {
            tvSmscodeTitle.setText(getString(R.string.register_account));

            tvSmscodeLogin.setVisibility(View.VISIBLE);
            btSmscodeOk.setText(getString(R.string.text_register));
            areaCode = getIntent().getIntExtra(activityAreaCode, 86);
            inputPhone = getIntent().getStringExtra(activityInputPhone);
            inputPswd = getIntent().getStringExtra(activityInputPswd);
            inputInvite = getIntent().getStringExtra(activityInputInvite);
        } else {
            tvSmscodeTitle.setText(getString(R.string.find_pswd));
            tvSmscodeLogin.setVisibility(View.GONE);
            btSmscodeOk.setText(getString(R.string.text_ok));
            areaCode = getIntent().getIntExtra(activityAreaCode, 86);
            inputPhone = getIntent().getStringExtra(activityInputPhone);
            inputPswd = getIntent().getStringExtra(activityInputPswd);
        }

        tvSmscodePhonedesc.setText(String.format(getString(R.string.login_phone_desc), inputPhone));

        verificationCodeView.setOnVerificationCodeCompleteListener(new VerificationCodeView.OnVerificationCodeCompleteListener() {
            @Override
            public void verificationCodeComplete(String verificationCode) {
                isSmsCodeValid = true;
                smsCode = verificationCode;
                if (activityType == registerType) {
                    registerNet();
                } else {
                    pswdForgetCheckCodeNet();
                }
            }

            @Override
            public void verificationCodeIncomplete(String verificationCode) {

            }
        });
        //开始倒计时，到了这个界面，说明上一个界面已经请求过验证码
        sendCodeSuccess();

    }

    @Override
    public void initListener() {
        vciSmscodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                isSmsCodeValid = true;
                smsCode = content;
                if (activityType == registerType) {
                    registerNet();
                } else {
                    pswdForgetCheckCodeNet();
                }
            }
        });
    }

    @OnClick({R.id.ib_title_left, R.id.bt_smscode_ok, R.id.bt_smscode_sendcode, R.id.tv_smscode_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_smscode_login:
                ToolUtils.startActivity(this, LoginMobileActivity.class);
                break;

            case R.id.bt_smscode_sendcode:
                if (activityType == registerType) {
                    sendRegisterSmsCodeNet();
                } else {
                    sendPswdSmsCodeNet();
                }

                break;
            case R.id.bt_smscode_ok:
                if (!isSmsCodeValid) {
                    ToastUtils.showShort(getString(R.string.login_code_first));
                    return;
                }
                if (activityType == registerType) {
                    registerNet();
                } else {
                    pswdForgetCheckCodeNet();
                }

                break;

        }
    }


    private void sendRegisterSmsCodeNet() {
        model.sendRegisterSmsCodeData(this, areaCode, inputPhone, inputPswd, inputInvite, new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("验证码发送成功返回数据----" + data);
                        sendCodeSuccess();
                    }

                    @Override
                    public void onError(int code) {
                        SmsCodeActivity.this.dismissProgress();
                    }
                }
        );
    }

    private void registerNet() {
        model.registerNet(this, smsCode, areaCode, inputPhone, inputPswd, inputInvite, new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("注册成功返回数据----" + data);
                        ToastUtils.showShort(getString(R.string.register_success));
                        finish();
                        ToolUtils.startActivity(SmsCodeActivity.this, LoginMobileActivity.class);
                    }

                    @Override
                    public void onError(int code) {
                        SmsCodeActivity.this.dismissProgress();
                    }
                }
        );
    }


    private void sendPswdSmsCodeNet() {
        model.sendPswdSmsCodeData(this, areaCode, inputPhone, "", "", new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("忘记密码--验证码发送成功返回数据----" + data);
                        sendCodeSuccess();
                    }

                    @Override
                    public void onError(int code) {
                        SmsCodeActivity.this.dismissProgress();
                    }
                }
        );
    }

    private void pswdForgetCheckCodeNet() {
        model.pswdForgetCheckCodeNet(this, smsCode, areaCode, inputPhone, "", new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("忘记密码--校验验证码--成功返回数据----" + data);
                        pswdForgetResetNet();
                    }

                    @Override
                    public void onError(int code) {
                        SmsCodeActivity.this.dismissProgress();
                    }
                }
        );
    }

    private void pswdForgetResetNet() {
        model.pswdForgetResetNet(this, smsCode, areaCode, inputPhone, inputPswd, new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("忘记密码--重置密码--成功返回数据----" + data);
                        ToastUtils.showShort(getString(R.string.pswd_reset_success));
                        finish();
                        ToolUtils.startActivity(SmsCodeActivity.this, LoginMobileActivity.class);
                    }

                    @Override
                    public void onError(int code) {
                        SmsCodeActivity.this.dismissProgress();
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
            mc = new MyCount(60000, 1000, this, btSmscodeSendcode);
        }
        mc.start();
        btSmscodeSendcode.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
