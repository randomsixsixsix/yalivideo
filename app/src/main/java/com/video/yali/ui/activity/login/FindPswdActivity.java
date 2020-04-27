package com.video.yali.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPswdActivity extends BaseActivity {


    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_findpswd_nation)
    TextView tvFindpswdNation;
    @BindView(R.id.iv_findpswd_nationright)
    ImageView ivFindpswdNationright;
    @BindView(R.id.et_findpswd_phone)
    EditText etFindpswdPhone;
    @BindView(R.id.et_smscode_pswd)
    EditText etSmscodePswd;
    @BindView(R.id.iv_smscode_pswdlook)
    ImageView ivSmscodePswdlook;
    @BindView(R.id.ll_smscode_pswd)
    LinearLayout llSmscodePswd;
    @BindView(R.id.ll_smscode_pswdline)
    View llSmscodePswdline;
    @BindView(R.id.bt_findpswd_ok)
    Button btFindpswdOk;
    private LoginModel model = new LoginModel();


    private int areaCode = 86;
    public static int requestCode = 1003;
    private boolean isShowPswd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_pswd;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.ib_title_left, R.id.tv_findpswd_nation, R.id.iv_findpswd_nationright, R.id.iv_smscode_pswdlook,  R.id.bt_findpswd_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;

            case R.id.tv_findpswd_nation:
            case R.id.iv_findpswd_nationright:
                if (ClickUtils.isFastClick()) {
                    return;
                }
                startNationActivity();
                break;
            case R.id.iv_smscode_pswdlook:
                setShowPswd();

                break;
            case R.id.bt_findpswd_ok:
                String inputPhone = etFindpswdPhone.getText().toString().trim();
                if (TextUtils.isEmpty(inputPhone)) {
                    ToastUtils.showShort(getString(R.string.phone_empty_tip));
                    return;
                }
                String intputPswdnew = etSmscodePswd.getText().toString().trim();
                if (TextUtils.isEmpty(intputPswdnew) || intputPswdnew.length() < 6) {
                    ToastUtils.showShort(getString(R.string.pswd_empty_tip));
                    return;
                }
                sendPswdSmsCodeNet(inputPhone,intputPswdnew);
                break;
        }
    }

    private void setShowPswd() {
        isShowPswd = !isShowPswd;
        if (isShowPswd) {
            ivSmscodePswdlook.setImageResource(R.mipmap.psw_icon_hint);
            etSmscodePswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivSmscodePswdlook.setImageResource(R.mipmap.psw_icon_unhint);
            etSmscodePswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
            tvFindpswdNation.setText(nationName);
        }
    }

    private void sendPswdSmsCodeNet(String inputPhone,String inputPswd) {
        model.sendPswdSmsCodeData(this, areaCode, inputPhone, "", "", new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
                        LogUtils.e("忘记密码--验证码发送成功返回数据----" + data);
                        Intent mIntent = new Intent(FindPswdActivity.this, SmsCodeActivity.class);
                        mIntent.putExtra(SmsCodeActivity.activityTypeName, SmsCodeActivity.findpswdType);
                        mIntent.putExtra(SmsCodeActivity.activityAreaCode, areaCode);
                        mIntent.putExtra(SmsCodeActivity.activityInputPhone, inputPhone);
                        mIntent.putExtra(SmsCodeActivity.activityInputPswd, inputPswd);
                        startActivity(mIntent);
                    }

                    @Override
                    public void onError(int code) {
                        FindPswdActivity.this.dismissProgress();
                    }
                }
        );
    }
}
