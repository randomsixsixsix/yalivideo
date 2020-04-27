package com.video.yali.ui.fragment.my;

import android.view.LayoutInflater;
import android.view.View;

import com.video.yali.R;
import com.video.yali.base.BasePagerFragment;

public class MessageFragment extends BasePagerFragment {

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        return mView;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }
}
