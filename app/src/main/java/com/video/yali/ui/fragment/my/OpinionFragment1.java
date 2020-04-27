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
import com.video.yali.adapter.MyOpinionAdapter1;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.HistoryBean;
import com.video.yali.bean.MyNotifyBean;
import com.video.yali.bean.MyOpinionQuestionBean;
import com.video.yali.bean.MyOpinionQuestionListBean;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.ui.activity.me.MyOpinionActivity;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class OpinionFragment1 extends BasePagerFragment {


    @BindView(R.id.refresh_opinion)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_opinion)
    RecyclerView mRecyclerView;

    private MyModel model = new MyModel();
    private MyOpinionAdapter1 mQuickAdapter;
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
        mQuickAdapter = new MyOpinionAdapter1(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                MyOpinionQuestionBean mMyOpinionQuestionBean = mQuickAdapter.getData().get(position);
                Intent webIntent = new Intent(getActivity(), WebActivity.class);
                webIntent.putExtra("webType", WebActivity.WebProblem);
                webIntent.putExtra("webTitle", mMyOpinionQuestionBean.getQuestion());
                webIntent.putExtra("webContent", mMyOpinionQuestionBean.getAnswer());
                startActivity(webIntent);
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
        model.getOpionQuestionListData((MyOpinionActivity) getActivity(), page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                MyOpinionQuestionListBean mNotifyListBean = gson.fromJson(data, MyOpinionQuestionListBean.class);
                ArrayList<MyOpinionQuestionBean> mData = mNotifyListBean.getList();
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
