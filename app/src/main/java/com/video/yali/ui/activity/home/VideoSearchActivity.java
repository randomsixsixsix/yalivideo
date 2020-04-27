package com.video.yali.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.SearchLabelAdapter;
import com.video.yali.adapter.StarMoviesAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.EventBean;
import com.video.yali.bean.LabelBean;
import com.video.yali.bean.SearchItemBean;
import com.video.yali.model.SearchModel;
import com.video.yali.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class VideoSearchActivity extends BaseActivity implements OnLoadMoreListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.recycle_search1)
    RecyclerView recycleSearch1;
    @BindView(R.id.recycle_search2)
    RecyclerView recycleSearch2;
    @BindView(R.id.recycle_search3)
    RecyclerView recycleSearch3;
    @BindView(R.id.recycle_search4)
    RecyclerView recycleSearch4;
    @BindView(R.id.recycle_search_result)
    RecyclerView recycleSearchResult;
    @BindView(R.id.refresh_search)
    SmartRefreshLayout refreshSearch;
    @BindView(R.id.tv_search_title)
    TextView tvSearchTitle;
    @BindView(R.id.recycle_search5)
    RecyclerView recycleSearch5;
    @BindView(R.id.tv_nocontent)
    TextView tvNocontent;

    private SearchModel model;
    private int channelid;
    private int area = -1;
    private int categoryid = -1;
    private int duration = -1;
    private int publishtime = -1;
    private int sort = -1;

    private String channelname;
    private int page = 1;
    private StarMoviesAdapter mAdapter;
    private List<ColumnListBean.ListBean> listdata;

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        channelid = intent.getIntExtra(GlobConstant.CHANNELID, 1);
        channelname = intent.getStringExtra(GlobConstant.CHANNELNAME);

        refreshSearch.setOnLoadMoreListener(this);
        refreshSearch.setEnableRefresh(false);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recycleSearch1.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recycleSearch2.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recycleSearch3.setLayoutManager(layoutManager3);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this);
        layoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        recycleSearch4.setLayoutManager(layoutManager4);

        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this);
        layoutManager5.setOrientation(RecyclerView.HORIZONTAL);
        recycleSearch5.setLayoutManager(layoutManager5);

        recycleSearchResult.setLayoutManager(new LinearLayoutManager(this));

        tvSearchTitle.setText(channelname);
        model = new SearchModel();
        getArea();
        getCatory();
        getDurcation();
        getMoveSort(channelid);
        getPublishTime();


        // getSearchResult(false);
    }

    private void getSearchResult(boolean isLoadmore) {

        model.videoSearch(this, area, categoryid, channelid, duration, publishtime, sort, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();

                if (isLoadmore) {

                    refreshSearch.finishLoadMore();
                } else {
                    refreshSearch.finishRefresh();
                }

                if (mAdapter == null) {

                    listdata = new ArrayList<>();
                    listdata.addAll(list);
                    if (listdata.size() == 0) {
                        tvNocontent.setVisibility(View.VISIBLE);
                        recycleSearchResult.setVisibility(View.GONE);
                        return;
                    }
                    tvNocontent.setVisibility(View.GONE);
                    recycleSearchResult.setVisibility(View.VISIBLE);
                    mAdapter = new StarMoviesAdapter(recycleSearchResult);
                    mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                        @Override
                        public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                            Intent intent = new Intent(VideoSearchActivity.this, VideoDetailsActivity.class);
                            intent.putExtra(GlobConstant.VIDEOID, list.get(position).getId());
                            ActivityUtils.startActivity(intent);
                        }
                    });
                    mAdapter.setData(listdata);
                    recycleSearchResult.setAdapter(mAdapter);
                } else if (isLoadmore) {
                    listdata.addAll(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    listdata.clear();
                    listdata.addAll(list);
                    if (listdata.size() == 0) {
                        tvNocontent.setVisibility(View.VISIBLE);
                        recycleSearchResult.setVisibility(View.GONE);
                        return;
                    }
                    tvNocontent.setVisibility(View.GONE);
                    recycleSearchResult.setVisibility(View.VISIBLE);
                    mAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(int code) {
                if (isLoadmore) {

                    refreshSearch.finishLoadMore();
                } else {
                    refreshSearch.finishRefresh();
                }
            }
        });
    }

    private List<LabelBean> listData;

    private void getPublishTime() {

        model.getPublishtime(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                parsedata(data, recycleSearch4, 5);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void parsedata(String data, RecyclerView recyclerView, int flag) {
        SearchItemBean searchItemBean = GsonUtils.fromJson(data, SearchItemBean.class);
        List<SearchItemBean.ListBean> list = searchItemBean.getList();
        listData = new ArrayList<>();
        listData.add(new LabelBean(1, "全部", -1));
        for (SearchItemBean.ListBean listBean : list) {
            if (listBean.getName().equals(channelname)) {
                listData.get(0).setStatus(0);
                int id = listBean.getId();
                setid(id, flag);
                listBean.setStatus(1);
            }
            listData.add(new LabelBean(listBean.getStatus(), listBean.getName(), listBean.getId()));
        }
        SearchLabelAdapter mAdapter = new SearchLabelAdapter(recyclerView, flag);
//        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
//            @Override
//            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//                for (LabelBean listDatum : listData) {
//                    listDatum.setStatus(0);
//                }
//                LabelBean labelBean = listData.get(position);
//                int id = labelBean.getId();
//                LogUtils.i("点击事件11");
//
//                labelBean.setStatus(1);
//                mAdapter.notifyDataSetChanged();
//
//                //  setid(id, flag);
//                //  getSearchResult(false);
//            }
//        });
        mAdapter.setData(listData);
        recyclerView.setAdapter(mAdapter);
    }

    private void setid(int id, int flag) {
        page = 1;
        if (flag == 1) {
            sort = id;
        } else if (flag == 2) {
            area = id;
        } else if (flag == 3) {
            categoryid = id;
        } else if (flag == 4) {
            duration = id;
        } else if (flag == 5) {
            publishtime = id;
        }
        LogUtils.i("点击事件");
        getSearchResult(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(EventBean eventBean) {

        int flag = eventBean.getFlag();
        int id = eventBean.getId();
        if (flag != 0) {

            setid(id, flag);
        }

    }

    private void getMoveSort(int channelid) {
        model.getMovesort(this, channelid, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                parsedata(data, recycleSearch1, 1);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void getDurcation() {
        model.getduration(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                parsedata(data, recycleSearch3, 4);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void getCatory() {
        model.getCategory(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                parsedata(data, recycleSearch2, 3);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void getArea() {
        model.getArea(this, channelid, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                parsedata(data, recycleSearch5, 2);
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
        getSearchResult(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
