package com.video.yali.widget.indicatortitles;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.video.yali.R;
import com.video.yali.ui.activity.MainActivity;
import com.video.yali.widget.indicatortitles.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;


import java.util.List;

public class NavigatorAdapter extends CommonNavigatorAdapter {
    private List<String> mDataList;
    private ViewPager mPager;

    public NavigatorAdapter(List<String> mDataList, ViewPager mViewpager) {
        this.mDataList = mDataList;
        mPager = mViewpager;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        // SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
        ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setText(mDataList.get(index));
        simplePagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
        simplePagerTitleView.setSelectedColor(Color.parseColor("#f8dc09"));
        simplePagerTitleView.setTextSize(19.0F);

        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(index);

            }
        });

        return simplePagerTitleView;


    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        //直线型指示器
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight((float) UIUtil.dip2px(context, 2.0D));
        indicator.setLineWidth((float) UIUtil.dip2px(context, 20.0D));
        indicator.setRoundRadius((float) UIUtil.dip2px(context, 2.0D));
        indicator.setStartInterpolator((Interpolator) (new AccelerateInterpolator()));
        indicator.setEndInterpolator((Interpolator) (new DecelerateInterpolator(2.0F)));
        indicator.setColors(Color.parseColor("#f8dc09"));
//        indicator.setColors(Color.parseColor("#FFB41F"));
        return indicator;
    }
}
