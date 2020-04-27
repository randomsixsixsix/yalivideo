package com.video.yali.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.video.yali.R;
import com.video.yali.listener.SoftKeyBoardListener;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CommentInputDialog extends Dialog {
    @BindView(R.id.et_comminput_input)
    EditText etComminputInput;
    @BindView(R.id.bt_comminput_ok)
    Button btComminputOk;
    private Activity activity;

    public CommentInputDialog(Activity activity, int theme) {
        super(activity, theme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_comment_input);
        ButterKnife.bind(this);
        init();
//        showInputMethod();
        setListener();
    }

    public void init() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawableResource(android.R.color.white);

    }

    private void setListener() {
        //为EditText设置监听，注意监听类型为TextWatcher
        etComminputInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    btComminputOk.setEnabled(true);
                    btComminputOk.setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    btComminputOk.setEnabled(false);
                    btComminputOk.setTextColor(activity.getResources().getColor(R.color.col_primary_line));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @SuppressLint("WrongConstant")
    public void showInputMethod() {
        etComminputInput.setFocusable(true);
        etComminputInput.setFocusableInTouchMode(true);
        etComminputInput.requestFocus();
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(800, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 点击按钮监听
     */
    public void setOnOkClickListener(final CommentInputDialog.CommentOkClickListener listener) {
        if (listener != null) {
            btComminputOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String commText = etComminputInput.getText().toString().trim();
                    if (!TextUtils.isEmpty(commText)) {
                        listener.onOkClick(commText);
                    }
                }
            });
        }
    }

    public void clearInputContent() {
        etComminputInput.setText("");
    }

    public interface CommentOkClickListener {
        void onOkClick(String commText);
    }

    @Override
    public void dismiss() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(etComminputInput,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(etComminputInput.getWindowToken(), 0); //强制隐藏键盘
        super.dismiss();
    }
}
