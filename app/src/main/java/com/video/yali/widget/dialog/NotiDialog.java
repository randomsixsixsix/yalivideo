package com.video.yali.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;


/**
 * 主页面消息弹框
 *
 * @author qinfan
 * <p>
 * 2015-11-6
 */
public class NotiDialog extends Dialog {


    private Context context;
    @BindView(R.id.view_notidialog_line)
    View viewMydialogLine;
    @BindView(R.id.tv_notidialog_title)
    TextView tvMydialogTitle;
    @BindView(R.id.tv_notidialog_desc)
    TextView tvMydialogDesc;
    @BindView(R.id.tv_notidialog_ok)
    TextView tvMydialogOk;


    public NotiDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_noti);
        ButterKnife.bind(this);

    }


    /**
     * 设置数据
     */
    public void setDialogData(String title, CharSequence desc, String ok) {
        if (TextUtils.isDigitsOnly(title)) {
            tvMydialogTitle.setVisibility(View.GONE);
            viewMydialogLine.setVisibility(View.GONE);
        } else {
            tvMydialogTitle.setVisibility(View.VISIBLE);
            viewMydialogLine.setVisibility(View.VISIBLE);
            tvMydialogTitle.setText(title);
        }
        tvMydialogDesc.setText(desc);

    }


    /**
     * 点击按钮监听
     */
    public void setOnDialogClickListener(final MyDialogClickListener listener) {
        if (listener != null) {
            tvMydialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onOkClick();
                }
            });
        }
    }

    public interface MyDialogClickListener {

        void onOkClick();

    }
}
