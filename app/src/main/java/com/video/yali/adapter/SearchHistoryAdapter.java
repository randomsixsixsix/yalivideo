package com.video.yali.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.video.yali.R;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class SearchHistoryAdapter extends BGARecyclerViewAdapter<String> {
    public SearchHistoryAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_searhistory);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String model) {

        helper.setText(R.id.tv_history, model);
    }
}
