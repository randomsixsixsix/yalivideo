package com.video.yali.base;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.R;
import com.video.yali.widget.dialog.LoadingDialog;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    LoadingDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    public abstract int getLayoutId();

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    public void showProgress() {
        if (myDialog == null) {
            myDialog = new LoadingDialog(this, R.style.MyDialog);
        }
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    public void dismissProgress() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }


    public void initImmersionBar() {
//        ImmersionBar.with(this)
//                .navigationBarColor(R.color.white)
//                .navigationBarDarkIcon(true)
//                .init();
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)     //状态栏颜色，不写默认透明色
                .statusBarDarkFont(false)  //状态栏字体是深色，不写默认为亮色
                .fitsSystemWindows(false)  //true解决状态栏和布局重叠问题
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgress();
    }
}
