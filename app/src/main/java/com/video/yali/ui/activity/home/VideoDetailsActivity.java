package com.video.yali.ui.activity.home;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.video.yali.GlobConstant;
import com.video.yali.MyApplication;
import com.video.yali.R;
import com.video.yali.adapter.CommentAdapter;
import com.video.yali.adapter.VideoAboutAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.CommentBean;
import com.video.yali.bean.CommentListBean;
import com.video.yali.bean.UserBean;
import com.video.yali.bean.VideoAboutListBean;
import com.video.yali.bean.AdBean;
import com.video.yali.bean.VideoDetailsBean;
import com.video.yali.download.VideoModel;
import com.video.yali.download.LogDownloadListener;
import com.video.yali.download.MyDownloadActivity;
import com.video.yali.ui.activity.MainActivity;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.ui.activity.me.PromoteActivity;
import com.video.yali.ui.fragment.comment.VideoDescFragment;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ScrollLinearLayoutManager;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;
import com.video.yali.widget.dialog.CommentInputDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class VideoDetailsActivity extends BaseActivity {

    SuperPlayerView mSuperPlayerView;
    @BindView(R.id.fl_videodetails_video1)
    FrameLayout flVideodetailsVideo1;
    @BindView(R.id.fl_videodetails_video2)
    FrameLayout flVideodetailsVideo2;
    @BindView(R.id.iv_videodetails_ad)
    ImageView ivVideodetailsAd;
    @BindView(R.id.rl_videodetails_promote)
    RelativeLayout rlVideodetailsPromote;
    @BindView(R.id.iv_videodetails_playbg)
    ImageView ivVideodetailsPlaybg;
    @BindView(R.id.iv_videodetails_playlogo)
    ImageView ivVideodetailsPlaylogo;
    @BindView(R.id.nsv_videodetails)
    NestedScrollView nsvVideodetails;
    @BindView(R.id.tv_videodetails_playnumber)
    TextView tvVideodetailsPlaynumber;
    @BindView(R.id.tv_videodetails_playnumberdesc)
    TextView tvVideodetailsPlaynumberdesc;
    @BindView(R.id.iv_videodetails_promote)
    ImageView ivVideodetailsPromote;
    @BindView(R.id.tv_videodetails_title)
    TextView tvVideodetailsTitle;
    @BindView(R.id.tv_videodetails_score)
    TextView tvVideodetailsScore;
    @BindView(R.id.tv_videodetails_desc)
    TextView tvVideodetailsDesc;
    @BindView(R.id.tv_videodetails_comment)
    TextView tvVideodetailsComment;
    @BindView(R.id.tv_videodetails_opinion)
    TextView tvVideodetailsOpinion;
    @BindView(R.id.iv_videodetails_zanup)
    ImageView ivVideodetailsZanup;
    @BindView(R.id.iv_videodetails_zandown)
    ImageView ivVideodetailsZandown;
    @BindView(R.id.rv_videodetails_like)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_videodetails_inputpic)
    ImageView ivVideodetailsInputpic;
    @BindView(R.id.tv_videodetails_input)
    TextView tvVideodetailsInput;
    @BindView(R.id.iv_videodetails_like)
    ImageView ivVideodetailsLike;
    @BindView(R.id.iv_videodetails_down)
    ImageView ivVideodetailsDown;
    @BindView(R.id.iv_videodetails_share)
    ImageView ivVideodetailsShare;
    @BindView(R.id.ll_videodetails_bottom)
    LinearLayout llVideodetailsBottom;
    @BindView(R.id.tv_videodetails_none)
    TextView tvVideodetailsNone;
    @BindView(R.id.tv_videodetails_commenthot)
    TextView tvVideodetailsCommenthot;
    @BindView(R.id.tv_videodetails_commentnew)
    TextView tvVideodetailsCommentnew;
    @BindView(R.id.rv_videodetails_comment)
    RecyclerView mRecyclerView2;
    @BindView(R.id.tv_videodetails_detailstitle)
    TextView tvVideodetailsDetailstitle;
    @BindView(R.id.view_videodetails_detailsline)
    View viewVideodetailsDetailsline;
    @BindView(R.id.ll_videodetails_detailstitle)
    LinearLayout llVideodetailsDetailstitle;
    @BindView(R.id.tv_videodetails_commenttitle)
    TextView tvVideodetailsCommenttitle;
    @BindView(R.id.view_videodetails_commentline)
    View viewVideodetailsCommentline;
    @BindView(R.id.ll_videodetails_commenttitle)
    LinearLayout llVideodetailsCommenttitle;
    @BindView(R.id.ll_videodetails_title)
    LinearLayout llVideodetailsTitle;
    @BindView(R.id.tv_videodetails_about)
    TextView tvVideodetailsAbout;
    @BindView(R.id.ll_videodetails_comment)
    LinearLayout llVideodetailsComment;
    @BindView(R.id.iv_videodetails_stopline)
    ImageView ivVideodetailsStopline;
    private com.video.yali.model.VideoModel model = new com.video.yali.model.VideoModel();
    private VideoDetailsBean mVideoDetailsBean;
    private int videoType;
    private int videoId;
    private Boolean isLike = false;
    private VideoAboutAdapter mQuickAdapter;
    private CommentAdapter mQuickAdapter2;
    private int commentType = 1;
    private MyOrientoinListener myOrientoinListener;
    private String download_url;//下载地址
    private String videoname;
    private String videocover;
    private String videoPath;
    private AdBean mAdBean;
    private UserBean userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_details;
    }

    @Override
    public void initView() {
        videoType = getIntent().getIntExtra(GlobConstant.VIDEOTYPE, 0);
        videoId = getIntent().getIntExtra(GlobConstant.VIDEOID, 0);
        videoPath = getIntent().getStringExtra(GlobConstant.VIDEOPATH);
        mSuperPlayerView = new SuperPlayerView(this);
        mRecyclerView.setLayoutManager(new ScrollLinearLayoutManager(this));
        mQuickAdapter = new VideoAboutAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        mRecyclerView2.setLayoutManager(new ScrollLinearLayoutManager(this));
        mQuickAdapter2 = new CommentAdapter(mRecyclerView2);
        mRecyclerView2.setAdapter(mQuickAdapter2);

        getVideoInfoNet();
        getVideoAboutNet();
        commentListNet();
        videoAdNet();
        userInfo = YlUtils.getUserInfo();

        if (YlUtils.judgeUserExist()) {
            rlVideodetailsPromote.setVisibility(View.GONE);
            UserBean mUserBean = YlUtils.getUserInfo();
            Glide.with(this)
                    .load(mUserBean.getAvatar())
                    .apply(centerCropTransform()
                            .placeholder(R.mipmap.icon_comment_input_person)
                            .error(R.mipmap.icon_comment_input_person)
                            .priority(Priority.HIGH)
                            .transform(new GlideCircleTransform())  //圆形头像,自定义类
                            .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                            .skipMemoryCache(false))     //跳过内存缓存
                    .into(ivVideodetailsInputpic);
        } else {
            rlVideodetailsPromote.setVisibility(View.VISIBLE);
            UserBean mUserBean = YlUtils.getUserInfo();
            if (mUserBean != null) {
                int remainNumber = mUserBean.getView_count() - mUserBean.getViewed_count();
                tvVideodetailsPlaynumber.setText(String.format(getString(R.string.video_play_number), remainNumber));
            } else {
                tvVideodetailsPlaynumber.setText(String.format(getString(R.string.video_play_number), 0));
            }
        }

        myOrientoinListener = new MyOrientoinListener(this);
        boolean autoRotateOn = (android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        //检查系统是否开启自动旋转
        if (autoRotateOn) {
            myOrientoinListener.enable();
        }

    }


    private void getVideoInfoNet() {
        model.getVideoInfoData(this, videoId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                mVideoDetailsBean = gson.fromJson(data, VideoDetailsBean.class);
                initVideoData();

            }

            @Override
            public void onError(int code) {
                nsvVideodetails.setVisibility(View.GONE);
                llVideodetailsBottom.setVisibility(View.GONE);
                tvVideodetailsNone.setVisibility(View.VISIBLE);

            }
        });

    }

    private void getVideoAboutNet() {
        model.getVideoAboutData(this, videoId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                VideoAboutListBean mVideoAboutListBean = gson.fromJson(data, VideoAboutListBean.class);
                ArrayList<VideoDetailsBean> aboutList = mVideoAboutListBean.getList();
                mQuickAdapter.setData(aboutList);
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    //初始化视频数据
    private void initVideoData() {

        download_url = mVideoDetailsBean.getDownload_url();
        videoname = mVideoDetailsBean.getName();
        videocover = mVideoDetailsBean.getPoster();
        //视频开始播放
        String thumbImageUrl = mVideoDetailsBean.getPoster();
        Glide.with(this)
                .load(thumbImageUrl)
                .into(ivVideodetailsPlaybg);
        Glide.with(this)
                .load(thumbImageUrl)
                .into(mSuperPlayerView.getSmallCoverPicView());
        isLike = mVideoDetailsBean.isHas_praise();
        setLikeStatue();

        tvVideodetailsTitle.setText(mVideoDetailsBean.getName());
        tvVideodetailsScore.setText(mVideoDetailsBean.getScore() + "    " + mVideoDetailsBean.getPlay_count() + getString(R.string.video_play_number2) + "    " + mVideoDetailsBean.getPublished_at());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initListener() {
        if (mSuperPlayerView != null) {
            mSuperPlayerView.setPlayerViewCallback(new SuperPlayerView.OnSuperPlayerViewCallback() {

                @Override
                public void onStartFullScreenPlay() {      //开始全屏播放
                    flVideodetailsVideo2.removeAllViews();
                    flVideodetailsVideo1.addView(mSuperPlayerView);
                    nsvVideodetails.setVisibility(View.GONE);
                    llVideodetailsBottom.setVisibility(View.GONE);
                    llVideodetailsTitle.setVisibility(View.GONE);

                }

                @Override
                public void onStopFullScreenPlay() {      //结束全屏播放
                    flVideodetailsVideo1.removeAllViews();
                    flVideodetailsVideo2.addView(mSuperPlayerView);
                    nsvVideodetails.setVisibility(View.VISIBLE);
                    llVideodetailsBottom.setVisibility(View.VISIBLE);
                    llVideodetailsTitle.setVisibility(View.VISIBLE);

                }

                @Override
                public void onClickSmallReturnBtn() {      //点击小播放模式的返回按钮
                    finish();
                }


            });
        }

        //发表弹幕
        mSuperPlayerView.setViewClickListener(new SuperPlayerView.ViewClickListener() {
            @Override
            public void onCommentClick() {
                showCommentInputPop(3, null);

            }
        });

        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                VideoDetailsBean aboutItem = mQuickAdapter.getData().get(position);
                Intent mIntent = new Intent(VideoDetailsActivity.this, VideoDetailsActivity.class);
                mIntent.putExtra("videoId", aboutItem.getId());
                startActivity(mIntent);
            }
        });

        mQuickAdapter2.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                CommentBean mCommentBean = commentData.get(position);
                if (childView.getId() == R.id.rl_comment_item_head_all) {        //查看评论详情
                    if (!YlUtils.judgeUserExist()) {
                        YlUtils.loginUser(VideoDetailsActivity.this);
                        return;
                    }
//                    showCommentInputPop(2,mCommentBean);
                    startCommentActivity(mCommentBean);
                } else if (childView.getId() == R.id.tv_comment_item_zannumber) {    //对评论进行点赞
                    if (!YlUtils.judgeUserExist()) {
                        YlUtils.loginUser(VideoDetailsActivity.this);
                        return;
                    }
                    if (mCommentBean.isIs_praise()) {
                        ToastUtils.showShort(getString(R.string.video_zan_has_tip));
                    } else {
                        commentZanNet(mCommentBean.getId(), position);
                    }
                }

            }
        });
        nsvVideodetails.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LogUtils.e("详情控件位置:" + getStopLineY(tvVideodetailsAbout) + "==评论控件位置:" + getStopLineY(llVideodetailsComment) + "==线的位置:" + getStopLineY(ivVideodetailsStopline));
                int lineY = getStopLineY(ivVideodetailsStopline) + 100;
                if (getStopLineY(tvVideodetailsAbout) < lineY && getStopLineY(llVideodetailsComment) > lineY) {
                    if (scrollStatue != 2) {
                        if (scrollStatue == 1) {
                            showView();
                        }
                        scrollStatue = 2;
                        tvVideodetailsDetailstitle.setTextColor(getResources().getColor(R.color.app_theme));
                        viewVideodetailsDetailsline.setBackgroundResource(R.drawable.yellow_cir3);
                        tvVideodetailsCommenttitle.setTextColor(getResources().getColor(R.color.white));
                        viewVideodetailsCommentline.setBackgroundResource(R.drawable.gray_cir4);
                        viewVideodetailsDetailsline.setVisibility(View.VISIBLE);
                        viewVideodetailsCommentline.setVisibility(View.GONE);
                    }
                } else if (getStopLineY(llVideodetailsComment) <= lineY) {
                    if (scrollStatue != 3) {
                        scrollStatue = 3;
                        tvVideodetailsDetailstitle.setTextColor(getResources().getColor(R.color.white));
                        viewVideodetailsDetailsline.setBackgroundResource(R.drawable.gray_cir4);
                        tvVideodetailsCommenttitle.setTextColor(getResources().getColor(R.color.app_theme));
                        viewVideodetailsCommentline.setBackgroundResource(R.drawable.yellow_cir3);
                        viewVideodetailsDetailsline.setVisibility(View.GONE);
                        viewVideodetailsCommentline.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (scrollStatue != 1) {
                        if (scrollStatue == 2 || scrollStatue == 3) {
                            scrollStatue = 1;
                            hiddView();
                        }
                    }
                }

            }
        });

    }

    private void hiddView() {
        Animation animBottomOut = AnimationUtils.loadAnimation(this,
                R.anim.pop_menuhide);
        animBottomOut.setDuration(300);
        llVideodetailsTitle.setVisibility(View.GONE);
        llVideodetailsTitle.startAnimation(animBottomOut);
    }

    private void showView() {
        Animation animBottomOut = AnimationUtils.loadAnimation(this,
                R.anim.pop_menushow);
        animBottomOut.setDuration(300);
        llVideodetailsTitle.setVisibility(View.VISIBLE);
        llVideodetailsTitle.startAnimation(animBottomOut);
    }


    private int scrollStatue = 1;

    private int getStopLineY(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return y;
    }


    private void startCommentActivity(CommentBean mCommentBean) {
        Intent intent = new Intent(VideoDetailsActivity.this, CommentDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("commentDetails", mCommentBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView != null && mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PAUSE) {
            mSuperPlayerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSuperPlayerView != null && mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            mSuperPlayerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSuperPlayerView != null) {
            mSuperPlayerView.resetPlayer();
            mSuperPlayerView.release();
            mSuperPlayerView = null;
        }
        //销毁时取消监听
        if (myOrientoinListener != null) {
            myOrientoinListener.disable();
        }
//        if (videoType == ConstantUtils.adtype_guide){
//            ToolUtils.startActivity(this, MainActivity.class);
//        }
    }


    @OnClick({R.id.iv_videodetails_playlogo, R.id.iv_videodetails_ad, R.id.iv_videodetails_promote, R.id.tv_videodetails_desc, R.id.tv_videodetails_comment, R.id.tv_videodetails_opinion,
            R.id.iv_videodetails_zanup, R.id.iv_videodetails_zandown, R.id.iv_videodetails_inputpic, R.id.tv_videodetails_input, R.id.iv_videodetails_like, R.id.iv_videodetails_down,
            R.id.iv_videodetails_share, R.id.tv_videodetails_commenthot, R.id.tv_videodetails_commentnew})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_videodetails_playlogo:
                ivVideodetailsPlaybg.setVisibility(View.GONE);
                ivVideodetailsPlaylogo.setVisibility(View.GONE);
                flVideodetailsVideo2.addView(mSuperPlayerView);
//                String videoUrl = "http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/e1ab85305285890781763144364/v.f20.mp4";
                if (videoType == ConstantUtils.video_type_down) {     //本地播放
                    LogUtils.e("本地播放地址---" + videoPath);
                    SuperPlayerModel superPlayerModel = new SuperPlayerModel();
                    superPlayerModel.url = videoPath;
                    mSuperPlayerView.playWithModel(superPlayerModel);

                } else {          //网络播放
                    UserBean mUserBean = YlUtils.getUserInfo();
                    if (mUserBean != null && mUserBean.getView_count() - mUserBean.getViewed_count() > 0) {
                        String videoUrl = mVideoDetailsBean.getPlay_url();
                        LogUtils.e("网络播放地址---" + videoUrl);
                        SuperPlayerModel superPlayerModel = new SuperPlayerModel();
                        superPlayerModel.url = videoUrl;
                        superPlayerModel.title = mVideoDetailsBean.getName();
                        mSuperPlayerView.playWithModel(superPlayerModel);
                        videoAddHistoryNet();
                    } else {
                        ToastUtils.showShort(getString(R.string.video_play_nonumber));
                    }
                }

                break;
            case R.id.iv_videodetails_ad:
                if (mAdBean != null) {
                    YlUtils.startAdDetails(this, ConstantUtils.adtype_video, mAdBean.getType(), mAdBean.getTarget_url(), mAdBean.getId());
                }
                break;
            case R.id.iv_videodetails_promote:
                ToolUtils.startActivity(this, PromoteActivity.class);
                break;
            case R.id.tv_videodetails_desc:     //简介
                if (videoType != ConstantUtils.video_type_down) {     //本地播放
                    showDialogFragmentDesc();
                }
                break;
            case R.id.iv_videodetails_inputpic:        //评论
            case R.id.tv_videodetails_input:        //评论
                if (!YlUtils.judgeUserExist()) {
                    YlUtils.loginUser(VideoDetailsActivity.this);
                    return;
                }
                showCommentInputPop(1, null);

                break;
            case R.id.iv_videodetails_like:
                if (!YlUtils.judgeUserExist()) {
                    YlUtils.loginUser(VideoDetailsActivity.this);
                    return;
                }
                if (isLike) {
                    ToastUtils.showShort(getString(R.string.video_zan_has_tip));
                } else {
                    videoZanNet();
                }
                break;
            case R.id.iv_videodetails_down:     //下载
                downNotifyNet();

                break;
            case R.id.iv_videodetails_share:
                shareText();
                break;
            case R.id.tv_videodetails_commenthot:
                commentType = 1;
                commentListNet();
                tvVideodetailsCommenthot.setTextColor(getResources().getColor(R.color.white));
                tvVideodetailsCommentnew.setTextColor(getResources().getColor(R.color.col_text4));
                tvVideodetailsCommenthot.setBackground(getResources().getDrawable(R.drawable.yellow_cir));
                tvVideodetailsCommentnew.setBackgroundColor(getResources().getColor(R.color.black_bg));
                break;
            case R.id.tv_videodetails_commentnew:
                commentType = 0;
                commentListNet();
                tvVideodetailsCommenthot.setTextColor(getResources().getColor(R.color.col_text4));
                tvVideodetailsCommentnew.setTextColor(getResources().getColor(R.color.white));
                tvVideodetailsCommentnew.setBackground(getResources().getDrawable(R.drawable.yellow_cir));
                tvVideodetailsCommenthot.setBackgroundColor(getResources().getColor(R.color.black_bg));
                break;
        }
    }


    private void shareText() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String appShareInfo = String.format(getString(R.string.app_share_video_detail_tip), userInfo.getPromotion_code());
        // 将文本内容放到系统剪贴板里。
        cm.setText(appShareInfo);
        ToastUtils.showShort(getString(R.string.cope_success_tip));
    }


    //简介弹框
    private VideoDescFragment mVideoDescFragment;

    private void showDialogFragmentDesc() {
        mVideoDescFragment = new VideoDescFragment(mVideoDetailsBean);
        mVideoDescFragment.show(getSupportFragmentManager(), VideoDescFragment.class.getSimpleName());

    }

    private void videoZanNet() {
        model.videoZanData(this, videoId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                isLike = !isLike;
                setLikeStatue();

            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void setLikeStatue() {
        if (isLike) {
            ivVideodetailsLike.setImageResource(R.mipmap.icon_favorite_active);
        } else {
            ivVideodetailsLike.setImageResource(R.mipmap.icon_favorite);
        }
    }

    private CommentInputDialog inputDialog;

    private void showCommentInputPop(int type, CommentBean mCommentBean) {
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
                if (type == 1 || type == 3) {       //一级评论，提交对影片的评论
                    commentPutNet(inputDialog, commText, 0, 0);
                    if (type == 3) {   //增加一条弹幕
                        mSuperPlayerView.addDanmuOneData(commText);
                    }
                } else {      //二级评论，提交对评论的评论
                    commentPutNet(inputDialog, commText, mCommentBean.getId(), mCommentBean.getId());
                }

            }
        });
    }

    private ArrayList<CommentBean> commentData;

    private void commentListNet() {
        model.commentListData(this, videoId, commentType, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                CommentListBean mCommentListBean = gson.fromJson(data, CommentListBean.class);
                commentData = mCommentListBean.getList();
                mQuickAdapter2.setData(commentData);
                if (commentData != null && commentData.size() > 0) {
                    ArrayList<String> danmuData = new ArrayList<String>();
                    for (int i = 0; i < commentData.size(); i++) {
                        danmuData.add(commentData.get(i).getContent());
                    }
                    mSuperPlayerView.setDanmuData(danmuData);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    public void commentPutNet(CommentInputDialog inputDialog, String commText, int firstCommentId, int targetCommentId) {
        model.commentPutData(this, videoId, commText, firstCommentId, targetCommentId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                //评论成功
                if (inputDialog != null) {
                    inputDialog.clearInputContent();
                    inputDialog.dismiss();
                }
                commentListNet();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    public void commentZanNet(int commentId, int position) {
        model.commentZanData(this, commentId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                mQuickAdapter2.setZanStatue(position);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    class MyOrientoinListener extends OrientationEventListener {
        public MyOrientoinListener(Context context) {
            super(context);
        }

        public MyOrientoinListener(Context context, int rate) {
            super(context, rate);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            int screenOrientation = getResources().getConfiguration().orientation;
            if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {       //正在播放时
                if (((orientation >= 0) && (orientation < 45)) || (orientation > 315)) {    //设置竖屏
                    mSuperPlayerView.mVodControllerLarge.exitFullScreen();
                    if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT && orientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                } else if (orientation > 225 && orientation < 315) {            //设置横屏
                    mSuperPlayerView.mVodControllerSmall.fullScreen();
                    if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                } else if (orientation > 45 && orientation < 135) {          // 设置反向横屏
                    mSuperPlayerView.mVodControllerSmall.fullScreen();
                    if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    }
                } else if (orientation > 135 && orientation < 225) {            //反向竖屏
                    mSuperPlayerView.mVodControllerLarge.exitFullScreen();
                    if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    }
                }
            }
        }
    }

    private void videoAddHistoryNet() {
        model.videoAddHistoryData(this, videoId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {


            }

            @Override
            public void onError(int code) {

            }
        });
    }

    //通知服务器该视频已经点击下载
    private void downNotifyNet() {
        model.videoDownNotifyData(this, videoId, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                //请求成功，可以下载，否则没有下载次数
                downLoadVideo();

            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void downLoadVideo() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLong(getString(R.string.neterror));
            return;
        }

        XXPermissions.with(this)
                .constantRequest()
                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {

                        if (TextUtils.isEmpty(download_url)) {
                            ToastUtils.showLong(getString(R.string.downloaderror));
                            return;
                        }

                        boolean isFinished = false;
                        boolean isDownLoading = false;
                        List<DownloadTask> values = OkDownload.restore(DownloadManager.getInstance().getFinished());
                        for (DownloadTask value : values) {
                            String url = value.progress.url;
                            if (url.equals(download_url)) {
                                String filepath = value.progress.filePath;
                                if (FileUtils.isFileExists(filepath)) {
                                    isFinished = true;
                                } else {
                                    value.remove();
                                }

                            }

                        }
                        if (isFinished) {
                            ToastUtils.showLong(getString(R.string.downloadFinish));
                            startDownloadActivity(1);
                            return;
                        }

                        List<DownloadTask> downloading = OkDownload.restore(DownloadManager.getInstance().getDownloading());
                        for (DownloadTask download : downloading) {

                            String url = download.progress.url;
                            if (url.equals(download_url)) {
                                String filepath = download.progress.filePath;
                                if (FileUtils.isFileExists(filepath)) {
                                    isDownLoading = true;
                                } else {
                                    download.remove();
                                }
                            }
                        }

                        if (isDownLoading) {
                            ToastUtils.showLong(getString(R.string.indownload));
                            startDownloadActivity(0);
                            return;
                        }


                        VideoModel apk = new VideoModel();
                        apk.id = videoId;
                        apk.name = videoname;
                        apk.iconUrl = videocover;
                        apk.url = download_url;
                        GetRequest<File> request = OkGo.<File>get(apk.url);
                        OkDownload.request(apk.url, request)//
                                .extra1(apk)
                                .save()
                                .register(new LogDownloadListener())//
                                .start();
                        startDownloadActivity(0);

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                    }
                });
    }

    private void startDownloadActivity(int i) {
        Intent intent = new Intent(VideoDetailsActivity.this, MyDownloadActivity.class);
        intent.putExtra(GlobConstant.SELECTPAGE, i);
        ActivityUtils.startActivity(intent);
    }

    //获取视频广告
    private void videoAdNet() {
        model.videoAdData(this, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                mAdBean = gson.fromJson(data, AdBean.class);
                if (!TextUtils.isEmpty(mAdBean.getTarget_url())) {
                    try {
                        Glide.with(MyApplication.getContext())
                                .load(mAdBean.getImage())
                                .into(ivVideodetailsAd);
                    } catch (Exception e) {

                    }
                } else {
                    ivVideodetailsAd.setVisibility(View.VISIBLE);
                    ivVideodetailsAd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }


}
