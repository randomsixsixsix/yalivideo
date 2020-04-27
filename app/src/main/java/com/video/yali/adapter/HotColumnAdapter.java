package com.video.yali.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.widget.RoundCornerImageView;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class HotColumnAdapter extends BGARecyclerViewAdapter<ColumnListBean.ListBean> {
    public HotColumnAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_hot_zhuanti);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ColumnListBean.ListBean model) {

        RoundCornerImageView imageView = (RoundCornerImageView) helper.getImageView(R.id.img_tuijian_zhuanti);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.height = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40)) / 4;
        //layoutParams.height = imageView.getWidth();
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext)
                .load(model.getPoster())
                .apply(centerCropTransform()
                        .centerCrop()
                        .placeholder(R.drawable.default_cover_xhsp)
                        .error(R.drawable.default_cover_xhsp)
                        //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false))
                .into(imageView);

        helper.setText(R.id.tv_tuijian_zhuanti_name, model.getName());

    }
}
