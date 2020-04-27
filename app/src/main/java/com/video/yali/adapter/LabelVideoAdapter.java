package com.video.yali.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.video.yali.R;
import com.video.yali.bean.VideoDetailsBean;
import com.video.yali.widget.flowlayout.FlowLayout;
import com.video.yali.widget.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class LabelVideoAdapter extends TagAdapter<VideoDetailsBean.TagsBean> {
    public LabelVideoAdapter(List<VideoDetailsBean.TagsBean> data) {
        super(data);
    }

    @Override
    public View getView(FlowLayout parent, int position, VideoDetailsBean.TagsBean item) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label_video, parent, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tv_labelvideo_item_name);
        if (!TextUtils.isEmpty(item.getName())) {
            tvLabel.setText(item.getName());
        }
        return view;
    }
}
