package com.video.yali.adapter;


import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.video.yali.base.BaseFragment;
import com.video.yali.base.BasePagerFragment;

import java.util.List;

/**
 * Created buy 风凊扬 on 2019/6/5 0005
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<BasePagerFragment> views;
   // private List<BaseFragment> views;
    public FragmentAdapter(FragmentManager fm, List<BasePagerFragment> list) {
        super(fm);
        this.views = list;
    }



    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    /**
     * 重写该方法，取消调用父类该方法
     * 可以避免在viewpager切换，fragment不可见时执行到onDestroyView，可见时又从onCreateView重新加载视图
     * 因为父类的destroyItem方法中会调用detach方法，将fragment与view分离，（detach()->onPause()->onStop()->onDestroyView()）
     * 然后在instantiateItem方法中又调用attach方法，此方法里判断如果fragment与view分离了，
     * 那就重新执行onCreateView，再次将view与fragment绑定（attach()->onCreateView()->onActivityCreated()->onStart()->onResume()）
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      //  super.destroyItem(container, position, object);
    }




}
