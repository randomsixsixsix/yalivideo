package com.video.yali.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.stx.xhb.xbanner.XBanner;
import com.video.yali.R;
import com.video.yali.adapter.HotColumnAdapter;
import com.video.yali.adapter.HotStarAdapter;
import com.video.yali.adapter.RenqiStarAdapter;
import com.video.yali.adapter.ZhuanjiAdapter;
import com.video.yali.base.BaseFragment;
import com.video.yali.bean.BannerBean;
import com.video.yali.bean.ColumnBannerBean;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.StarListBean;
import com.video.yali.model.ColumnModel;
import com.video.yali.ui.activity.colum.ColumnListActivity;
import com.video.yali.ui.activity.colum.HotColumnActivity;
import com.video.yali.ui.activity.colum.RenqiStarActivity;
import com.video.yali.utils.BannerUtils;
import com.video.yali.utils.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class ColumFragment extends BaseFragment implements SimpleImmersionOwner {
    @BindView(R.id.recycle_zhuanji)
    RecyclerView recycleZhuanji;
    @BindView(R.id.xbanner_zhuanlan)
    XBanner mXbanner;
    @BindView(R.id.tv_more_hotstar)
    TextView tvMoreHot;
    @BindView(R.id.recycle_hot_star)
    RecyclerView recycleHot;
    @BindView(R.id.tv_more_renqistar)
    TextView tvMoreRenqi;
    @BindView(R.id.recycle_renqi_star)
    RecyclerView recycleRenqi;
    @BindView(R.id.ll_points)
    LinearLayout llPoints;

    private ColumnModel model;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_column;
    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recycleZhuanji.setLayoutManager(layoutManager);
        recycleHot.setLayoutManager(new GridLayoutManager(context, 4));
        recycleRenqi.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void initData() {
        super.initData();
        model = new ColumnModel();


        initColumnYear();//年度推荐
        initBanner();//轮播图
        initHotColumn();//热门专栏
        initRenqiStar();//人气明星
    }

    private void initBanner() {

        model.columnAd(context, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                ColumnBannerBean columnBannerBean = GsonUtils.fromJson(data, ColumnBannerBean.class);
                List<ColumnBannerBean.ListBean> list = columnBannerBean.getList();
                List<BannerBean> bannerBeans = new ArrayList<>();
                for (ColumnBannerBean.ListBean listBean : list) {
                    bannerBeans.add(new BannerBean(listBean.getImage(), listBean.getTarget_url(),listBean.getType(),listBean.getId()));
                }
                BannerUtils.initBanner(context, mXbanner, bannerBeans, true, llPoints);
            }

            @Override
            public void onError(int code) {

            }
        });
    }


    @OnClick({R.id.tv_more_hotstar, R.id.tv_more_renqistar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more_hotstar:
                ActivityUtils.startActivity(HotColumnActivity.class);
                break;
            case R.id.tv_more_renqistar:
                ActivityUtils.startActivity(RenqiStarActivity.class);
                break;
        }
    }


    private void initRenqiStar() {

        model.columnStar(context, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                StarListBean starListBean = GsonUtils.fromJson(data, StarListBean.class);
                List<StarListBean.ListBean> list = starListBean.getList();
                if (list.size() > 4) {
                    list = list.subList(0, 4);
                }
                RenqiStarAdapter starAdapter = new RenqiStarAdapter(recycleRenqi);
                starAdapter.setData(list);
                recycleRenqi.setAdapter(starAdapter);
            }

            @Override
            public void onError(int code) {

            }
        });


    }

    private void initHotColumn() {

        model.columnHot(context, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();
                HotColumnAdapter hotColumnAdapter = new HotColumnAdapter(recycleHot);
                hotColumnAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                    @Override
                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                        int id = list.get(position).getId();
                        Intent intent = new Intent(context, ColumnListActivity.class);
                        intent.putExtra("id", id);
                        ActivityUtils.startActivity(intent);
                    }
                });
                if (list.size() > 8) {
                    hotColumnAdapter.setData(list.subList(0, 8));
                } else {

                    hotColumnAdapter.setData(list);
                }


                recycleHot.setAdapter(hotColumnAdapter);
            }

            @Override
            public void onError(int code) {

            }
        });
//        List<String> hotData = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            hotData.add("");
//        }
//        HotStarAdapter hotStarAdapter = new HotStarAdapter(recycleHot, R.layout.item_zhuanlan_hotstar);
//        hotStarAdapter.setData(hotData);
//        recycleHot.setAdapter(hotStarAdapter);
    }

    private void initColumnYear() {

        model.columnYear(context, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();
                ZhuanjiAdapter zhuanjiAdapter = new ZhuanjiAdapter(recycleZhuanji);
                zhuanjiAdapter.setData(list);
                recycleZhuanji.setAdapter(zhuanjiAdapter);
            }

            @Override
            public void onError(int code) {

            }
        });

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();

    }

}
