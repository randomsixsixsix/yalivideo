package com.video.yali.ui.activity.me;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.R;
import com.video.yali.adapter.MyLikeAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.CommentListBean;
import com.video.yali.bean.MyLikeBean;
import com.video.yali.bean.MyLikeListBean;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

/**
 * 我的喜欢
 */
public class MyLikeActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.refresh_mylike)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_mylike)
    RecyclerView mRecyclerView;

    private MyModel model = new MyModel();
    private MyLikeAdapter mQuickAdapter;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_like;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.my_like));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQuickAdapter = new MyLikeAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

        getMyLikeNet(false);


    }

    @Override
    public void initListener() {
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                MyLikeBean mMyLikeBean = mQuickAdapter.getData().get(position);
                Intent mIntent = new Intent(MyLikeActivity.this, VideoDetailsActivity.class);
                mIntent.putExtra("videoId", mMyLikeBean.getMovie_id());
                startActivity(mIntent);
            }
        });
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getMyLikeNet(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getMyLikeNet(false);
            }
        });
    }

    @OnClick(R.id.ib_title_left)
    public void onClick() {
        finish();
    }

    private void getMyLikeNet(boolean isLoadmore) {
        model.getMyLikesData(this, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                MyLikeListBean mMyLikeListBean = gson.fromJson(data, MyLikeListBean.class);
                ArrayList<MyLikeBean> mData = mMyLikeListBean.getList();
                if (isLoadmore) {
                    mSmartRefreshLayout.finishLoadMore();
                    mQuickAdapter.addMoreData(mData);
                } else {
                    mSmartRefreshLayout.setNoMoreData(false);
                    mQuickAdapter.clear();
                    mSmartRefreshLayout.finishRefresh();
                    mQuickAdapter.setData(mData);
                }
                if (mData.size() < ConstantUtils.pageSize) {
                    mSmartRefreshLayout.setNoMoreData(true);
                } else {
                    page++;
                }

            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
