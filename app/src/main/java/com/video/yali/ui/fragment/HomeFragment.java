package com.video.yali.ui.fragment;

import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseFragment;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.HomeDataBean;
import com.video.yali.download.MyDownloadActivity;
import com.video.yali.model.HomeDataModel;
import com.video.yali.ui.activity.home.SearchActivity;
import com.video.yali.ui.activity.home.VideoSearchActivity;
import com.video.yali.ui.activity.me.MyHistoryActivity;
import com.video.yali.utils.RequestCallback;
import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.home_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.home_viewpager)
    ViewPager mPager;
    @BindView(R.id.img_sao)
    ImageView imgSao;
    @BindView(R.id.img_history)
    ImageView imgHistory;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.home_fl_search)
    LinearLayout homeFlSearch;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.tv_home_search)
    TextView tvHomeSearch;
    @BindView(R.id.img_download)
    ImageView imgDownload;
    @BindView(R.id.ll_top)
    LinearLayout llTop;

    private HomeDataModel model;
    private List<HomeDataBean.DataBeanX.ListBean> list;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

    }

    private List<String> mDataList;
    private List<BasePagerFragment> fragmentList;

    @Override
    public void initData() {
        super.initData();

        model = new HomeDataModel();
        mPager.addOnPageChangeListener(this);
        showProgress();
        getHomeData();


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.e("onhidden_homefragment");
        if (!hidden) {
            // getHomeData();
        }
    }

    private void getHomeData() {

        model.getHomeData(context, new RequestCallback() {
            @Override
            public void onSuccess(String response) {
                dismissProgress();
                LogUtils.i("response:" + response);
                HomeDataBean homeDataBean = GsonUtils.fromJson(response, HomeDataBean.class);
                HomeDataBean.DataBeanX data = homeDataBean.getData();
                list = data.getList();

                mDataList = new ArrayList<>();
                fragmentList = new ArrayList<>();

                for (HomeDataBean.DataBeanX.ListBean listBean : list) {
                    mDataList.add(listBean.getName());
                    fragmentList.add(HomePagerFragment.newInstance(listBean.getId()));
                }

                showView();

            }


            @Override
            public void onError(int code) {
                dismissProgress();
            }


        });
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();

    }


    private void showView() {


        FragmentPagerAdapter adapter = new FragmentAdapter(getChildFragmentManager(), fragmentList);

        if (mPager.getAdapter() != null) {
            Class<? extends FragmentManager> aClass = getChildFragmentManager().getClass();
            try {
                Field f = aClass.getDeclaredField("mAdded");
                f.setAccessible(true);
                ArrayList<Fragment> list = (ArrayList) f.get(getChildFragmentManager());
                list.clear();
                f = aClass.getDeclaredField("mActive");
                f.setAccessible(true);
                SparseArray<Fragment> array = (SparseArray) f.get(getChildFragmentManager());
                array.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //将适配器和ViewPager结合
        mPager.setAdapter(adapter);

        final CommonNavigator commonNavigator = new CommonNavigator(context);
        if (mDataList.size() <= 4) {
            commonNavigator.setAdjustMode(true);
        } else {
            commonNavigator.setAdjustMode(false);
        }

        commonNavigator.setFollowTouch(true);
        commonNavigator.setAdapter(new NavigatorAdapter(mDataList, mPager));
        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator, mPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (position == 0) {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
        } else {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            ll2.removeAllViews();
            if (list != null && list.size() > 0) {
                HomeDataBean.DataBeanX.ListBean listBean = list.get(position);
                List<HomeDataBean.DataBeanX.ListBean.CategoriesBean> categories = listBean.getCategories();
                if (categories.size() > 0) {

                    for (int i = 0; i < categories.size(); i++) {

                        if (i < 2) {
                            HomeDataBean.DataBeanX.ListBean.CategoriesBean category = categories.get(i);
                            View view = LayoutInflater.from(context).inflate(R.layout.include_home_text, null);
                            TextView tvSelect = view.findViewById(R.id.tv_select);
                            tvSelect.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, VideoSearchActivity.class);
                                    intent.putExtra(GlobConstant.CHANNELID, list.get(position).getId());
                                    intent.putExtra(GlobConstant.CHANNELNAME, category.getName());
                                    ActivityUtils.startActivity(intent);
                                }
                            });
                            tvSelect.setText(category.getName());
                            ll2.addView(view);
                        }

                    }

                }
            }

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.tv_home_search, R.id.img_download, R.id.img_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_home_search:
                ActivityUtils.startActivity(SearchActivity.class);
                break;
            case R.id.img_download:
                ActivityUtils.startActivity(MyDownloadActivity.class);
                break;
            case R.id.img_history:
                ActivityUtils.startActivity(MyHistoryActivity.class);
                break;
        }
    }
}
