package com.video.yali.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;


/**
 * 自定义退出弹出框
 *
 * @author qinfan
 * <p>
 * 2015-11-6
 */
public class MyDialog extends Dialog {

    @BindView(R.id.iv_mydialog_descimage)
    ImageView ivMydialogDescimage;
    @BindView(R.id.ll_mydialog_descimage)
    LinearLayout llMydialogDescimage;
    private Context context;
    @BindView(R.id.view_mydialog_line)
    View viewMydialogLine;
    @BindView(R.id.tv_mydialog_title)
    TextView tvMydialogTitle;
    @BindView(R.id.tv_mydialog_desc)
    TextView tvMydialogDesc;
    @BindView(R.id.ll_mydialog_two)
    LinearLayout llMydialogTwo;
    @BindView(R.id.tv_mydialog_cancel)
    TextView tvMydialogCancel;
    @BindView(R.id.tv_mydialog_confirm)
    TextView tvMydialogConfirm;
    @BindView(R.id.tv_mydialog_ok)
    TextView tvMydialogOk;


    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_my);
        ButterKnife.bind(this);

    }

    /**
     * 设置数据颜色
     */
    public void setDataColor(int titleColor, int descColor, int cancelColor, int confirmColor) {
        if (titleColor > 0) {
            tvMydialogTitle.setTextColor(context.getResources().getColor(titleColor));
        }
        if (descColor > 0) {
            tvMydialogDesc.setTextColor(context.getResources().getColor(descColor));
        }
        if (cancelColor > 0) {
            tvMydialogCancel.setTextColor(context.getResources().getColor(cancelColor));
        }
        if (confirmColor > 0) {
            tvMydialogConfirm.setTextColor(context.getResources().getColor(confirmColor));
        }
    }


    /**
     * 设置数据
     */
    public void setDialogData(String title, CharSequence desc, String cancel, String confirm, String ok) {
        if (TextUtils.isDigitsOnly(title)) {
            tvMydialogTitle.setVisibility(View.GONE);
            viewMydialogLine.setVisibility(View.GONE);
        } else {
            tvMydialogTitle.setVisibility(View.VISIBLE);
            viewMydialogLine.setVisibility(View.VISIBLE);
            tvMydialogTitle.setText(title);
        }
        tvMydialogDesc.setText(desc);
        if (!TextUtils.isEmpty(ok)) {
            tvMydialogOk.setText(ok);
            tvMydialogOk.setVisibility(View.VISIBLE);
            llMydialogTwo.setVisibility(View.GONE);
        } else {
            llMydialogTwo.setVisibility(View.VISIBLE);
            tvMydialogOk.setVisibility(View.GONE);
            tvMydialogCancel.setText(cancel);
            tvMydialogConfirm.setText(confirm);
        }

    }

    /**
     * 设置数据
     */
    public void setDialogImage(String descImageUrl) {
        if (TextUtils.isEmpty(descImageUrl)) {
            llMydialogDescimage.setVisibility(View.GONE);
        }else{
            llMydialogDescimage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(descImageUrl)
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.default_cover_xhsp)
                            .error(R.drawable.default_cover_xhsp)
                            //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .skipMemoryCache(false))
                    .into(ivMydialogDescimage);
        }

    }

    /**
     * 点击按钮监听
     */
    public void setOnDialogClickListener(final MyDialogClickListener listener) {
        if (listener != null) {
            tvMydialogCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCancelClick();
                }
            });
            tvMydialogConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onConfirmClick();
                }
            });

            tvMydialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onOkClick();
                }
            });
        }
    }

    public interface MyDialogClickListener {
        void onCancelClick();

        void onConfirmClick();

        void onOkClick();

    }
}
