package com.video.yali.adapter;

import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.video.yali.R;
import com.video.yali.bean.MySortBean;
import com.video.yali.bean.VideoAboutBean;
import com.video.yali.bean.VideoDetailsBean;
import com.video.yali.utils.GlideRoundTransform;
import com.video.yali.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class VideoAboutAdapter extends BGARecyclerViewAdapter<VideoDetailsBean> {


    public VideoAboutAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_video_about);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, VideoDetailsBean model) {
        helper.setText(R.id.tv_videodetails_item_title, model.getName());
        String numberdesc = String.format(mContext.getString(R.string.video_play_number5), model.getPlay_count());
        helper.setText(R.id.tv_videodetails_item_playnumber, numberdesc);
        TagFlowLayout tflVideodetailsItemLabel = helper.getView(R.id.tfl_videodetails_item_label);
        List<VideoDetailsBean.TagsBean> labelList = model.getTags();
        // 截取labelList
        if (labelList.size() > 3) {
            labelList = labelList.subList(0, 3);
            VideoDetailsBean.TagsBean firstTag = labelList.get(0);
            VideoDetailsBean.TagsBean secondTag = labelList.get(0);
            VideoDetailsBean.TagsBean lastTag = labelList.get(0);
            int totalLength = firstTag.getName().length() + secondTag.getName().length();
            if(totalLength + lastTag.getName().length() > 8){
                labelList = labelList.subList(0, 2);
            }
        }

        if (labelList != null && labelList.size() > 0) {
            tflVideodetailsItemLabel.setVisibility(View.VISIBLE);
            LabelVideoAdapter labelQuickAdapter = new LabelVideoAdapter(labelList);
            tflVideodetailsItemLabel.setAdapter(labelQuickAdapter);
        } else {
            tflVideodetailsItemLabel.setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(model.getPoster())
                .apply(centerCropTransform()
                        .placeholder(R.drawable.default_cover_xhsp)
                        .error(R.drawable.default_cover_xhsp)
                          .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(false))
                .into((ImageView) helper.getView(R.id.iv_videodetails_item_pic));
    }


}
