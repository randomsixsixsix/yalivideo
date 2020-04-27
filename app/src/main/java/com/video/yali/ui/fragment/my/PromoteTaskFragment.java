package com.video.yali.ui.fragment.my;

import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.video.yali.R;
import com.video.yali.adapter.MyLikeAdapter;
import com.video.yali.adapter.PromoteTaskAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.PromoteDayBean;
import com.video.yali.bean.PromoteTaskBean;
import com.video.yali.utils.ScrollLinearLayoutManager;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class PromoteTaskFragment extends BasePagerFragment {
    @BindView(R.id.rv_promotetask)
    RecyclerView mRecyclerView;
    private PromoteTaskAdapter mQuickAdapter;
    private ArrayList<PromoteTaskBean> mData;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_promote1, null);
        return mView;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new ScrollLinearLayoutManager(getActivity()));
        mQuickAdapter = new PromoteTaskAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

    }

    @Override
    protected void loadData() {
        if (mData!=null && mData.size()>0){
            mQuickAdapter.setData(mData);
        }
    }

    public void setData(ArrayList<PromoteTaskBean> mData) {
        this.mData=mData;
        if (mQuickAdapter!=null){
            LogUtils.e("请求到了数据--"+mData.size());
            mQuickAdapter.setData(mData);
        }
    }
}
