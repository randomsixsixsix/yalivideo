package com.video.yali.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.video.yali.R;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.ui.activity.colum.ColumnListActivity;
import com.video.yali.widget.BGAImageView;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class ZhuanjiAdapter extends BGARecyclerViewAdapter<ColumnListBean.ListBean> implements BGAOnRVItemClickListener {
    public ZhuanjiAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_zhuanlan_zhuanji);
        setOnRVItemClickListener(this);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ColumnListBean.ListBean model) {

        BGAImageView imageView = (BGAImageView) helper.getImageView(R.id.item_bga_zhuanji);
        Glide.with(mContext).load(model.getPoster()).placeholder(R.mipmap.girl_default).into(imageView);
        helper.setText(R.id.tv_zhuanji_name, model.getName());
        helper.setText(R.id.tv_zhuanji_time, model.getUpdated_at());
        helper.setText(R.id.tv_zhuanji_info, model.getDescription());
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

        int id = getData().get(position).getId();
        Intent intent = new Intent(mContext, ColumnListActivity.class);
        intent.putExtra("id", id);
        ActivityUtils.startActivity(intent);
    }
}
