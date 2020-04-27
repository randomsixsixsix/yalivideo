package com.video.yali.ui.fragment.my;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.video.yali.R;
import com.video.yali.adapter.PromoteDayAdapter;
import com.video.yali.adapter.PromoteTaskAdapter;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.PromoteDayBean;
import com.video.yali.bean.PromoteTaskBean;
import com.video.yali.ui.activity.login.RegisterActivity;
import com.video.yali.ui.activity.me.PromoteCodeActivity;
import com.video.yali.utils.ScrollLinearLayoutManager;
import com.video.yali.utils.ToolUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

public class PromoteDayFragment extends BasePagerFragment {

    @BindView(R.id.rv_promoteday)
    RecyclerView mRecyclerView;
    private PromoteDayAdapter mQuickAdapter;
    private ArrayList<PromoteDayBean> mData;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_promote3, null);
        return mView;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new ScrollLinearLayoutManager(getActivity()));
        mQuickAdapter = new PromoteDayAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);

        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_promoteday_item_go) {        //选择
                    int taskStatus = mQuickAdapter.getData().get(position).getTask_status();
                    if (taskStatus == 1) {
                        ToolUtils.startActivity(getActivity(), PromoteCodeActivity.class);
                    }
                }
            }
        });
    }

    @Override
    protected void loadData() {
        if (mData!=null&&mData.size()>0){
            mQuickAdapter.setData(mData);
        }
    }

    public void setData(ArrayList<PromoteDayBean> mData) {
        this.mData=mData;
        if (mQuickAdapter!=null){
            mQuickAdapter.setData(mData);
        }
    }
}
