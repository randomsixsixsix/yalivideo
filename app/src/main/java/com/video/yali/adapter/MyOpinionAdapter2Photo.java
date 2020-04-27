package com.video.yali.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.video.yali.R;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.ToolUtils;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MyOpinionAdapter2Photo extends BGARecyclerViewAdapter<String> {


    public MyOpinionAdapter2Photo(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_myopinion2_photo);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, String model) {
        ImageView iv_myopinion_item2_photo = helper.getView(R.id.iv_myopinion_item2_photo);
        if (position == mData.size() - 1) {
            iv_myopinion_item2_photo.setImageResource(R.mipmap.release_icon_add);
        } else {
            Bitmap bitmap = ToolUtils.getLoacalBitmap(model); //从本地取图片(在cdcard中获取)
            iv_myopinion_item2_photo.setImageBitmap(bitmap); //设置Bitmap
        }

        LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sp_params.width = ToolUtils.getScreenWidth(mContext) * 1 / 4;
        sp_params.height = ToolUtils.getScreenWidth(mContext) * 1 / 4;
        iv_myopinion_item2_photo.setLayoutParams(sp_params);

    }

}
