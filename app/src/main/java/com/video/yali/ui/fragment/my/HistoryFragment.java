package com.video.yali.ui.fragment.my;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.R;
import com.video.yali.adapter.MyHistoryAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.HistoryBean;
import com.video.yali.bean.HistoryListBean;
import com.video.yali.bean.MyLikeBean;
import com.video.yali.event.OrangeEvent;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.ui.activity.me.MyHistoryActivity;
import com.video.yali.ui.activity.me.MyLikeActivity;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class HistoryFragment extends BasePagerFragment {

    @BindView(R.id.refresh_myhistory)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_myhistory)
    RecyclerView mRecyclerView;
    @BindView(R.id.sb_myhistory_bottom1)
    SuperButton sbMyhistoryBottom1;
    @BindView(R.id.sb_myhistory_bottom2)
    SuperButton sbMyhistoryBottom2;
    @BindView(R.id.ll_myhistory_bottom)
    LinearLayout llMyhistoryBottom;
    private MyModel model = new MyModel();
    private MyHistoryAdapter mQuickAdapter;
    private boolean isChoseAll = false;
    private boolean isLoadmore = false;
    private int page = 1;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_history, null);
        return mView;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mQuickAdapter = new MyHistoryAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        page = 1;
        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.ll_myhistory_item_chose) {        //选择
                    mQuickAdapter.setChoseOneStatue(position);
                    checkAllStatue();
                }
            }
        });
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                HistoryBean mHistoryBean = mQuickAdapter.getData().get(position);
                Intent mIntent = new Intent(getActivity(), VideoDetailsActivity.class);
                mIntent.putExtra("videoId", mHistoryBean.getMovie_id());
                startActivity(mIntent);
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

    //监听单项选择是否改变全部状态
    private void checkAllStatue() {
        ArrayList<HistoryBean> mData = (ArrayList<HistoryBean>) mQuickAdapter.getData();
        boolean isAll = true;
        boolean defaultChose = mData.get(0).isChose();
        for (int i = 0; i < mData.size(); i++) {
            boolean currChose = mData.get(i).isChose();
            if (defaultChose != currChose) {
                isAll = false;
                break;
            }
        }
        if (isAll) {
            isChoseAll = defaultChose;
            setAllChoseStatue();
        }

    }


    @Override
    protected void loadData() {
        int fragmentType = this.getArguments().getInt("type", 0);
        model.getMyHistoryData((MyHistoryActivity) getActivity(), fragmentType, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                HistoryListBean mHistoryListBean = gson.fromJson(data, HistoryListBean.class);
                ArrayList<HistoryBean> mData = mHistoryListBean.getList();
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

    @Subscribe       //订阅事件FirstEvent
    public void onEventMainThread(OrangeEvent event) {
        if (ConstantUtils.EVENT_HISTORYEDIT_CHOSE == event.msg) {
            mQuickAdapter.setEditStatue(true);
            llMyhistoryBottom.setVisibility(View.VISIBLE);
        } else if (ConstantUtils.EVENT_HISTORYEDIT_NO == event.msg) {
            mQuickAdapter.setEditStatue(false);
            llMyhistoryBottom.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.sb_myhistory_bottom1, R.id.sb_myhistory_bottom2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sb_myhistory_bottom1:
                isChoseAll = !isChoseAll;
                setAllChoseStatue();
                break;
            case R.id.sb_myhistory_bottom2:
                deleteNet();
                break;
        }
    }

    private void setAllChoseStatue() {
        if (isChoseAll) {
            mQuickAdapter.setChoseAllStatue(true);
            sbMyhistoryBottom1.setText(getString(R.string.chose_noall));
        } else {
            mQuickAdapter.setChoseAllStatue(false);
            sbMyhistoryBottom1.setText(getString(R.string.chose_all));
        }
    }

    private void deleteNet() {
        ArrayList<HistoryBean> mData = (ArrayList<HistoryBean>) mQuickAdapter.getData();
        ArrayList<Integer> selectIds = new ArrayList<Integer>();
        for (int i = 0; i < mData.size(); i++) {
            boolean currChose = mData.get(i).isChose();
            if (currChose) {
                selectIds.add(mData.get(i).getId());
            }
        }
        if (selectIds.size() == 0) {
            ToastUtils.showShort(getString(R.string.delete_no_tip));
            return;
        }
        Object[] arrayIds = selectIds.toArray();
        model.getMyHistoryDeleteData((MyHistoryActivity) getActivity(), arrayIds, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                loadData();
            }

            @Override
            public void onError(int code) {

            }
        });
    }


}
