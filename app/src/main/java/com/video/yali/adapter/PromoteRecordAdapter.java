package com.video.yali.adapter;

import com.video.yali.R;
import com.video.yali.bean.PromoteRecordBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class PromoteRecordAdapter extends BGARecyclerViewAdapter<PromoteRecordBean> {


    public PromoteRecordAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_promote_record);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, PromoteRecordBean model) {
        String code = (position + 1) + "." + String.format(mContext.getResources().getString(R.string.invite_code), model.getPromotionCode());
        helper.setText(R.id.tv_promoterecord_item_code, code);
        helper.setText(R.id.tv_promoterecord_item_type, model.getPromotionType());
        helper.setText(R.id.tv_promoterecord_item_time, model.getPromotionTime());

    }

}
