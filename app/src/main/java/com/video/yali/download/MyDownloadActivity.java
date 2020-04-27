package com.video.yali.download;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDownloadActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.indicator_mydownload)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_mydownload)
    ViewPager vpMydownload;
    private List<BasePagerFragment> fragmentList;
    private List<String> mDataList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_dowanload;
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    public void initData() {
        super.initData();
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();

        Intent intent = getIntent();
        int selectpage = intent.getIntExtra(GlobConstant.SELECTPAGE, 0);
        fragmentList = new ArrayList<>();
        mDataList = new ArrayList<>();

        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        fragmentList.add(new DownloadingFragment());
        fragmentList.add(new DownloadOkFragment());
        mDataList.add("下载中");
        mDataList.add("已下载");
        vpMydownload.setAdapter(adapter);

        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//设置宽度等分
        commonNavigator.setFollowTouch(true);
        commonNavigator.setAdapter(new NavigatorAdapter(mDataList, vpMydownload));
        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator, vpMydownload);

        magicIndicator.onPageSelected(selectpage);
        vpMydownload.setCurrentItem(selectpage);

    }
}
