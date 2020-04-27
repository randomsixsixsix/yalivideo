package com.video.yali.ui.activity.me;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.ui.fragment.my.MessageFragment;
import com.video.yali.ui.fragment.my.NotifyFragment;
import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class MyNotifyActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.indicator_mynotify)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.vp_mynotify)
    ViewPager mViewPager;


    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<BasePagerFragment> fragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_notify;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.my_notify));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        titles.add(getString(R.string.my_notify_bulletin));
        titles.add(getString(R.string.my_notify_message));

        fragmentList.add(new NotifyFragment());
        fragmentList.add(new MessageFragment());

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
