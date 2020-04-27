package com.video.yali.ui.activity.login;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.UserBean;
import com.video.yali.event.OrangeEvent;
import com.video.yali.model.LoginModel;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.utils.ClickUtils;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginMobileActivity extends BaseActivity {
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.tv_loginmobile_nation)
    TextView tvLoginmobileNation;
    @BindView(R.id.iv_loginmobile_nationright)
    ImageView ivLoginmobileNationright;
    @BindView(R.id.et_loginmobile_phone)
    EditText etLoginmobilePhone;
    @BindView(R.id.et_loginmobile_pswd)
    EditText etLoginmobilePswd;
    @BindView(R.id.iv_loginmobile_pswdlook)
    ImageView ivLoginmobilePswdlook;
    @BindView(R.id.bt_loginmobile_ok)
    Button btLoginmobileOk;
    @BindView(R.id.tv_loginmobile_forget)
    TextView tvLoginmobileForget;
    @BindView(R.id.tv_loginmobile_register)
    TextView tvLoginmobileRegister;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_loginmobile_phonecode)
    TextView tvLoginmobilePhonecode;
    private int areaCode = 86;
    public static int requestCode = 1001;
    private boolean isShowPswd;
    private LoginModel model = new LoginModel();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_mobile;
    }

    @Override
    public void initView() {
//        tvTitleRight.setVisibility(View.VISIBLE);
//        tvTitleRight.setText(getString(R.string.login_name));
//        etLoginmobilePhone.setText("15736778888");
//        etLoginmobilePswd.setText("123456");

//        etLoginmobilePhone.setText("15136881378");
//        etLoginmobilePswd.setText("123qwe");

//        etLoginmobilePhone.setText("15736779727");
//        etLoginmobilePswd.setText("123456");

//        etLoginmobilePhone.setText("15736778888");
//        etLoginmobilePswd.setText("123456");
        String modeInfo = ToolUtils.getModelInfo();

    }

    @OnClick({R.id.ib_title_left, R.id.tv_loginmobile_nation, R.id.iv_loginmobile_nationright, R.id.iv_loginmobile_pswdlook, R.id.bt_loginmobile_ok,
            R.id.tv_loginmobile_agree, R.id.tv_loginmobile_forget, R.id.tv_loginmobile_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_loginmobile_nation:
            case R.id.iv_loginmobile_nationright:
                if (ClickUtils.isFastClick()) {
                    return;
                }
                startNationActivity();
                break;
            case R.id.iv_loginmobile_pswdlook:      //显示关闭--密码文字
                setShowPswd();
                break;
            case R.id.bt_loginmobile_ok:
                String inputPhone = etLoginmobilePhone.getText().toString().trim();
                String inputPswd = etLoginmobilePswd.getText().toString().trim();
                if (TextUtils.isEmpty(inputPhone)) {
                    ToastUtils.showShort(getString(R.string.phone_empty_tip));
                    return;
                }
                if (TextUtils.isEmpty(inputPswd) || inputPswd.length() < 6) {
                    ToastUtils.showShort(getString(R.string.pswd_empty_tip));
                    return;
                }
                loginNet(inputPhone, inputPswd);
                break;
            case R.id.tv_loginmobile_agree:
                Intent webIntent = new Intent(this, WebActivity.class);
                webIntent.putExtra("webType", WebActivity.WebAgree);
                startActivity(webIntent);
                break;
            case R.id.tv_loginmobile_forget:
                ToolUtils.startActivity(this, FindPswdActivity.class);
                break;
            case R.id.tv_loginmobile_register:
                ToolUtils.startActivity(this, RegisterActivity.class);
                break;
        }
    }

    private void loginNet(String inputPhone, String inputPswd) {
        model.loginMobileData(this, areaCode, inputPhone, inputPswd, new RequestCallback() {
                    @Override
                    public void onSuccess(String data) {
//                       //解析数据成集合
//                        Type listType = new TypeToken<ArrayList<ServiceBean>>() {}.getType(); // TypeToken是一个空的抽象类
//                        Gson gson = new Gson();
//                        ArrayList<ServiceBean> list = gson.fromJson(data, listType);
                        Gson gson = new Gson();
                        UserBean mUserBean = gson.fromJson(data, UserBean.class);
                        YlUtils.saveUserInfo(mUserBean);
                        //登陆成功后刷新用户信息
                        //发布事件(获取下单的通知)
                        EventBus.getDefault().post(new OrangeEvent(ConstantUtils.EVENT_FRESHUSERINFO));
                        finish();
                    }

                    @Override
                    public void onError(int code) {

                    }
                }
        );
    }


    private void setShowPswd() {
        isShowPswd = !isShowPswd;
        if (isShowPswd) {
            ivLoginmobilePswdlook.setImageResource(R.mipmap.psw_icon_hint);
            etLoginmobilePswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            ivLoginmobilePswdlook.setImageResource(R.mipmap.psw_icon_unhint);
            etLoginmobilePswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
            tvLoginmobileNation.setText(nationName);
            tvLoginmobilePhonecode.setText("+" + areaCode);
        }
    }


}
