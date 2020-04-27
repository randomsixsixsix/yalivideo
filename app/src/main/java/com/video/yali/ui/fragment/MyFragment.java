package com.video.yali.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.R;
import com.video.yali.adapter.MySortAdapter;
import com.video.yali.base.BaseFragment;
import com.video.yali.bean.AdBean;
import com.video.yali.bean.MySortBean;
import com.video.yali.bean.UserBean;
import com.video.yali.download.MyDownloadActivity;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.MainActivity;
import com.video.yali.ui.activity.me.MyHistoryActivity;
import com.video.yali.ui.activity.me.MyLikeActivity;
import com.video.yali.ui.activity.me.MyNotifyActivity;
import com.video.yali.ui.activity.me.MyOpinionActivity;
import com.video.yali.ui.activity.me.PersonalActivity;
import com.video.yali.ui.activity.me.PromoteActivity;
import com.video.yali.ui.activity.me.SettingActivity;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ScrollGridLayoutManager;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class MyFragment extends BaseFragment {

    @BindView(R.id.iv_my_userlogo)
    ImageView ivMyUserlogo;
    @BindView(R.id.tv_my_username)
    TextView tvMyUsername;
    @BindView(R.id.iv_my_ad)
    ImageView ivMyAd;
    @BindView(R.id.ll_my_top)
    LinearLayout llMyTop;
    @BindView(R.id.tv_my_titlenumber1)
    TextView tvMyTitlenumber1;
    @BindView(R.id.tv_my_titlenumber2)
    TextView tvMyTitlenumber2;
    @BindView(R.id.tv_my_titlenumber3)
    TextView tvMyTitlenumber3;
    @BindView(R.id.ll_my_rank)
    LinearLayout llMyRank;
    @BindView(R.id.tv_my_rank1)
    TextView tvMyRank1;
    @BindView(R.id.tv_my_rank2)
    TextView tvMyRank2;
    @BindView(R.id.rb_my_rate)
    ProgressBar rbMyRate;
    @BindView(R.id.tv_my_diff)
    TextView tvMyDiff;
    @BindView(R.id.sb_my_promote)
    SuperButton sbMyPromote;
    @BindView(R.id.rv_my)
    RecyclerView mRecyclerView;


    @BindArray(R.array.my_sorts)  //绑定string里面array数组
            String[] mySortsStrings;

    private int[] mySortsIcons = new int[]{R.mipmap.my_like, R.mipmap.my_history, R.mipmap.my_down, R.mipmap.my_exchange, R.mipmap.my_notify, R.mipmap.my_opinion,
            R.mipmap.my_startcode, R.mipmap.my_setting, R.mipmap.my_openvip};
    private MySortAdapter mQuickAdapter;
    private MyModel model = new MyModel();
    private AdBean mAdBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }


    @Override
    public void initView() {

        initSortData();
        mRecyclerView.setLayoutManager(new ScrollGridLayoutManager(getActivity(), 3));
        initUserData();
        myAdNet();
    }

    private void initSortData() {
        ArrayList<MySortBean> sortData = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            sortData.add(new MySortBean(mySortsStrings[i], mySortsIcons[i]));
        }

        mQuickAdapter = new MySortAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setData(sortData);
    }


    @Override
    public void initListener() {
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                switch (position) {
                    case 0:
                        if (!YlUtils.judgeUserExist()) {
                            YlUtils.loginUser(getActivity());
                            return;
                        }
                        ToolUtils.startActivity(context, MyLikeActivity.class);
                        break;
                    case 1:
                        ToolUtils.startActivity(context, MyHistoryActivity.class);
                        break;
                    case 2:
                        ActivityUtils.startActivity(MyDownloadActivity.class);
                        break;
                    case 3:

//                        if (!YlUtils.judgeUserExist()) {
//                            YlUtils.loginUser(getActivity());
//                            return;
//                        }

                        Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("https://noone.ltd/xhsp1");
                        intent3.setData(content_url);
                        ActivityUtils.startActivity(intent3);
                        break;
                    case 4:
                        ToolUtils.startActivity(context, MyNotifyActivity.class);
                        break;
                    case 5:
                        ToolUtils.startActivity(context, MyOpinionActivity.class);
                        break;
                    case 6:
//                        ToolUtils.startActivity(context, MyStartCodeActivity.class);
                        break;
                    case 7:
                        if (!YlUtils.judgeUserExist()) {
                            YlUtils.loginUser(getActivity());
                            return;
                        }
                        ToolUtils.startActivity(context, SettingActivity.class);
                        break;
                    case 8:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick({R.id.iv_my_ad, R.id.iv_my_userlogo, R.id.tv_my_username, R.id.sb_my_promote})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my_ad:
                if (mAdBean != null) {
                    YlUtils.startAdDetails(getActivity(), ConstantUtils.adtype_video, mAdBean.getType(), mAdBean.getTarget_url(), mAdBean.getId());
                }
                break;
            case R.id.iv_my_userlogo:
            case R.id.tv_my_username:
                if (!YlUtils.judgeUserExist()) {
                    YlUtils.loginUser(getActivity());
                    return;
                }
                ToolUtils.startActivity(context, PersonalActivity.class);
                break;
            case R.id.sb_my_promote:
                ToolUtils.startActivity(getActivity(), PromoteActivity.class);
                break;
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
    }


    public void initUserData() {
        if (YlUtils.judgeUserExist()) {
            UserBean mUserBean = YlUtils.getUserInfo();
            Glide.with(context)
                    .load(mUserBean.getAvatar())
                    .apply(centerCropTransform()
                            .placeholder(R.mipmap.default_header_logo)
                            .error(R.mipmap.default_header_logo)
                            .priority(Priority.HIGH)
                            .transform(new GlideCircleTransform())  //圆形头像,自定义类
                            .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                            .skipMemoryCache(false))     //跳过内存缓存
                    .into(ivMyUserlogo);
            tvMyUsername.setText(mUserBean.getName());
            tvMyRank1.setText(mUserBean.getCurrent_level_name() + ".LV" + mUserBean.getCurrent_level());
            tvMyRank2.setText(mUserBean.getNext_level_name() + ".LV" + mUserBean.getNext_level());
            tvMyTitlenumber1.setText((mUserBean.getView_count() - mUserBean.getViewed_count()) + "/" + mUserBean.getView_count());
            tvMyTitlenumber2.setText((mUserBean.getDownload_count() - mUserBean.getDownloaded_count()) + "/" + mUserBean.getDownload_count());
            tvMyTitlenumber3.setText(mUserBean.getPromoter_count() + "");
            String dissNumbString = mUserBean.getCount_to_next_level() + "";
            String dissString = String.format(getString(R.string.my_rank_diss), mUserBean.getCount_to_next_level());
            SpannableString span = new SpannableString(dissString);
            span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.col_text_dissnumber)), dissString.length() - dissNumbString.length() - 1, dissString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //红色-----红色字体
            tvMyDiff.setText(span);
            if (mUserBean.isIs_vip()) {
                mQuickAdapter.setShowOpenVipView(false);
            } else {
                mQuickAdapter.setShowOpenVipView(true);
            }
            rbMyRate.setProgress(60);
        } else {          //空界面
            if (view == null) {
                return;
            }
            ivMyUserlogo.setImageResource(R.mipmap.default_header_logo);
            tvMyUsername.setText(getString(R.string.my_unlogin_title));
            tvMyRank1.setText(getString(R.string.my_vip0));
            tvMyRank2.setText(getString(R.string.my_vip1));
            UserBean mUserBean = YlUtils.getUserInfo();
            if (mUserBean != null) {
                String dissNumbString = mUserBean.getCount_to_next_level() + "";
                String dissString = String.format(getString(R.string.my_rank_diss), mUserBean.getCount_to_next_level());
                SpannableString span = new SpannableString(dissString);
                span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.col_text_dissnumber)), dissString.length() - dissNumbString.length() - 1, dissString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //红色-----红色字体
                tvMyDiff.setText(span);
                tvMyTitlenumber1.setText((mUserBean.getView_count() - mUserBean.getViewed_count()) + "/" + mUserBean.getView_count());
                tvMyTitlenumber2.setText((mUserBean.getDownload_count() - mUserBean.getDownloaded_count()) + "/" + mUserBean.getDownload_count());
                tvMyTitlenumber3.setText(mUserBean.getFavorite_count() + "");
            }
        }

    }


    //获取个人中心广告
    private void myAdNet() {
        model.myAdData((MainActivity) getActivity(), new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                mAdBean = gson.fromJson(data, AdBean.class);
                Glide.with(getActivity())
                        .load(mAdBean.getImage())
                        .apply(centerCropTransform()
                                .placeholder(R.drawable.app_bar_top)
                                .error(R.drawable.app_bar_top)
                                //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .skipMemoryCache(false))
                        .into(ivMyAd);
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
