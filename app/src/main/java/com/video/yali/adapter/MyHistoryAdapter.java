package com.video.yali.adapter;

import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.bean.HistoryBean;
import com.video.yali.bean.MyLikeBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class MyHistoryAdapter extends BGARecyclerViewAdapter<HistoryBean> {


    public MyHistoryAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_history);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.ll_myhistory_item_chose);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, HistoryBean model) {

        if (isEdit) {
            helper.setVisibility(R.id.ll_myhistory_item_chose, View.VISIBLE);
        } else {
            helper.setVisibility(R.id.ll_myhistory_item_chose, View.GONE);
        }
        helper.setText(R.id.tv_myhistory_item_title, model.getName());
        helper.setText(R.id.tv_myhistory_item_date, model.getViewed_at());
        Glide.with(mContext)
                .load(model.getPoster_v())
                .apply(centerCropTransform()
                        .placeholder(R.drawable.default_cover_xhsp)
                        .error(R.drawable.default_cover_xhsp)
                        //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false))
                .into((ImageView) helper.getView(R.id.iv_myhistory_item_pic));

        if (model.isChose()) {
            helper.setImageResource(R.id.iv_myhistory_item_chose, R.mipmap.select_dui);
        } else {
            helper.setImageResource(R.id.iv_myhistory_item_chose, R.mipmap.select_un);
        }

    }

    private boolean isEdit;

    public void setEditStatue(boolean isEdit) {
        this.isEdit = isEdit;
        this.notifyDataSetChanged();
    }

    public void setChoseOneStatue(int position) {
        boolean chose = mData.get(position).isChose();
        this.mData.get(position).setChose(!chose);
        this.notifyItemChanged(position);
    }

    public void setChoseAllStatue(boolean isAll) {
        for (int i = 0; i < mData.size(); i++) {
            if (isAll) {
                this.mData.get(i).setChose(true);
            } else {
                this.mData.get(i).setChose(false);
            }
        }
        this.notifyDataSetChanged();
    }
}
