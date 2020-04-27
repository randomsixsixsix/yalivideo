package com.video.yali.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.HomeZhuantiAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.BannerBean;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.HomeAdBean;
import com.video.yali.bean.HomeBnnerBean;
import com.video.yali.bean.HomeZhuantiBean;
import com.video.yali.bean.VideoBean;
import com.video.yali.model.HomeDataModel;
import com.video.yali.ui.activity.WebActivity;
import com.video.yali.ui.activity.colum.ColumnListActivity;
import com.video.yali.ui.activity.colum.HotColumnActivity;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.utils.BannerUtils;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.RequestCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;


public class HomePagerFragment extends BasePagerFragment implements OnRefreshListener {
    private LinearLayout llHomeRoot;
    private SmartRefreshLayout mRefresh;
    private HomeDataModel model;

    private int channelid;
    private XBanner xbanner;


    @Override
    protected View getLayoutId() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.fragment_homepager, null);
        llHomeRoot = inflate.findViewById(R.id.ll_home_root);
        mRefresh = inflate.findViewById(R.id.refresh_home);
        return inflate;
    }


    @Override
    protected void initView() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (xbanner != null) {
                xbanner.startAutoPlay();
            }
        } else {
            if (xbanner != null) {
                xbanner.stopAutoPlay();
            }
        }
    }

    @Override
    protected void loadData() {
        if (getArguments() != null) {
            channelid = getArguments().getInt("channelid");
        }
        mRefresh.setOnRefreshListener(this);
        mRefresh.setEnableLoadMore(false);
        model = new HomeDataModel();
        showProgress();
        getHomePageData();


    }

    private void getHomePageData() {
        model.getHomePagerData(context, channelid, new RequestCallback() {
            @Override
            public void onSuccess(String response) {
                dismissProgress();
                mRefresh.finishRefresh();
                try {
                    JSONObject datajson = new JSONObject(response);
                    if (datajson.has("data")) {

                        JSONObject jsonObject1 = datajson.getJSONObject("data");
                        JSONArray children = jsonObject1.getJSONArray("children");
                        LogUtils.i("childrenstring:" + children.toString());
                        llHomeRoot.removeAllViews();
                        for (int i = 0; i < children.length(); i++) {
                            JSONObject jsonObject2 = children.getJSONObject(i);
                            String widget_type = jsonObject2.getString("widget_type");
                            String dataString = jsonObject2.toString();
                            switch (widget_type) {
                                case GlobConstant.SWIPER:
                                    HomeBnnerBean listBean = GsonUtils.fromJson(dataString, HomeBnnerBean.class);
                                    int size = listBean.getData().size();
                                    //LogUtils.i("轮播图的个数:" + size);
                                    addBanner(listBean.getData());

                                    break;
                                case GlobConstant.AD:
                                    HomeAdBean homeAdBean = GsonUtils.fromJson(dataString, HomeAdBean.class);
                                    // LogUtils.i("广告图片:" + homeAdBean.getData().getImage());
                                    addAd(homeAdBean);
                                    break;
                                case GlobConstant.COLUMN:
                                    HomeZhuantiBean homeZhuantiBean = GsonUtils.fromJson(dataString, HomeZhuantiBean.class);
                                    //  LogUtils.i("专题个数:" + homeZhuantiBean.getData().getData().size() + ",专题类型:" + homeZhuantiBean.getData().getName());
                                    addZhuanti(homeZhuantiBean);
                                    break;
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtils.e("json解析错误:" + e.getMessage());
                }

            }

            @Override
            public void onError(int code) {
                dismissProgress();

            }
        });
    }

    private void addZhuanti(HomeZhuantiBean homeZhuantiBean) {
        View zhuantiView = LayoutInflater.from(context).inflate(R.layout.include_zhuanti, null);
        RecyclerView recyclerView = zhuantiView.findViewById(R.id.recycle_zhuanti);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        TextView tvhuanyipi = zhuantiView.findViewById(R.id.tv_huanhyipi);
        TextView tvzhuantiName = zhuantiView.findViewById(R.id.tv_zhuanti_name);
        String column_type = homeZhuantiBean.getData().getColumn_type();
        HomeZhuantiAdapter adapter = null;
        if (column_type.equals("columnH")) {
            recyclerView.setMinimumHeight(SizeUtils.dp2px(440));
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            adapter = new HomeZhuantiAdapter(recyclerView, R.layout.item_home_zhuanti);
        } else if (column_type.equals("columnV")) {
            recyclerView.setMinimumHeight(SizeUtils.dp2px(400));
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            adapter = new HomeZhuantiAdapter(recyclerView, R.layout.item_home_zhuantiv);
        }
        List<VideoBean> listdata = new ArrayList<>();
        adapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                Intent intent = new Intent(context, VideoDetailsActivity.class);
                intent.putExtra(GlobConstant.VIDEOID, homeZhuantiBean.getData().getData().get(position).getId());
                ActivityUtils.startActivity(intent);
            }
        });
        listdata.clear();
        List<HomeZhuantiBean.DataBeanX.DataBean> data = homeZhuantiBean.getData().getData();
        for (HomeZhuantiBean.DataBeanX.DataBean datum : data) {

            if (column_type.equals("columnH")) {
                listdata.add(new VideoBean(datum.getId(), datum.getDuration(), datum.getPoster(), datum.getName()));
            } else if (column_type.equals("columnV")) {
                listdata.add(new VideoBean(datum.getId(), datum.getDuration(), datum.getPoster_v(), datum.getName()));

            }
        }
        adapter.setData(listdata);
        recyclerView.setAdapter(adapter);

        //更多
        tvzhuantiName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = homeZhuantiBean.getData().getId();
                Intent intent = new Intent(context, ColumnListActivity.class);
                intent.putExtra("id", id);
                ActivityUtils.startActivity(intent);

            }
        });

        final int[] page = {1};
        HomeZhuantiAdapter finalAdapter = adapter;
        tvhuanyipi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                page[0]++;
                getColumnMoview(column_type, homeZhuantiBean, page, listdata, finalAdapter);
            }
        });
        tvzhuantiName.setText(homeZhuantiBean.getData().getName());
        llHomeRoot.addView(zhuantiView);
    }

    private void getColumnMoview(String column_type, HomeZhuantiBean homeZhuantiBean, int[] page, List<VideoBean> listdata, HomeZhuantiAdapter adapter) {
        model.getHomeMovies(context, homeZhuantiBean.getData().getId(), page[0], new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                listdata.clear();
                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();
                if (list.size() == 0) {
//                    page[0] = 1;
                    ToastUtils.showLong("没有更多数据了");
//                    getColumnMoview(column_type, homeZhuantiBean, page, listdata, adapter);
                }

                if (list.size() < 6) {
//                    ToastUtils.showLong("没有更多数据了");
                    page[0] = 0;

                }
                for (ColumnListBean.ListBean datum : list) {
                    if (column_type.equals("columnH")) {
                        listdata.add(new VideoBean(datum.getId(), datum.getDuration(), datum.getPoster(), datum.getName()));
                    } else if (column_type.equals("columnV")) {
                        listdata.add(new VideoBean(datum.getId(), datum.getDuration(), datum.getPoster_v(), datum.getName()));

                    }
                    //   listdata.add(new VideoBean(listBean.getId(), listBean.getDuration(), listBean.getPoster(), listBean.getName()));
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onError(int code) {

            }
        });
    }

    private void addAd(HomeAdBean homeAdBean) {
        View adView = LayoutInflater.from(context).inflate(R.layout.include_ad, null);
        ImageView adimg = adView.findViewById(R.id.img_ad);
        adimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeAdBean.DataBean data = homeAdBean.getData();
                int type = data.getType();
                switch (type) {
                    case 1:
                        String target_url = data.getTarget_url();
                        Intent webIntent = new Intent(context, WebActivity.class);
                        webIntent.putExtra("webType", ConstantUtils.adtype_banner);
                        webIntent.putExtra("webUrl", target_url);
                        ActivityUtils.startActivity(webIntent);
                        break;
                    case 2:
                        String target_url3 = data.getTarget_url();
                        Intent intent3 = new Intent();
                        intent3.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(target_url3);
                        intent3.setData(content_url);
                        ActivityUtils.startActivity(intent3);
                        break;
                    case 3://电影
                        Intent intent = new Intent(context, VideoDetailsActivity.class);
                        intent.putExtra(GlobConstant.VIDEOID, Integer.parseInt(data.getTarget_url()));
                        ActivityUtils.startActivity(intent);
                        break;
                    case 4://专题
                        Intent intent2 = new Intent(context, ColumnListActivity.class);
                        intent2.putExtra("id", Integer.parseInt(data.getTarget_url()));
                        ActivityUtils.startActivity(intent2);
                        break;
                }
            }
        });
        Glide.with(context).load(homeAdBean.getData().getImage()).placeholder(R.drawable.default_cover_xhsp).error(R.drawable.default_cover_xhsp).into(adimg);
        llHomeRoot.addView(adView);
    }

    private void addBanner(List<HomeBnnerBean.DataBean> data) {
        View bannerView = LayoutInflater.from(context).inflate(R.layout.include_banner, null);
        xbanner = bannerView.findViewById(R.id.page_xbanner);
        LinearLayout llPoints = bannerView.findViewById(R.id.ll_points);
        List<BannerBean> beans = new ArrayList<>();
        for (HomeBnnerBean.DataBean datum : data) {
            beans.add(new BannerBean(datum.getImage(), datum.getTarget_url(), datum.getType(), datum.getId()));
        }
        BannerUtils.initBanner(context, xbanner, beans, true, llPoints);

        llHomeRoot.addView(bannerView);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        getHomePageData();
    }


    public static HomePagerFragment newInstance(int text) {
        HomePagerFragment fragmentOne = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("channelid", text);
        fragmentOne.setArguments(bundle);
        return fragmentOne;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkGo.getInstance().cancelAll();
    }
}
