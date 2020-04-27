package com.video.yali.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.video.yali.R;
import com.video.yali.bean.StarListBean;
import com.video.yali.ui.activity.colum.StarDetailActivity;
import com.video.yali.widget.BGAImageView;
import com.video.yali.widget.RatingBar;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class RankStarAdapter extends BGARecyclerViewAdapter<StarListBean.ListBean> implements BGAOnRVItemClickListener {
    public RankStarAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_rank_star);
        setOnRVItemClickListener(this);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, StarListBean.ListBean model) {
        TextView tvMingci = helper.getTextView(R.id.tv_mingci);
        if(position==0){
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no1_label));
        }
        if (position == 1) {
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no2_label));
        } else if (position == 2) {
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no3_label));
        } else if (position >= 3) {
            tvMingci.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_default_label));
        }
        if (position < 3) {
            helper.setText(R.id.tv_mingci, "No." + (position + 1));
        } else {
            helper.setText(R.id.tv_mingci, position + 1 + "");
        }


        BGAImageView imghead = (BGAImageView) helper.getImageView(R.id.img_star_head);

        Glide.with(mContext).load(model.getAvatar()).placeholder(R.mipmap.girl_default).into(imghead);
        RatingBar view = helper.getView(R.id.star_count);
        view.setSelectedNumber(model.getScore());

        helper.setText(R.id.tv_star_name, model.getName());
        helper.setText(R.id.tv_star_videocount, model.getCount() + "部影片");
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

        Intent intent = new Intent(mContext, StarDetailActivity.class);

        intent.putExtra("starid", getData().get(position).getStar_id());
        ActivityUtils.startActivity(intent);
    }
}
