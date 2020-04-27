package com.video.yali.adapter;

import com.video.yali.R;
import com.video.yali.bean.MyLikeBean;
import com.video.yali.bean.MyNotifyBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MyNotifyAdapter extends BGARecyclerViewAdapter<MyNotifyBean> {


    public MyNotifyAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_mynotify);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, MyNotifyBean model) {
        helper.setText(R.id.tv_mynotify_item_title, model.getTitle());
        helper.setText(R.id.tv_mynotify_item_date, model.getDescription());
        if (model.isIs_read()) {     //已读
            helper.setImageResource(R.id.iv_mynotify_item_pic, R.mipmap.notify_isread_logo);
        } else {     //未读
            helper.setImageResource(R.id.iv_mynotify_item_pic, R.mipmap.notify_logo);
        }
    }

    public void setIsReadStatue(int readPosition) {
        this.mData.get(readPosition).setIs_read(true);
        this.notifyItemChanged(readPosition);
    }
}
