package com.video.yali.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.SearchHistoryAdapter;
import com.video.yali.adapter.StarMoviesAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.model.SearchModel;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.SearchHistoryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class SearchActivity extends BaseActivity implements OnLoadMoreListener, TextWatcher {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.recycle_search)
    RecyclerView recycleSearchResult;
    @BindView(R.id.refresh_search)
    SmartRefreshLayout refreshSearch;
    @BindView(R.id.recycle_history)
    RecyclerView recycleHistory;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;
    @BindView(R.id.tv_nocontent)
    TextView tvNocontent;
    @BindView(R.id.tv_searchtop)
    TextView tvSearchtop;

    private SearchModel model;
    private int page = 1;
    private String keyword;
    private StarMoviesAdapter mAdapter;
    private ArrayList<ColumnListBean.ListBean> listdata;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
        model = new SearchModel();
        recycleSearchResult.setLayoutManager(new LinearLayoutManager(this));
        recycleHistory.setLayoutManager(new GridLayoutManager(this, 4));
        refreshSearch.setEnableRefresh(false);
        refreshSearch.setOnLoadMoreListener(this);
        etSearch.addTextChangedListener(this);
        setHistory();
    }

    private void setHistory() {

        List<String> searchHistory = SearchHistoryUtils.getSearchHistory();
        if (searchHistory.size() == 0) {
            return;
        }
        SearchHistoryAdapter adapter = new SearchHistoryAdapter(recycleHistory);
        adapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                etSearch.setText(searchHistory.get(position));
                etSearch.setSelection(searchHistory.get(position).length());
                page = 1;
                keyword = searchHistory.get(position);
                searchResult(false);
            }
        });
        adapter.setData(searchHistory);
        recycleHistory.setAdapter(adapter);

    }

    @OnClick({R.id.img_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_search:
                String input = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    ToastUtils.showLong("请输入搜索内容");
                    return;
                }
                keyword = input;


                searchResult(false);
                break;
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        searchResult(true);
    }

    private void searchResult(boolean isLoadmore) {

        SearchHistoryUtils.saveSearchHistory(keyword);
        model.keywordSearch(this, keyword, page, new RequestCallback() {
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

                            Intent intent = new Intent(SearchActivity.this, VideoDetailsActivity.class);
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

            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String input = editable.toString().trim();
        if (TextUtils.isEmpty(input)) {

            rlHistory.setVisibility(View.VISIBLE);
            refreshSearch.setVisibility(View.GONE);

            setHistory();
        } else {

            rlHistory.setVisibility(View.GONE);
//            if (mAdapter != null) {
//                listdata.clear();
//                mAdapter.notifyDataSetChanged();
//            }


            keyword=input;
            searchResult(false);
            refreshSearch.setVisibility(View.VISIBLE);
           // tvNocontent.setVisibility(View.GONE);
        }


    }
}
