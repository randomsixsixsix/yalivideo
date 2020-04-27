package com.video.yali.adapter;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.bean.VideoBean;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.GlideRoundTransform;
import com.video.yali.widget.RoundCornerImageView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class ColumnListAdapter extends BGARecyclerViewAdapter<VideoBean> {
    public ColumnListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_videolist);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, VideoBean model) {
        RoundCornerImageView imageView = (RoundCornerImageView) helper.getImageView(R.id.img_video);
        Glide.with(mContext)
                .load(model.getCorver())
                .apply(centerCropTransform()
                        .placeholder(R.drawable.default_cover_xhsp)
                        .error(R.drawable.default_cover_xhsp)
                        //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false))
                .into(imageView);

        helper.setText(R.id.tv_video_duration, model.getDurcation());
        helper.setText(R.id.tv_video_name, model.getName());
    }
}
