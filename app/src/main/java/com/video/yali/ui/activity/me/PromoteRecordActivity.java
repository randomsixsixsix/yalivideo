package com.video.yali.ui.activity.me;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.R;
import com.video.yali.adapter.PromoteRecordAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.MyLikeBean;
import com.video.yali.bean.MyLikeListBean;
import com.video.yali.bean.PromoteRecordBean;
import com.video.yali.bean.PromoteRecordListBean;
import com.video.yali.model.MyModel;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class PromoteRecordActivity extends BaseActivity {


    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.rv_promoterecord)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_promoterecord)
    SmartRefreshLayout mSmartRefreshLayout;

    private MyModel model = new MyModel();
    private PromoteRecordAdapter mQuickAdapter;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_promote_record;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.promote_record));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQuickAdapter = new PromoteRecordAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

        getPromoteRecordNet(false);

    }

    @Override
    public void initListener() {
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPromoteRecordNet(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getPromoteRecordNet(false);
            }
        });
    }

    @OnClick(R.id.ib_title_left)
    public void onClick() {
        finish();
    }

    private void getPromoteRecordNet(boolean isLoadmore) {
        model.getPromoteRecordData(this, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                PromoteRecordListBean mPromoteRecordListBean = gson.fromJson(data, PromoteRecordListBean.class);
                ArrayList<PromoteRecordBean> mData = mPromoteRecordListBean.getList();
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
