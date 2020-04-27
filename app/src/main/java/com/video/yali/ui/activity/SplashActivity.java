package com.video.yali.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;


public class SplashActivity extends BaseActivity {


    private Dialog dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();

        showDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean isFirst = SPUtils.getInstance().getBoolean(GlobConstant.ISfIRST, true);
                if (isFirst) {
                    ActivityUtils.startActivity(Guide1Activity.class);
                } else {
                    ActivityUtils.startActivity(Guide2Activity.class);
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
                SplashActivity.this.finish();
            }
        }, 3000);

    }

    private void showDialog() {

        dialog = new Dialog(this, R.style.Dialog);
        dialog.setContentView(R.layout.dialog_splash);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
