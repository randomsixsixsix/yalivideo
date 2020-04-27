package com.video.yali.ui.fragment.my;

import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.R;
import com.video.yali.adapter.MyOpinionAdapter3;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.MyFeedbackBean;
import com.video.yali.bean.MyFeedbackListBean;
import com.video.yali.bean.MyOpinionListBean;
import com.video.yali.bean.MyOpinionQuestionBean;
import com.video.yali.bean.MyOpinionQuestionListBean;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.me.MyOpinionActivity;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class OpinionFragment3 extends BasePagerFragment {

    @BindView(R.id.refresh_opinion)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_opinion)
    RecyclerView mRecyclerView;
    private MyModel model = new MyModel();
    private MyOpinionAdapter3 mQuickAdapter;
    private boolean isLoadmore = false;
    private int page = 1;


    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_opinion, null);
        return mView;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuickAdapter = new MyOpinionAdapter3(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

        initListener();
    }

    private void initListener() {
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
        model.getOpionListData((MyOpinionActivity) getActivity(), page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                MyFeedbackListBean mMyFeedbackListBean = gson.fromJson(data, MyFeedbackListBean.class);
                ArrayList<MyFeedbackBean> mData = mMyFeedbackListBean.getList();
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
