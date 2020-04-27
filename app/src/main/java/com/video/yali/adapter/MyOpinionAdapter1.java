package com.video.yali.adapter;

import com.video.yali.R;
import com.video.yali.bean.MyOpinionQuestionBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MyOpinionAdapter1 extends BGARecyclerViewAdapter<MyOpinionQuestionBean> {


    public MyOpinionAdapter1(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_myopinion1);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, MyOpinionQuestionBean model) {
        helper.setText(R.id.tv_myopinion_item1_title, model.getQuestion());
        helper.setText(R.id.tv_myopinion_item1_date, model.getDescription());

    }

}
