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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.video.yali.R;
import com.video.yali.adapter.CommentAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.CommentBean;
import com.video.yali.model.VideoModel;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.widget.dialog.CommentInputDialog;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

@SuppressLint("ValidFragment")
public class CommentDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.tv_commvideo_title)
    TextView tvCommvideoTitle;
    @BindView(R.id.iv_commvideo_close)
    ImageView ivCommvideoClose;
    @BindView(R.id.rv_commvideo)
    RecyclerView mRecyclerView;
    @BindView(R.id.nsv_commvideo_content)
    NestedScrollView nsvCommvideoContent;
    @BindView(R.id.ll_commvideo_bottom)
    LinearLayout llCommvideoBottom;
    @BindView(R.id.rl_commvideo_empty)
    RelativeLayout rlCommvideoEmpty;

    private VideoModel model = new VideoModel();
    private CommentAdapter mQuickAdapter;
    private List<CommentBean> commentData;
    private BaseActivity activity;
    Unbinder unbinder;

    public CommentDialogFragment(BaseActivity activity, List<CommentBean> commentData) {
        this.activity = activity;
        this.commentData = commentData;
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
        View rootView = inflater.inflate(R.layout.fragment_comment_video, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ToolUtils.getScreenHeidth(getActivity()) * 2 / 3));
//        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT*2/3));
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mQuickAdapter = new CommentAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        setCommentData(commentData);

        mQuickAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_comment_item_zannumber) {
                    CommentBean mCommentBean = mQuickAdapter.getData().get(position);
                    commentZanNet(mCommentBean.getId(),position);
                }

            }
        });
    }

    private void commentZanNet(int commentId,int position) {
        model.commentZanData(activity, commentId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                mQuickAdapter.freshPositionData(position);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    public void setCommentData(List<CommentBean> commentData) {
        if (commentData == null || commentData.size() == 0) {
            rlCommvideoEmpty.setVisibility(View.VISIBLE);
            nsvCommvideoContent.setVisibility(View.GONE);
            tvCommvideoTitle.setText(R.string.comment_title_none);
        } else {
            rlCommvideoEmpty.setVisibility(View.GONE);
            nsvCommvideoContent.setVisibility(View.VISIBLE);
            mQuickAdapter.setData(commentData);
            String title = String.format(getString(R.string.comment_title_number), commentData.size() );
            nsvCommvideoContent.smoothScrollTo(0, 0);
            tvCommvideoTitle.setText(title);
            if (inputDialog!=null)
            if (inputDialog != null ) {
                inputDialog.clearInputContent();
                inputDialog.dismiss();
            }
        }
    }

    private CommentInputDialog inputDialog;

    private void showCommentInputPop() {
        if (inputDialog == null) {
            inputDialog = new CommentInputDialog(getActivity(), R.style.MyDialog);
        }
        Window window = inputDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //设置软键盘通常是可见的
        window.setSoftInputMode(params.SOFT_INPUT_STATE_VISIBLE);
        inputDialog.show();
        inputDialog.setOnOkClickListener(new CommentInputDialog.CommentOkClickListener() {
            @Override
            public void onOkClick(String commText) {
                //开始评论
//                ((VideoDetailsActivity) getActivity()).commentPutNet(inputDialog,commText);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismiss();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_commvideo_close, R.id.ll_commvideo_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_commvideo_close:
                dismiss();
                break;
            case R.id.ll_commvideo_bottom:
                showCommentInputPop();
                break;
        }
    }

    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int deletePosition, CommentBean mCommentBean);
    }
}
