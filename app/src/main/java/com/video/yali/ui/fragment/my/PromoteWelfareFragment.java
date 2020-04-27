package com.video.yali.ui.fragment.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.video.yali.R;
import com.video.yali.adapter.PromoteTaskAdapter;
import com.video.yali.adapter.PromoteWelfareAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.PromoteDayBean;
import com.video.yali.bean.PromoteTaskBean;
import com.video.yali.bean.PromoteWelfareBean;
import com.video.yali.ui.activity.login.LoginMobileActivity;
import com.video.yali.ui.activity.login.RegisterActivity;
import com.video.yali.utils.ScrollLinearLayoutManager;
import com.video.yali.utils.ToolUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

public class PromoteWelfareFragment extends BasePagerFragment {

    @BindView(R.id.rv_promotewelfare)
    RecyclerView mRecyclerView;
    private PromoteWelfareAdapter mQuickAdapter;
    private ArrayList<PromoteWelfareBean> mData;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_promote2, null);
        return mView;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new ScrollLinearLayoutManager(getActivity()));
        mQuickAdapter = new PromoteWelfareAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_promotewelfare_item_go) {        //选择
                    int taskStatus = mQuickAdapter.getData().get(position).getTask_status();
                    if (taskStatus == 1) {
                        ToolUtils.startActivity(getActivity(), RegisterActivity.class);
                    }
                }
            }
        });
    }


    @Override
    protected void loadData() {
        if (mData != null && mData.size() > 0) {
            mQuickAdapter.setData(mData);
        }
    }

    public void setData(ArrayList<PromoteWelfareBean> mData) {
        this.mData = mData;
        if (mQuickAdapter != null) {
            mQuickAdapter.setData(mData);
        }
    }
}
