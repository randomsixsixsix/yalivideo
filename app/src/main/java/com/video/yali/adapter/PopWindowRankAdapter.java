package com.video.yali.adapter;

import com.video.yali.R;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class PopWindowRankAdapter extends BGARecyclerViewAdapter<String> {


    public PopWindowRankAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_popwindow_rank);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_poprank_item_name);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.setText(R.id.tv_poprank_item_name, model);


    }

    private int selectPosition=-1;
    public void setSelectPosition(int selectIndex) {
        this.selectPosition=selectIndex;
        this.notifyDataSetChanged();
    }

}
