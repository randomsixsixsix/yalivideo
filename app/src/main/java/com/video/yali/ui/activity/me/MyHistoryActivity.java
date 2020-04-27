package com.video.yali.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.event.OrangeEvent;
import com.video.yali.ui.fragment.my.HistoryFragment;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyHistoryActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.indicator_myhistory)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.vp_myhistory)
    ViewPager mViewPager;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    private boolean isEdit=false;


    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<BasePagerFragment> fragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_history;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.my_history));
        tvTitleRight.setText(getString(R.string.text_edit));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        tvTitleRight.setVisibility(View.VISIBLE);
        titles.add(getString(R.string.my_history_today));
        titles.add(getString(R.string.my_history_week));
        titles.add(getString(R.string.my_history_moreearly));

        for (int i = 0; i < 3; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 1);
            fragmentList.add((HistoryFragment) HistoryFragment.instantiate(this, HistoryFragment.class.getName(), bundle));
        }


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


    @OnClick({R.id.ib_title_left, R.id.tv_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_title_right:
                LogUtils.e("发布通知---");
                //发布事件
                isEdit=!isEdit;
                if (isEdit){
                    EventBus.getDefault().post(new OrangeEvent(ConstantUtils.EVENT_HISTORYEDIT_CHOSE));
                    tvTitleRight.setText(getString(R.string.text_cancel));
                }else{
                    EventBus.getDefault().post(new OrangeEvent(ConstantUtils.EVENT_HISTORYEDIT_NO));
                    tvTitleRight.setText(getString(R.string.text_edit));
                }
                break;
        }
    }
}
