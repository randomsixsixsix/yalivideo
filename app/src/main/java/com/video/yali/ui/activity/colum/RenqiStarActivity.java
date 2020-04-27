package com.video.yali.ui.activity.colum;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.HotStarAdapter;
import com.video.yali.adapter.SearchLabelAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.EventBean;
import com.video.yali.bean.LabelBean;
import com.video.yali.bean.StarListBean;
import com.video.yali.model.ColumnModel;
import com.video.yali.utils.RequestCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

/**
 * 人气明星
 */
public class RenqiStarActivity extends BaseActivity implements OnLoadMoreListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.recycle_choice1)
    RecyclerView recycleChoice1;
    @BindView(R.id.recycle_choice2)
    RecyclerView recycleChoice2;
    @BindView(R.id.recycle_stars)
    RecyclerView recycleStars;
    @BindView(R.id.refresh_renqistar)
    SmartRefreshLayout refreshRenqistar;
    @BindArray(R.array.cup)
    String[] cups;

    private ColumnModel model;
    private int page = 1;
    private List<StarListBean.ListBean> listdata;
    private HotStarAdapter hotStarAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_renqistar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        refreshRenqistar.setOnLoadMoreListener(this);
        refreshRenqistar.setEnableRefresh(false);
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
        EventBus.getDefault().register(this);
        model = new ColumnModel();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recycleChoice1.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recycleChoice2.setLayoutManager(layoutManager2);

        recycleStars.setLayoutManager(new GridLayoutManager(this, 3));

        initdata();
    }

    private List<LabelBean> labelBeans;
    private List<LabelBean> labelBeans2;

    private String sort = "1";
    private String cup = "A";

    public void initdata() {
        cup = cups[0];
        labelBeans = new ArrayList<>();
        labelBeans2 = new ArrayList<>();

        labelBeans.add(new LabelBean(1, getResources().getString(R.string.renqimost), "1"));
        labelBeans.add(new LabelBean(0, getResources().getString(R.string.videomost), "2"));

        for (int i = 0; i < cups.length; i++) {
            if (i == 0) {
                labelBeans2.add(new LabelBean(1, cups[i] + "罩杯", cups[i]));
            } else {
                labelBeans2.add(new LabelBean(0, cups[i] + "罩杯", cups[i]));

            }
        }

        SearchLabelAdapter adapter = new SearchLabelAdapter(recycleChoice1, 7);
        adapter.setData(labelBeans);
        recycleChoice1.setAdapter(adapter);

        SearchLabelAdapter adapter2 = new SearchLabelAdapter(recycleChoice2, 8);
        adapter2.setData(labelBeans2);
        recycleChoice2.setAdapter(adapter2);


        getStarList(false);


    }

    private void getStarList(boolean isLoadmore) {
        showProgress();
        model.starList(this, sort, cup, page, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                dismissProgress();
                if (isLoadmore) {
                    refreshRenqistar.finishLoadMore();
                } else {
                    refreshRenqistar.finishRefresh();
                }

                StarListBean starListBean = GsonUtils.fromJson(data, StarListBean.class);
                List<StarListBean.ListBean> list = starListBean.getList();
                if (hotStarAdapter == null) {
                    listdata = new ArrayList<>();
                    listdata.addAll(list);
                    if (listdata.size() == 0) {

                        return;
                    }
                    hotStarAdapter = new HotStarAdapter(recycleStars, R.layout.item_star);
                    hotStarAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                        @Override
                        public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                            StarListBean.ListBean listBean = listdata.get(position);
                            int id = listBean.getId();
                            Intent intent = new Intent(RenqiStarActivity.this, StarDetailActivity.class);
                            intent.putExtra("starid", id);
                            ActivityUtils.startActivity(intent);
                        }
                    });
                    hotStarAdapter.setData(listdata);
                    recycleStars.setAdapter(hotStarAdapter);
                } else if (isLoadmore) {
                    listdata.addAll(list);
                    hotStarAdapter.notifyDataSetChanged();
                } else {
                    listdata.clear();
                    listdata.addAll(list);
                    hotStarAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(int code) {
                dismissProgress();
                if (isLoadmore) {
                    refreshRenqistar.finishLoadMore();
                } else {
                    refreshRenqistar.finishRefresh();
                }
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
        getStarList(true);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshList(EventBean eventBean) {

        String code = eventBean.getCode();
        if (code.equals("7")) {
            sort = eventBean.getMsg();
            page = 1;
            getStarList(false);
        }

        if (code.equals("8")) {
            cup = eventBean.getMsg();
            page = 1;
            getStarList(false);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
