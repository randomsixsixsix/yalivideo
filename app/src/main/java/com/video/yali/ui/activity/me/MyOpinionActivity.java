package com.video.yali.ui.activity.me;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.ui.fragment.my.OpinionFragment1;
import com.video.yali.ui.fragment.my.OpinionFragment2;
import com.video.yali.ui.fragment.my.OpinionFragment3;

import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class MyOpinionActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.indicator_myopinion)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.vp_myopinion)
    ViewPager mViewPager;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<BasePagerFragment> fragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_opinion;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.my_opinion));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        titles.add(getString(R.string.my_opinion_question));
        titles.add(getString(R.string.my_opinion_once));
        titles.add(getString(R.string.my_opinion_return));

        fragmentList.add(new OpinionFragment1());
        fragmentList.add(new OpinionFragment2());
        fragmentList.add(new OpinionFragment3());

        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        //将适配器和ViewPager结合
        mViewPager.setAdapter(adapter);

        final CommonNavigator commonNavigator = new CommonNavigator(this);
        if (titles.size() < 6) {
            commonNavigator.setAdjustMode(true);//设置宽度等分
        } else {
            commonNavigator.setAdjustMode(false);
        }
        commonNavigator.setFollowTouch(true);
        commonNavigator.setAdapter(new NavigatorAdapter(titles, mViewPager));
        mMagicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @OnClick(R.id.ib_title_left)
    public void onClick() {
        finish();
    }

}
