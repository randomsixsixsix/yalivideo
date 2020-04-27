package com.video.yali.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.allen.library.SuperButton;
import com.video.yali.R;

/* 定义一个倒计时的内部类 */
public class MyCount extends CountDownTimer {

    private Context mContext;
    private Button sbGetsms;

    public MyCount(long millisInFuture, long countDownInterval, Context mContext, Button sbGetsms) {
        super(millisInFuture, countDownInterval);
        this.mContext = mContext;
        this.sbGetsms = sbGetsms;
    }

    @Override
    public void onFinish() {
       String againCodeString=mContext.getResources().getString(R.string.login_code_again_get);  //再次获取验证码
        if (sbGetsms!=null) {
            sbGetsms.setText(againCodeString);
            sbGetsms.setEnabled(true);
            sbGetsms.setTextColor(mContext.getResources().getColor(R.color.col_text1));//字体主题色
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
//        Timber.tag(MyApplication.tag).i("开始计时"+millisUntilFinished);
        String anewCodeString=mContext.getResources().getString(R.string.login_code_anew_time); //重新发送
        String contact = String.format(anewCodeString, millisUntilFinished / 1000);
        if (sbGetsms!=null) {
            sbGetsms.setText(contact);
            sbGetsms.setEnabled(false);
            sbGetsms.setTextColor(mContext.getResources().getColor(R.color.col_text3)); //字体黑灰色
        }
    }
}
