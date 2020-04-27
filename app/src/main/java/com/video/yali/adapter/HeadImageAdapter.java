package com.video.yali.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.bean.MyLikeBean;
import com.video.yali.utils.GlideCircleTransform;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class HeadImageAdapter extends BGARecyclerViewAdapter<String> {


    public HeadImageAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_headimage);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, String model) {
        Glide.with(mContext)
                .load(model)
                .apply(centerCropTransform()
                        .placeholder(R.mipmap.default_header_logo)
                        .error(R.mipmap.default_header_logo)
                        .priority(Priority.HIGH)
                        .transform(new GlideCircleTransform())  //圆形头像,自定义类
                        .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                        .skipMemoryCache(false))     //跳过内存缓存
                .into(helper.getImageView(R.id.iv_headimage_item_pic));

    }

}
