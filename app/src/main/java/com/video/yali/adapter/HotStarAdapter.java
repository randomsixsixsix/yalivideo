package com.video.yali.adapter;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.video.yali.R;
import com.video.yali.bean.StarListBean;
import com.video.yali.widget.BGAImageView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class HotStarAdapter extends BGARecyclerViewAdapter<StarListBean.ListBean> {
    public HotStarAdapter(RecyclerView recyclerView,int type) {
        super(recyclerView, type);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, StarListBean.ListBean model) {
        BGAImageView imageView = (BGAImageView) helper.getImageView(R.id.item_bga_star);
        helper.setText(R.id.tv_item_star,model.getName());
        Glide.with(mContext).load(model.getAvatar()).placeholder(R.mipmap.girl_default).into(imageView);

    }
}
