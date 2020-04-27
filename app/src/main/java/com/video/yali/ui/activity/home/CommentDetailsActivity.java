package com.video.yali.ui.activity.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.video.yali.MyApplication;
import com.video.yali.R;
import com.video.yali.adapter.CommentAdapter;
import com.video.yali.adapter.CommentReplayAdapter;
import com.video.yali.adapter.VideoAboutAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.CommentBean;
import com.video.yali.bean.CommentListBean;
import com.video.yali.bean.CommentReplayBean;
import com.video.yali.model.VideoModel;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ScrollLinearLayoutManager;
import com.video.yali.utils.ToolUtils;
import com.video.yali.widget.dialog.CommentInputDialog;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class CommentDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.tv_commentdetails_name)
    TextView tvCommentdetailsName;
    @BindView(R.id.tv_commentdetails_date)
    TextView tvCommentdetailsDate;
    @BindView(R.id.tv_commentdetails_content)
    TextView tvCommentdetailsContent;
    @BindView(R.id.tv_commentdetails_zannumber)
    TextView tvCommentdetailsZannumber;
    @BindView(R.id.iv_commentdetails_head)
    ImageView ivCommentdetailsHead;
    @BindView(R.id.rv_commentdetails)
    RecyclerView mRecyclerView;
    private CommentBean mCommentBean;
    private CommentReplayAdapter mQuickAdapter;
    private Boolean isZan = false;
    private VideoModel model = new VideoModel();

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment_details;
    }

    @Override
    public void initView() {
        mCommentBean = getIntent().getParcelableExtra("commentDetails");

        tvTitleMiddle.setText(getString(R.string.comment_details));
        tvTitleMiddle.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new ScrollLinearLayoutManager(this));
        mQuickAdapter = new CommentReplayAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);


        initCommentInfo();
        commentDetailsNet();
    }

    private void initCommentInfo() {
        if (mCommentBean != null) {
            String headUrl = mCommentBean.getAvatar();
            try {
                Glide.with(MyApplication.getContext())
                        .load(headUrl)
                        .apply(centerCropTransform()
                                .placeholder(R.mipmap.default_header_comment)
                                .error(R.mipmap.default_header_comment)
                                .priority(Priority.HIGH)
                                .transform(new GlideCircleTransform())  //圆形头像,自定义类
                                .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                                .skipMemoryCache(false))     //跳过内存缓存
                        .into(ivCommentdetailsHead);
            } catch (Exception e) {

            }
            tvCommentdetailsName.setText(mCommentBean.getName());
            tvCommentdetailsContent.setText(mCommentBean.getContent());
            tvCommentdetailsDate.setText(mCommentBean.getTime_name());
            tvCommentdetailsZannumber.setText(mCommentBean.getPraise() + "");
            isZan = mCommentBean.isIs_praise();
            setZanStatue();

        }
    }

    private void setZanStatue() {
        if (isZan) {
            ToolUtils.setTextImage(this, tvCommentdetailsZannumber, R.mipmap.icon_comment_praise_active, 3);
        } else {
            ToolUtils.setTextImage(this, tvCommentdetailsZannumber, R.mipmap.icon_comment_praise, 3);
        }

    }

    @Override
    public void initListener() {
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                int replayId = commentReplayData.get(position).getId();
                showCommentInputPop(2, replayId);
            }
        });
    }

    @OnClick({R.id.ib_title_left, R.id.tv_commentdetails_replay, R.id.tv_commentdetails_zannumber})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_commentdetails_replay:
                showCommentInputPop(2, mCommentBean.getId());
                break;
            case R.id.tv_commentdetails_zannumber:
                if (isZan) {
                    ToastUtils.showShort(getString(R.string.video_zan_has_tip));
                } else {
                    commentZanNet();
                }
                break;
        }
    }

    private CommentInputDialog inputDialog;

    private void showCommentInputPop(int type, int replayId) {
        if (inputDialog == null) {
            inputDialog = new CommentInputDialog(this, R.style.MyDialog);
        }
        Window window = inputDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        //设置软键盘通常是可见的
        window.setSoftInputMode(params.SOFT_INPUT_STATE_VISIBLE);
        inputDialog.show();
        inputDialog.setOnOkClickListener(new CommentInputDialog.CommentOkClickListener() {
            @Override
            public void onOkClick(String commText) {
                if (type == 1) {       //一级评论，提交对影片的评论
                    commentPutNet(inputDialog, commText, 0, 0);
                } else {      //二级评论，提交对评论的评论
                    commentPutNet(inputDialog, commText, mCommentBean.getId(), replayId);
                }

            }
        });
    }

    public void commentPutNet(CommentInputDialog inputDialog, String commText, int firstCommentId, int targetCommentId) {
        model.commentPutData(this, mCommentBean.getMovie_id(), commText, firstCommentId, targetCommentId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                //评论成功
                if (inputDialog != null) {
                    inputDialog.clearInputContent();
                    inputDialog.dismiss();
                }
                commentDetailsNet();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private ArrayList<CommentReplayBean> commentReplayData;

    public void commentDetailsNet() {
        model.commentDetailsData(this, mCommentBean.getId(), mCommentBean.getMovie_id(), new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                CommentBean mCommentBean = gson.fromJson(data, CommentBean.class);
                if (mCommentBean != null) {
                    CommentDetailsActivity.this.mCommentBean = mCommentBean;
                    initCommentInfo();
                    if (mCommentBean.getCommentsLowerVOList() != null && mCommentBean.getCommentsLowerVOList().size() > 0) {
                        commentReplayData = mCommentBean.getCommentsLowerVOList();
                        mQuickAdapter.setData(commentReplayData);
                    }
                }

            }

            @Override
            public void onError(int code) {

            }
        });
    }

    public void commentZanNet() {
        model.commentZanData(this, mCommentBean.getId(), new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                isZan = !isZan;
                setZanStatue();
                mCommentBean.setPraise(mCommentBean.getPraise() + 1);
                tvCommentdetailsZannumber.setText(mCommentBean.getPraise() + "");
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
