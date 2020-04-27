package com.video.yali.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ToastUtils;
import com.video.yali.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 头像弹框
 *
 * @author qinfan
 * <p>
 * 2015-11-6
 */
public class NickDialog extends Dialog {


    @BindView(R.id.et_nickdialog_name)
    EditText etNickdialogName;
    @BindView(R.id.et_nickdialog_number)
    TextView etNickdialogNumber;
    @BindView(R.id.sb_nickdialog_cancel)
    SuperButton sbNickdialogCancel;
    @BindView(R.id.sb_nickdialog_confir)
    SuperButton sbNickdialogConfir;
    private Context context;


    public NickDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_nick);
        ButterKnife.bind(this);
        etNickdialogName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    etNickdialogNumber.setText((7 - s.length()) + "/7");
                } else {
                    etNickdialogNumber.setText("0/7");
                }

            }
        });

        sbNickdialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    /**
     * 点击按钮监听
     */
    public void setOnDialogClickListener(final MyDialogClickListener listener) {
        if (listener != null) {
            sbNickdialogConfir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputNick = etNickdialogName.getText().toString().trim();
                    if (TextUtils.isEmpty(inputNick)) {
                        ToastUtils.showShort(context.getString(R.string.please_input_nick));
                        return;
                    }
                    listener.onConfirmClick(inputNick);
                }
            });

        }
    }

    public interface MyDialogClickListener {

        void onConfirmClick(String inputNick);

    }
}
