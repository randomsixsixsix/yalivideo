package com.video.yali.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.video.yali.R;


/**
 * 手机相册/相机选择的弹窗
 *
 * @author zh
 * 2019-03-23
 */
public class LoadingDialog extends Dialog {

    TextView tvLoading;
    private Context context;


    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading);
    }

    /**
     * 设置数据
     */
    public void setDialogTip(String tip) {
        if (!TextUtils.isEmpty(tip)) {
            tvLoading.setText(tip);
        }
    }
}
