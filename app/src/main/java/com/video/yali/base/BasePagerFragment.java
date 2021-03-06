package com.video.yali.base;

import android.app.Activity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

import com.blankj.utilcode.util.LogUtils;
import com.video.yali.R;
import com.video.yali.widget.dialog.LoadingDialog;


public abstract class BasePagerFragment extends Fragment {

    protected String TAG = getClass().getSimpleName();
    protected Activity context;
    private View rootView;
    LoadingDialog myDialog;

    protected abstract View getLayoutId();

    protected abstract void initView();


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
    }

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    public boolean isUIVisible;
    protected boolean isLoadCompleted;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
       // LogUtils.e("isVisibleToUser:" + isVisibleToUser);
        if (isVisibleToUser && !isLoadCompleted) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && !isLoadCompleted) {
            // 此处不需要判断isViewCreated，因为这个方法在onCreateView方法之后执行
            lazyLoad();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = getLayoutId();
        ButterKnife.bind(this, rootView);

        // return view;
        isViewCreated = true;
        initView();
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isUIVisible = !hidden;
    }

    protected void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
           // isViewCreated = false;
          //  isUIVisible = false;
            isLoadCompleted = true;
        }
    }


    protected abstract void loadData();

    public boolean onBackPressed() {
        assert getFragmentManager() != null;
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return true;
        }
        return false;
    }


    public void showProgress() {
        myDialog = new LoadingDialog(context, R.style.MyDialog);
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.show();
    }

    public void dismissProgress() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }
}
