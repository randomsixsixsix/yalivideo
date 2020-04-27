package com.video.yali.adapter;

import android.view.View;

import com.blankj.utilcode.util.CacheMemoryUtils;
import com.video.yali.R;
import com.video.yali.utils.ToolUtils;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class MySettingAdapter extends BGARecyclerViewAdapter<String> {


    public MySettingAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_mysetting);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, String model) {
        helper.setText(R.id.tv_mysetting_item_title, model);
        if (position == 0 || position == 1 || position == 3) {
            helper.setVisibility(R.id.view_mysetting_item_line1, View.VISIBLE);
            helper.setVisibility(R.id.view_mysetting_item_line2, View.GONE);
        } else {
            helper.setVisibility(R.id.view_mysetting_item_line1, View.GONE);
            helper.setVisibility(R.id.view_mysetting_item_line2, View.VISIBLE);
        }

        if (position == 3) {
            helper.setText(R.id.tv_mysetting_item_context, "12M");
            helper.setVisibility(R.id.iv_mysetting_item_rightlogo, View.GONE);
        } else if (position == 4) {
            helper.setText(R.id.tv_mysetting_item_context, mContext.getString(R.string.language_china));
            helper.setVisibility(R.id.iv_mysetting_item_rightlogo, View.VISIBLE);
        } else if (position == 5) {
            helper.setText(R.id.tv_mysetting_item_context, ToolUtils.getVersion(mContext));
            helper.setVisibility(R.id.iv_mysetting_item_rightlogo, View.GONE);
        } else {
            helper.setText(R.id.tv_mysetting_item_context, "");
            helper.setVisibility(R.id.iv_mysetting_item_rightlogo, View.VISIBLE);
        }


    }


}
