package com.video.yali.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.video.yali.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp;
    private HorizontalScrollView hsv;
    private LinearLayout container;
    private String[] menus = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技",
            "财经", "时尚","头条", "社会", "国内", "国际", "娱乐", "体育"};
    private String[] strs = {"111111111111", "222222222222222222",
            "3333333333333333333", "444444444444444444", "555555555555555555",
            "66666666666666666", "777777777777777777", "444444444444444444",
            "555555555555555555", "66666666666666666", "777777777777777777"};
    private String[] strs2 = {"222222222222222222", "3333333333333333333",
            "444444444444444444", "555555555555555555", "66666666666666666",
            "777777777777777777"};
    // 手机屏幕宽度
    private int screenW;
    private ListView lv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenW = dm.widthPixels;

        initMenu();
        initViewPager();
    }

    /**
     * 初始化菜单
     *
     * @author Zoe
     * @date 2019-6-22下午6:16:53
     */
    private void initMenu() {
        // 获取布局文件中的HorizontalScrollView
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        // 获取HorizontalScrollView中的为一个child，Linearlayout
        container = (LinearLayout) findViewById(R.id.ll_hor);
        // 布局参数，给Linearlayout中的TextView使用
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(10, 10, 10, 10);
        // 向Linerlayout布局中添加控件
        for (int i = 0; i < menus.length; i++) {
            TextView tv = new TextView(this);
            tv.setId(i);
            tv.setText(menus[i].toString());
            tv.setTextSize(14);
            tv.setGravity(Gravity.CENTER);
            // 使用了上方的布局参数，TextView之间有对应的Margin值
            tv.setLayoutParams(lp);
            // 设置第一个菜单（TextView）的初始状态
            if (i == 0) {
                tv.setTextColor(Color.RED);
                tv.setTextSize(16);
            }
            // 给菜单（TextView）设置点击事件
            tv.setOnClickListener(this);
            // 循环，将每个新创建的TextView添加到HorizontalScrollView中的Linearlayout中
            container.addView(tv);
        }
    }


    /**
     * 初始化ViewPager
     *
     * @author Zoe
     * @date 2019-6-22下午6:16:19
     */
    @SuppressLint("NewApi")
    public void initViewPager() {
        vp = (ViewPager) findViewById(R.id.vp);
        // 将view填充到ViewPager
        vp.setAdapter(new PagerAdapter() {

            @SuppressLint({"NewApi", "InflateParams"})
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // 将view添加到viewPager中（view用以显示我们想要显示布局或数据，图片，文字等）
                LayoutInflater inflater = getLayoutInflater();
                // View view = new View(MainActivity.this);
                // 将自定义的布局填充到新的View中
                View view = inflater.inflate(R.layout.layout_main, null);
                // 将需要加载的数据添加到ListView中（网络数据，自定义数据）
                initListView(view, position);
                // 将新的View添加到ViewPager中
                container.addView(view, 0);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // 下面的方法可以移除ViewPager中的所有View，但这里destoryItem中是空的。需要删除destroyItem中的super...，否则会报错
                // container.removeAllViews();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // 菜单（TextView）的数量
                return menus.length;
            }
        });

        // 页面跳转监听
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                selectTab(arg0);
            }

            // position 当前所在页面
            // positionOffset 当前所在页面偏移百分比
            // positionOffsetPixels 当前所在页面偏移量
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

    }

    /**
     * 导航栏子项点击事件
     *
     * @param v
     * @author Zoe
     * @date 2019-6-23下午4:08:11
     */
    @Override
    public void onClick(View v) {
        // 每个菜单的点击，使每个菜单的位置与ViewPager中的View的位置保持一致，为实现联动做基础
        vp.setCurrentItem(v.getId());
    }

    /**
     * 导航栏滑动
     *
     * @param position
     * @author Zoe
     * @date 2019-6-23上午11:37:28
     */
    private void selectTab(int position) {
        // HorizontalScrollView中只有一个child即Linearlayout，所以可以通过下面的方法获取
        LinearLayout layout = (LinearLayout) hsv.getChildAt(0);
        // 循环Linearlayout中的每一个菜单(TextView)
        for (int i = 0; i < layout.getChildCount(); i++) {
            TextView childAt = (TextView) layout.getChildAt(position);
            // 字体的长度；
            int k = childAt.getMeasuredWidth();
            // 从字体到屏幕左边的距离；
            int l = childAt.getLeft();

            int s = l + k / 2 - screenW / 2;
            hsv.smoothScrollTo(s, 0);

            TextView child = (TextView) layout.getChildAt(i);
            if (position == i) {
                child.setTextColor(Color.RED);
                child.setTextSize(16);
            } else {
                child.setTextColor(Color.BLACK);
                child.setTextSize(14);
            }
        }
    }

    /**
     * 初始化首页的ListView
     * 获取网络数据添加到ListView
     *
     * @param view
     * @param position
     * @author Zoe
     * @date 2019-6-23下午8:55:49
     */
    public void initListView(View view, int position) {
        ArrayAdapter<String> adapter;
        if (position % 2 == 0) {
            adapter = new ArrayAdapter<String>(TestActivity.this,
                    android.R.layout.simple_list_item_1, strs);
        } else {
            adapter = new ArrayAdapter<String>(TestActivity.this,
                    android.R.layout.simple_list_item_1, strs2);
        }
        lv_show = (ListView) view.findViewById(R.id.lv_show);
        // TODO 将网络数据添加到listView中
        lv_show.setAdapter(adapter);
        // ----------------------------样例结束
        // 设置listView的子项点击事件
        //lv_show.setOnItemClickListener(MainActivity.this);

    }
}
