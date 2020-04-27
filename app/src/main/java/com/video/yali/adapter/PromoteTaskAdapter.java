package com.video.yali.adapter;

import com.video.yali.R;
import com.video.yali.bean.MyOpinionQuestionBean;
import com.video.yali.bean.PromoteTaskBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class PromoteTaskAdapter extends BGARecyclerViewAdapter<PromoteTaskBean> {


    public PromoteTaskAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_promote_task);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, PromoteTaskBean model) {
        if (position == 0) {
            helper.setBackgroundRes(R.id.iv_promotetask_item_pic, R.mipmap.icon_level_first);
        } else if (position == 1) {
            helper.setBackgroundRes(R.id.iv_promotetask_item_pic, R.mipmap.icon_level_second);
        } else if (position == 2) {
            helper.setBackgroundRes(R.id.iv_promotetask_item_pic, R.mipmap.icon_level_third);
        }
        helper.setText(R.id.tv_promotetask_item_title, model.getName());
        helper.setText(R.id.tv_promote_task1_desc, model.getRules());

    }

}
