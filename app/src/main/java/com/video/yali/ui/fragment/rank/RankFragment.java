package com.video.yali.ui.fragment.rank;


import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BusUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseFragment;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.EventBean;
import com.video.yali.listener.AppBarStateChangeListener;
import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;


public class RankFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.rank_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.rank_viewpager)
    ViewPager mPager;
    @BindView(R.id.rank_coordinator)
    CoordinatorLayout rankCoordinator;
    @BindArray(R.array.ranktitle)
    String[] ranktitle;
    @BindView(R.id.tv_rank_select)
    TextView title_text;


    @BindView(R.id.rl_title)
    RelativeLayout title;
    @BindView(R.id.rank_appbar)
    AppBarLayout rankAppbar;
    @BindView(R.id.tv_rank_day)
    TextView tvRankDay;
    @BindView(R.id.tv_rank_week)
    TextView tvRankWeek;
    @BindView(R.id.tv_rank_month)
    TextView tvRankMonth;

    private List<BasePagerFragment> fragmentList;
    private List<String> mDataList;
    private String type = GlobConstant.DAY;
    private int currentpage = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rank;
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();

    }

    @Override
    public void initData() {
        super.initData();
        mDataList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        setselect(1);
        setScrollListener();

        for (int i = 0; i < ranktitle.length; i++) {
            mDataList.add(ranktitle[i]);
        }
        fragmentList.add(new RankChildFragment(mDataList.get(0)));
        fragmentList.add(new RankChildFragmentHot());
        fragmentList.add(new RankChildFragment(mDataList.get(2)));
        fragmentList.add(new RankChildFragment(mDataList.get(3)));
        fragmentList.add(new RankChildFragmentStar());

        FragmentPagerAdapter adapter = new FragmentAdapter(getChildFragmentManager(), fragmentList);
        //将适配器和ViewPager结合
        mPager.setAdapter(adapter);

        mPager.addOnPageChangeListener(this);
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

    private void setScrollListener() {
        rankAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int y) {

                if (state == State.EXPANDED) {
                    title.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    title.setVisibility(View.VISIBLE);
                    Animation animation = new AlphaAnimation(0F, 1F);
                    animation.setDuration(2000);
                    title.startAnimation(animation);

                } else {
                    title.setVisibility(View.GONE);
                }
            }
        });
    }


    @OnClick({R.id.tv_rank_day, R.id.tv_rank_week, R.id.tv_rank_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rank_day:
                setselect(1);

                break;
            case R.id.tv_rank_week:
                setselect(2);

                break;
            case R.id.tv_rank_month:
                setselect(3);

                break;
        }
    }

    private void setselect(int flag) {
        tvRankDay.setSelected(false);
        tvRankMonth.setSelected(false);
        tvRankWeek.setSelected(false);
        switch (flag) {
            case 1:
                tvRankDay.setSelected(true);

                title_text.setText(ranktitle[currentpage]+" | 日榜");
                type = GlobConstant.DAY;
                break;
            case 2:
                tvRankWeek.setSelected(true);
                title_text.setText(ranktitle[currentpage]+" | 周榜");
                type = GlobConstant.WEEK;
                break;
            case 3:
                tvRankMonth.setSelected(true);
                title_text.setText(ranktitle[currentpage]+" | 月榜");
                type = GlobConstant.MONTH;
                break;
        }

        LogUtils.i("type:" + type);

        EventBus.getDefault().post(new EventBean(ranktitle[currentpage], type));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentpage = position;
        setselect( 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
