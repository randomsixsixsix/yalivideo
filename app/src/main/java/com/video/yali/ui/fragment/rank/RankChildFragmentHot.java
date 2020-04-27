package com.video.yali.ui.fragment.rank;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.adapter.RankHotAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.EventBean;
import com.video.yali.bean.HotSearchBean;
import com.video.yali.model.RankModel;
import com.video.yali.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankChildFragmentHot extends BasePagerFragment implements OnLoadMoreListener {
    @BindView(R.id.recycle_rankhot)
    RecyclerView mRecycle;
    @BindView(R.id.refresh_rankhot)
    SmartRefreshLayout refreshRankhot;
    @BindView(R.id.tv_nocontent)
    TextView tvNocontent;
    private RankModel model;
    private String type = "day";
    private int page = 1;
    private String info = "热搜榜";
    private RankHotAdapter mAdapter;
    private List<HotSearchBean.ListBean> listdata;

    @Override
    protected View getLayoutId() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_rankhot, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshRankhot.setEnableRefresh(false);
        refreshRankhot.setOnLoadMoreListener(this);
        mRecycle.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void loadData() {

        model = new RankModel();
        getRanklist(false);


    }

    private void getRanklist(boolean isLoadmore) {
        model.getRankList(context, Neturl.TOPSEARCH, type, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                if (isLoadmore) {
                    refreshRankhot.finishLoadMore();
                } else {
                    refreshRankhot.finishRefresh();
                }

                HotSearchBean hotSearchBean = GsonUtils.fromJson(data, HotSearchBean.class);
                List<HotSearchBean.ListBean> list = hotSearchBean.getList();

                if (mAdapter == null) {
                    listdata = new ArrayList<>();
                    listdata.addAll(list);
                    if (listdata.size() == 0) {
                        tvNocontent.setVisibility(View.VISIBLE);
                        mRecycle.setVisibility(View.GONE);
                        return;
                    }
                    tvNocontent.setVisibility(View.GONE);
                    mRecycle.setVisibility(View.VISIBLE);
                    mAdapter = new RankHotAdapter(mRecycle);
                    mAdapter.setData(listdata);
                    mRecycle.setAdapter(mAdapter);
                } else if (isLoadmore) {
                    listdata.addAll(list);
                    mAdapter.notifyDataSetChanged();

                } else {
                    listdata.clear();
                    listdata.addAll(list);
                    if (listdata.size() == 0) {
                        tvNocontent.setVisibility(View.VISIBLE);
                        mRecycle.setVisibility(View.GONE);
                    } else {
                        tvNocontent.setVisibility(View.GONE);
                        mRecycle.setVisibility(View.VISIBLE);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(int code) {
                if (isLoadmore) {
                    refreshRankhot.finishLoadMore();
                } else {
                    refreshRankhot.finishRefresh();
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshView(EventBean eventBean) {
        String code = eventBean.getCode();
        if (!TextUtils.isEmpty(code)) {
            if (TextUtils.equals(code, info)) {
                String msg = eventBean.getMsg();
                if (!TextUtils.isEmpty(msg)) {
                    page = 1;
                    type = msg;
                    getRanklist(false);
                }
            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelAll();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getRanklist(true);
    }
}
