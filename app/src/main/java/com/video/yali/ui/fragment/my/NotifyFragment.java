package com.video.yali.ui.fragment.my;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.R;
import com.video.yali.adapter.MyLikeAdapter;
import com.video.yali.adapter.MyNotifyAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.HistoryBean;
import com.video.yali.bean.MyLikeBean;
import com.video.yali.bean.MyLikeListBean;
import com.video.yali.bean.MyNotifyBean;
import com.video.yali.bean.NotifyListBean;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.ui.activity.me.MyLikeActivity;
import com.video.yali.ui.activity.me.MyNotifyActivity;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class NotifyFragment extends BasePagerFragment {

    @BindView(R.id.rv_notify)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_notify)
    SmartRefreshLayout mSmartRefreshLayout;

    private MyModel model = new MyModel();
    private MyNotifyAdapter mQuickAdapter;
    private boolean isLoadmore = false;
    private int page = 1;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_notify, null);
        return mView;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuickAdapter = new MyNotifyAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                MyNotifyBean mMyNotifyBean = mQuickAdapter.getData().get(position);
                Intent webIntent = new Intent(getActivity(), WebActivity.class);
                webIntent.putExtra("webType", WebActivity.WebNotify);
                webIntent.putExtra("webId", mMyNotifyBean.getId());
                webIntent.putExtra("webTitle", mMyNotifyBean.getTitle());
                webIntent.putExtra("webContent", mMyNotifyBean.getContent());
                startActivity(webIntent);
                if (!mMyNotifyBean.isIs_read()) {
                    mQuickAdapter.setIsReadStatue(position);
                }
            }
        });

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadmore = true;
                loadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isLoadmore = false;
                loadData();
            }
        });
    }


    @Override
    protected void loadData() {
        model.getMyNotifyData((MyNotifyActivity) getActivity(), page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                NotifyListBean mNotifyListBean = gson.fromJson(data, NotifyListBean.class);
                ArrayList<MyNotifyBean> mData = mNotifyListBean.getList();
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
