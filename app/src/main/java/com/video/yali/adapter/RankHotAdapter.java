package com.video.yali.adapter;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.video.yali.R;
import com.video.yali.bean.HotSearchBean;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class RankHotAdapter extends BGARecyclerViewAdapter<HotSearchBean.ListBean> {
    public RankHotAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_rank_hot);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, HotSearchBean.ListBean model) {

        TextView tvMingci = helper.getTextView(R.id.tv_mingci);

        if(position==0){
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no1_label));
        }
        else if (position == 1) {
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no2_label));
        } else if (position == 2) {
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no3_label));
        } else if (position >= 3) {
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_default_label));
        }
        if (position < 3) {
            helper.setText(R.id.tv_mingci, "No." + (position + 1));
        } else {
            helper.setText(R.id.tv_mingci, position + 1 + "");
        }


        helper.setText(R.id.tv_rank_name, model.getKeyword() + "");
        helper.setText(R.id.tv_renqi, "人气值:" + model.getSearch_count());


    }
}
