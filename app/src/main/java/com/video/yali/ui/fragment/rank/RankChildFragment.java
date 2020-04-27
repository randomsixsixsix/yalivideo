package com.video.yali.ui.fragment.rank;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.Neturl;
import com.video.yali.R;
import com.video.yali.adapter.RankAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.EventBean;
import com.video.yali.bean.VideoBean;
import com.video.yali.model.RankModel;
import com.video.yali.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 观影,新片,鲁sir
 */
public class RankChildFragment extends BasePagerFragment implements OnRefreshLoadMoreListener {

    @BindView(R.id.recycle_rank)
    RecyclerView recycleRank;
    @BindView(R.id.refresh_rank)
    SmartRefreshLayout refreshRank;
    @BindView(R.id.tv_nocontent)
    TextView tvNocontent;

    private RankModel model;
    private int page = 1;
    private String type = "day";
    private List<VideoBean> listdata;
    private RankAdapter rankAdapter;

    @Override
    protected View getLayoutId() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_rankchild, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        model = new RankModel();
        EventBus.getDefault().register(this);
        refreshRank.setEnableRefresh(false);
        refreshRank.setOnRefreshLoadMoreListener(this);
        recycleRank.setLayoutManager(new LinearLayoutManager(context));

        getdata(false);


    }

    private void getdata(boolean isLoadmore) {
        if (info.equals("观影榜")) {
            getRanklist(Neturl.TOPPLAY, isLoadmore);
        } else if (info.equals("新片榜")) {
            getRanklist(Neturl.TOPNEW, isLoadmore);
        } else if (info.equals("撸sir榜")) {
            getRanklist(Neturl.TOPMASTURBATION, isLoadmore);

        }
    }

    private void getRanklist(String url, boolean isLoadmore) {
        model.getRankList(context, url, type, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                finishdata(isLoadmore);

                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();


                if (rankAdapter == null) {
                    listdata = new ArrayList<>();
                    for (ColumnListBean.ListBean listBean : list) {
                        listdata.add(new VideoBean(listBean.getMovie_id(), listBean.getDuration(), listBean.getPoster(), listBean.getName(),
                                listBean.getDownload_url(), listBean.getPlay_count() + "", listBean.getDownload_count() + "", listBean.getLike_count() + ""
                                , listBean.getPublished_at()
                        ));
                    }
                    if (listdata.size() == 0) {
                        tvNocontent.setVisibility(View.VISIBLE);
                        recycleRank.setVisibility(View.GONE);
                        return;
                    }
                    tvNocontent.setVisibility(View.GONE);
                    recycleRank.setVisibility(View.VISIBLE);
                    rankAdapter = new RankAdapter(recycleRank, info);
                    rankAdapter.setData(listdata);
                    recycleRank.setAdapter(rankAdapter);
                } else if (isLoadmore) {
                    for (ColumnListBean.ListBean listBean : list) {
                        listdata.add(new VideoBean(listBean.getMovie_id(), listBean.getDuration(), listBean.getPoster(), listBean.getName(),
                                listBean.getDownload_url(), listBean.getPlay_count() + "", listBean.getDownload_count() + "", listBean.getLike_count() + ""
                                , listBean.getPublished_at()
                        ));
                    }
                    rankAdapter.notifyDataSetChanged();

                } else {
                    listdata.clear();
                    for (ColumnListBean.ListBean listBean : list) {
                        listdata.add(new VideoBean(listBean.getMovie_id(), listBean.getDuration(), listBean.getPoster(), listBean.getName(),
                                listBean.getDownload_url(), listBean.getPlay_count() + "", listBean.getDownload_count() + "", listBean.getLike_count() + ""
                                , listBean.getPublished_at()
                        ));
                    }
                    if (listdata.size() == 0) {
                        tvNocontent.setVisibility(View.VISIBLE);
                        recycleRank.setVisibility(View.GONE);
                    }else {
                        tvNocontent.setVisibility(View.GONE);
                        recycleRank.setVisibility(View.VISIBLE);
                    }
                    rankAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(int code) {
                finishdata(isLoadmore);
            }
        });
    }


    private String info;

    public RankChildFragment(String title) {

        info = title;
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
                    getdata(false);
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
        getdata(true);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        getdata(false);
    }

    private void finishdata(boolean isLoadmore) {
        if (isLoadmore) {
            refreshRank.finishLoadMore();
        } else {
            refreshRank.finishRefresh();
        }
    }
}
