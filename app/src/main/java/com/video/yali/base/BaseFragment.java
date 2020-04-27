package com.video.yali.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.components.ImmersionFragment;
import com.video.yali.R;
import com.video.yali.widget.dialog.LoadingDialog;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment extends ImmersionFragment {

    public View view;
    protected Activity context;

    LoadingDialog myDialog;
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity mcontext) {
        super.onAttach(mcontext);
        context = mcontext;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(getActivity());
        initView();
        initData();
        initListener();
    }

    public abstract int getLayoutId();

    public void init() {
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
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
