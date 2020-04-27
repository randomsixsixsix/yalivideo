package com.video.yali.adapter;

import android.view.View;

import com.video.yali.R;
import com.video.yali.bean.MySortBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MySortAdapter extends BGARecyclerViewAdapter<MySortBean> {


    public MySortAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_mysort);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, MySortBean model) {
        helper.setText(R.id.tv_mysort_item_title, model.getTitle());
        helper.setImageResource(R.id.iv_mysort_item_pic, model.getPic());

//        if (!showVipView&&position==mData.size()-1){
//            helper.setVisibility(R.id.ll_mysort_item_all, View.GONE);
//        }else{
//            helper.setVisibility(R.id.ll_mysort_item_all, View.VISIBLE);
//        }

    }


    private boolean showVipView;
    public void setShowOpenVipView(boolean isShow) {
        this.showVipView=isShow;
        this.notifyItemChanged(mData.size()-1);
    }
}
