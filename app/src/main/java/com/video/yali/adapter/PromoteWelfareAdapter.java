package com.video.yali.adapter;

import com.video.yali.R;
import com.video.yali.bean.MyOpinionQuestionBean;
import com.video.yali.bean.PromoteWelfareBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class PromoteWelfareAdapter extends BGARecyclerViewAdapter<PromoteWelfareBean> {


    public PromoteWelfareAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_promote_welfare);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_promotewelfare_item_go);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, PromoteWelfareBean model) {
        helper.setText(R.id.tv_promotewelfare_item_title, model.getName());
        helper.setText(R.id.tv_promotewelfare_item_desc, model.getDescription());
        helper.setText(R.id.tv_promotewelfare_item_go, model.getTask_status_name());
        if (model.getTask_status() == 1) {
            helper.setBackgroundRes(R.id.tv_promotewelfare_item_go, R.drawable.yellow_cir);
        } else {
            helper.setBackgroundRes(R.id.tv_promotewelfare_item_go, R.drawable.gray_cir5);
        }

    }

}
