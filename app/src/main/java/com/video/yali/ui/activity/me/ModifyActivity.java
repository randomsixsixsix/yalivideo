package com.video.yali.ui.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.model.SettingModel;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.YlUtils;
import com.video.yali.widget.RankPopWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyActivity extends BaseActivity {

    public static String modifyTypeName = "modifyType";
    public static int resultCode = 2003;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.et_modify_input)
    EditText etTitleInput;
    @BindView(R.id.bt_modify_ok)
    Button btModifyOk;
    private int modifyType;
    private SettingModel model = new SettingModel();

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify;
    }

    @Override
    public void initView() {
        modifyType = getIntent().getIntExtra(modifyTypeName, 1);
        tvTitleMiddle.setVisibility(View.VISIBLE);
        if (modifyType == 1) {
            tvTitleMiddle.setText(getString(R.string.personal_work));
        } else if (modifyType == 2) {
            tvTitleMiddle.setText(getString(R.string.personal_like));
        }

    }

    @OnClick({R.id.ib_title_left, R.id.bt_modify_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.bt_modify_ok:
                String inputContext=etTitleInput.getText().toString().trim();
                if (TextUtils.isEmpty(inputContext)){
                    ToastUtils.showShort(getString(R.string.please_modify));
                    return;
                }
                if (modifyType == 1) {
                    personalWorkNet(inputContext);
                } else if (modifyType == 2) {
                    tvTitleMiddle.setText(getString(R.string.personal_like));
                }
                break;
        }
    }

    private void personalWorkNet( String work) {
        model.personalWorkData(this, work, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                Intent mIntent = new Intent();
                mIntent.putExtra("modifyName",work);
                mIntent.putExtra("modifyType",modifyType);
                setResult(resultCode, mIntent);
                finish();
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
