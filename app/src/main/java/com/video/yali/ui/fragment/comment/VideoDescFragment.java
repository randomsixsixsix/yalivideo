package com.video.yali.ui.fragment.comment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.video.yali.R;
import com.video.yali.bean.CommentBean;
import com.video.yali.bean.VideoDetailsBean;
import com.video.yali.utils.ToolUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class VideoDescFragment extends BottomSheetDialogFragment {


    Unbinder unbinder;
    @BindView(R.id.tv_videodesc_title)
    TextView tvVideodescTitle;
    @BindView(R.id.tv_videodesc_close)
    ImageView tvVideodescClose;
    @BindView(R.id.tv_videodesc_number)
    TextView tvVideodescNumber;
    @BindView(R.id.tv_videodesc_label)
    TextView tvVideodescLabel;
    @BindView(R.id.tv_videodesc_desc)
    TextView tvVideodescDesc;
    private VideoDetailsBean mVideoDetailsBean;

    public VideoDescFragment(VideoDetailsBean mVideoDetailsBean) {
        this.mVideoDetailsBean = mVideoDetailsBean;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给dialog设置主题为透明背景 不然会有默认的白色背景
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme);

    }

    /**
     * 如果想要点击外部消失的话 重写此方法
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //设置点击外部可消失
        dialog.setCanceledOnTouchOutside(true);
        //设置使软键盘弹出的时候dialog不会被顶起
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        win.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);
        //这里设置dialog的进出动画
        win.setWindowAnimations(R.style.DialogBottomAnim);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 在这里将view的高度设置为精确高度，即可屏蔽向上滑动不占全屏的手势。
        //如果不设置高度的话 会默认向上滑动时dialog覆盖全屏
        View rootView = inflater.inflate(R.layout.fragment_video_desc, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ToolUtils.getScreenHeidth(getActivity()) * 1 / 2));
//        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT*2/3));
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        tvVideodescTitle.setText(mVideoDetailsBean.getName());
        tvVideodescNumber.setText(String.format(getString(R.string.video_play_number3),mVideoDetailsBean.getPlay_count()));
        tvVideodescLabel.setText(String.format(getString(R.string.video_play_label),""));
        tvVideodescDesc.setText(mVideoDetailsBean.getDesc());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismiss();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_videodesc_close)
    public void onClick() {
        dismiss();
    }
}
