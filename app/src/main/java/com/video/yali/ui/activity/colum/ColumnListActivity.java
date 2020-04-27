package com.video.yali.ui.activity.colum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.ColumnListAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.ColumDetailBean;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.VideoBean;
import com.video.yali.model.ColumnModel;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.utils.RequestCallback;
import com.video.yali.widget.BottomRoundImageView;
import com.video.yali.widget.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class ColumnListActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    @BindView(R.id.img_videolist_top)
    BottomRoundImageView imgVideolistTop;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_video_type)
    TextView tvVideoType;
    @BindView(R.id.refresh_videolist)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recycle_videolist)
    RecyclerView mRecycle;
    @BindView(R.id.tv_video_desc)
    ExpandableTextView tvVideoDesc;
    private int id;
    private int page = 1;
    private ColumnListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_videolist;
    }

    private ColumnModel model;
    private List<VideoBean> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
        mRefresh.setOnRefreshLoadMoreListener(this);
        mRecycle.setLayoutManager(new GridLayoutManager(this, 3));
        id = getIntent().getIntExtra("id", 1);

        model = new ColumnModel();
        showProgress();
        getColumDetail();
        getColumList(false);

    }

    private void getColumList(boolean isLoadmore) {

        model.getColumnList(this, id, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                dismissProgress();
                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();

                if (mAdapter == null) {
                    listdata = new ArrayList<>();
                    for (ColumnListBean.ListBean listBean : list) {
                        listdata.add(new VideoBean(listBean.getId(), listBean.getDuration(), listBean.getPoster_v(), listBean.getName()));
                    }
                    if (listdata.size() == 0) {
                        if (isLoadmore) {
                            mRefresh.finishLoadMore();
                        } else {
                            mRefresh.finishRefresh();
                        }
                        return;
                    }
                    mAdapter = new ColumnListAdapter(mRecycle);
                    mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                        @Override
                        public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                            Intent intent = new Intent(ColumnListActivity.this, VideoDetailsActivity.class);
                            intent.putExtra(GlobConstant.VIDEOID, listdata.get(position).getId());
                            ActivityUtils.startActivity(intent);

                        }
                    });
                    mAdapter.setData(listdata);
                    mRecycle.setAdapter(mAdapter);
                } else if (isLoadmore) {

                    for (ColumnListBean.ListBean listBean : list) {
                        listdata.add(new VideoBean(listBean.getId(), listBean.getDuration(), listBean.getPoster_v(), listBean.getName()));
                    }
                    if (list.size() == 0) {
                        mRefresh.finishLoadMoreWithNoMoreData();
                    }
                    mAdapter.notifyDataSetChanged();
                    mRefresh.finishLoadMore();
                } else {
                    listdata.clear();
                    for (ColumnListBean.ListBean listBean : list) {
                        listdata.add(new VideoBean(listBean.getId(), listBean.getDuration(), listBean.getPoster_v(), listBean.getName()));
                    }
                    mAdapter.notifyDataSetChanged();
                    mRefresh.finishRefresh();
                }


            }

            @Override
            public void onError(int code) {
                dismissProgress();
                if (isLoadmore) {
                    mRefresh.finishLoadMore();
                } else {
                    mRefresh.finishRefresh();
                }
            }
        });
    }

    private void getColumDetail() {

        model.getZhuantiDetail(this, id, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                ColumDetailBean columDetailBean = GsonUtils.fromJson(data, ColumDetailBean.class);
                String poster = columDetailBean.getPoster();//封面图片
                Glide.with(ColumnListActivity.this).load(poster).into(imgVideolistTop);
                String name = columDetailBean.getName();
                tvVideoType.setText(name);
                tvVideoDesc.setText(columDetailBean.getDescription());
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getColumList(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        getColumList(false);

    }
}
