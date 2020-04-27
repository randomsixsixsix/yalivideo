package com.video.yali.adapter;

import com.video.yali.R;
import com.video.yali.bean.MyFeedbackBean;
import com.video.yali.bean.MyOpinionBean;
import com.video.yali.bean.MyOpinionQuestionBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MyOpinionAdapter3 extends BGARecyclerViewAdapter<MyFeedbackBean> {


    public MyOpinionAdapter3(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_myopinion3);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, MyFeedbackBean model) {
        helper.setText(R.id.tv_myopinion_item3_type, model.getType());
        helper.setText(R.id.tv_myopinion_item3_desc, model.getContent());
        helper.setText(R.id.tv_myopinion_item3_date, model.getFeedback_time());

    }

}
