package com.video.yali.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.video.yali.R;
import com.video.yali.adapter.FragmentAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.MyPromoteBean;
import com.video.yali.bean.UserBean;
import com.video.yali.model.MyModel;
import com.video.yali.ui.fragment.my.PromoteDayFragment;
import com.video.yali.ui.fragment.my.PromoteTaskFragment;
import com.video.yali.ui.fragment.my.PromoteWelfareFragment;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;
import com.video.yali.widget.ColorArcProgressBar;
import com.video.yali.widget.indicatortitles.NavigatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class PromoteActivity extends BaseActivity {

    @BindView(R.id.bar_promote)
    ColorArcProgressBar mColorArcProgressBar;
    @BindView(R.id.indicator_promote)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.vp_promote)
    ViewPager mViewPager;
    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.bt_promote_ok)
    Button btPromoteOk;
    @BindView(R.id.iv_promote_head)
    ImageView ivPromoteHead;
    @BindView(R.id.tv_promote_name)
    TextView tvPromoteName;
    @BindView(R.id.tv_promote_distance)
    TextView tvPromoteDistance;


    private MyModel model = new MyModel();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<BasePagerFragment> fragmentList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_promote;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.text_promote));
        tvTitleRight.setText(getString(R.string.promote_record));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        tvTitleRight.setVisibility(View.VISIBLE);
        mColorArcProgressBar.setCurrentValues(100);

        titles.add(getString(R.string.promote_task1_title));
        titles.add(getString(R.string.promote_task2_title));
        titles.add(getString(R.string.promote_task3_title));

        fragmentList.add(new PromoteTaskFragment());
        fragmentList.add(new PromoteWelfareFragment());
        fragmentList.add(new PromoteDayFragment());

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

        UserBean mUserBean = YlUtils.getUserInfo();
        if (mUserBean != null) {
            Glide.with(this)
                    .load(mUserBean.getAvatar())
                    .apply(centerCropTransform()
                            .placeholder(R.mipmap.default_header_logo)
                            .error(R.mipmap.default_header_logo)
                            .priority(Priority.HIGH)
                            .transform(new GlideCircleTransform())  //圆形头像,自定义类
                            .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                            .skipMemoryCache(false))     //跳过内存缓存
                    .into(ivPromoteHead);
            tvPromoteName.setText(mUserBean.getName());
            tvPromoteDistance.setText(String.format(getString(R.string.my_pomote_diss), mUserBean.getCount_to_next_level()));
        }

    }

    @OnClick({R.id.ib_title_left, R.id.tv_title_right, R.id.bt_promote_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.tv_title_right:
                ToolUtils.startActivity(this, PromoteRecordActivity.class);
                break;
            case R.id.bt_promote_ok:
                ToolUtils.startActivity(this, PromoteCodeActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPromoteTaskNet();
    }

    protected void getPromoteTaskNet() {
//        if (!YlUtils.judgeUserExist()) {
//            return;
//        }
        model.getPromoteTaskData(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                MyPromoteBean mMyPromoteBean = gson.fromJson(data, MyPromoteBean.class);
                if (mMyPromoteBean != null && mMyPromoteBean.getList() != null) {
                    ((PromoteTaskFragment) fragmentList.get(0)).setData(mMyPromoteBean.getList().getPromoteTaskList());
                    ((PromoteWelfareFragment) fragmentList.get(1)).setData(mMyPromoteBean.getList().getWelfareTaskList());
                    ((PromoteDayFragment) fragmentList.get(2)).setData(mMyPromoteBean.getList().getDailyTaskList());
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

}
