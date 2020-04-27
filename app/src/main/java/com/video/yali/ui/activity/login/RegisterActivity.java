package com.video.yali.ui.activity.login;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.model.LoginModel;
import com.video.yali.utils.ClickUtils;
import com.video.yali.utils.RequestCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_register_nation)
    TextView tvRegisterNation;
    @BindView(R.id.iv_register_nationright)
    ImageView ivRegisterNationright;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_pswd)
    EditText etRegisterPswd;
    @BindView(R.id.iv_register_pswdlook)
    ImageView ivRegisterPswdlook;
    @BindView(R.id.et_register_invitecode)
    EditText etRegisterInvitecode;
    @BindView(R.id.bt_register_ok)
    Button btRegisterOk;


    private int areaCode = 86;
    public static int requestCode = 1002;
    private boolean isShowPswd;
    private LoginModel model = new LoginModel();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {

    }

    @OnClick({R.id.ib_title_left, R.id.tv_register_nation, R.id.iv_register_nationright, R.id.iv_register_pswdlook, R.id.bt_register_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_register_nation:
            case R.id.iv_register_nationright:
                if (ClickUtils.isFastClick()) {
                    return;
                }
                startNationActivity();
                break;
            case R.id.iv_register_pswdlook:
                setShowPswd();
                break;
            case R.id.bt_register_ok:
                String inputPhone = etRegisterPhone.getText().toString().trim();
                String inputPswd = etRegisterPswd.getText().toString().trim();
                String inputInvite = etRegisterInvitecode.getText().toString().trim();
                if (TextUtils.isEmpty(inputPhone)) {
                    ToastUtils.showShort(getString(R.string.phone_empty_tip));
                    return;
                }
                if (TextUtils.isEmpty(inputPswd) || inputPswd.length() < 6) {
                    ToastUtils.showShort(getString(R.string.pswd_empty_tip));
                    return;
                }
                sendSmsCodeNet(inputPhone,inputPswd,inputInvite);
                break;
        }
    }

    private void setShowPswd() {
        isShowPswd = !isShowPswd;
        if (isShowPswd) {
            ivRegisterPswdlook.setImageResource(R.mipmap.psw_icon_hint);
            etRegisterPswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivRegisterPswdlook.setImageResource(R.mipmap.psw_icon_unhint);
            etRegisterPswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }

    private void startNationActivity() {
        Intent mintent = new Intent(this, NationActivity.class);
        startActivityForResult(mintent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && resultCode == NationActivity.resultCode) {
            String nationName = data.getStringExtra("nationName");
            areaCode = data.getIntExtra("nationCode", 86);
            tvRegisterNation.setText(nationName);
        }
    }

    private void sendSmsCodeNet(String inputPhone,String inputPswd,String inputInvite) {
        model.sendRegisterSmsCodeData(this, areaCode, inputPhone, inputPswd, inputInvite, new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("返回数据----" + data);
                        Intent mIntent = new Intent(RegisterActivity.this, SmsCodeActivity.class);
                        mIntent.putExtra(SmsCodeActivity.activityTypeName, SmsCodeActivity.registerType);
                        mIntent.putExtra(SmsCodeActivity.activityAreaCode, areaCode);
                        mIntent.putExtra(SmsCodeActivity.activityInputPhone, inputPhone);
                        mIntent.putExtra(SmsCodeActivity.activityInputPswd, inputPswd);
                        mIntent.putExtra(SmsCodeActivity.activityInputInvite, inputInvite);
                        startActivity(mIntent);
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }
}
