package com.video.yali.adapter;

import android.widget.TextView;

import com.video.yali.R;
import com.video.yali.bean.MyBackBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MyOpinionAdapter2Problem extends BGARecyclerViewAdapter<MyBackBean> {


    public MyOpinionAdapter2Problem(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_myopinion2_problem);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, MyBackBean model) {
        TextView tv_myopinion_item2_title = helper.getView(R.id.tv_myopinion_item2_title);
        tv_myopinion_item2_title.setText(model.getType());
        if (selectPosition == position) {
            tv_myopinion_item2_title.setBackgroundResource(R.drawable.black_cir_select);
        } else {
            tv_myopinion_item2_title.setBackgroundResource(R.drawable.black_cir_nomal);
        }

    }

    private int selectPosition = -1;

    public void setSelelctType(int position) {
        selectPosition = position;
        this.notifyDataSetChanged();
    }
}
