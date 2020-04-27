package com.video.yali.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.bean.MyLikeBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class MyLikeAdapter extends BGARecyclerViewAdapter<MyLikeBean> {


    public MyLikeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_mylike);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, MyLikeBean model) {
        helper.setText(R.id.tv_mylike_item_title, model.getName());
        helper.setText(R.id.tv_mylike_item_date, model.getJoined_at());
        Glide.with(mContext)
                .load(model.getPoster_v())
                .apply(centerCropTransform()
                        .placeholder(R.drawable.default_cover_xhsp)
                        .error(R.drawable.default_cover_xhsp)
                        //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false))
                .into((ImageView) helper.getView(R.id.iv_mylike_item_pic));
    }

}
