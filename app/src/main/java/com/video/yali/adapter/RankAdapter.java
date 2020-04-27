package com.video.yali.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.bean.VideoBean;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.widget.RoundCornerImageView;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class RankAdapter extends BGARecyclerViewAdapter<VideoBean> implements BGAOnRVItemClickListener {

    private String title;

    public RankAdapter(RecyclerView recyclerView, String title) {
        super(recyclerView, R.layout.item_rank1);
        this.title = title;
        setOnRVItemClickListener(this);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, VideoBean model) {
        RelativeLayout rlRank1 = helper.getView(R.id.rl_rank1);
        RelativeLayout rlRank2 = helper.getView(R.id.rl_rank2);

        TextView tvMingci1 = helper.getTextView(R.id.tv_mingci1);
        TextView tvMingci2 = helper.getTextView(R.id.tv_mingci2);
        if (position == 0) {
            rlRank1.setVisibility(View.VISIBLE);
            rlRank2.setVisibility(View.GONE);
            helper.setText(R.id.tv_mingci1, "No." + (position + 1));
            helper.setText(R.id.tv_name, model.getName());
            helper.setText(R.id.tv_video_info, "播放量: " + model.getBofang() + "   下载量: " + model.getXiazai() + "  收藏量: " + model.getShoucang());
            RoundCornerImageView imageView = (RoundCornerImageView) helper.getImageView(R.id.img_rank_top1);
            TextView tvVideotime = helper.getTextView(R.id.tv_video_time);
            if (title.equals("新片榜")) {
                tvVideotime.setVisibility(View.VISIBLE);
                tvVideotime.setText("上架时间: " + model.getTime());
                helper.setText(R.id.tv_video_info, "播放量: " + model.getBofang());
            } else if (title.equals("撸sir榜")) {
                helper.setText(R.id.tv_video_info, "播放量: " + model.getBofang() + "  收藏量: " + model.getShoucang());
                tvVideotime.setVisibility(View.INVISIBLE);
            } else {

                tvVideotime.setVisibility(View.INVISIBLE);
            }
            Glide.with(mContext)
                    .load(model.getCorver())
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.default_cover_xhsp)
                            .error(R.drawable.default_cover_xhsp)
                            //  .transform(new CenterCrop(),new GlideRoundTransform(mContext,3))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .skipMemoryCache(false))
                    .into(imageView);
        } else {
            rlRank1.setVisibility(View.GONE);
            rlRank2.setVisibility(View.VISIBLE);
            if (position < 3) {
                helper.setText(R.id.tv_mingci2, "No." + (position + 1));
            } else {

                helper.setText(R.id.tv_mingci2, position + 1 + "");

            }
            TextView tvVideotime2 = helper.getTextView(R.id.tv_video_time2);
            TextView tvVideoPlay = helper.getTextView(R.id.tv_video_play);
            if (title.equals("新片榜")) {
                tvVideotime2.setVisibility(View.VISIBLE);
                tvVideotime2.setText("上架时间: " + model.getTime());
                tvVideoPlay.setVisibility(View.GONE);
                helper.setText(R.id.tv_video_info2, "播放量: " + model.getBofang());
            } else if (title.equals("撸sir榜")) {
                tvVideotime2.setVisibility(View.GONE);
                helper.setText(R.id.tv_video_info2, "播放量: " + model.getBofang() + "  收藏量: " + model.getShoucang());
                tvVideoPlay.setVisibility(View.GONE);
            } else {
                tvVideotime2.setVisibility(View.GONE);
                tvVideoPlay.setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_video_info2, "下载量: " + model.getXiazai() + "  收藏量: " + model.getShoucang());
                tvVideoPlay.setText("播放量: " + model.getBofang());
            }

            helper.setText(R.id.tv_video_name2, model.getName());

            RoundCornerImageView imageView = (RoundCornerImageView) helper.getImageView(R.id.img_rank2);
            Glide.with(mContext)
                    .load(model.getCorver())
                    .apply(centerCropTransform()
                            .placeholder(R.drawable.default_cover_xhsp)
                            .error(R.drawable.default_cover_xhsp)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .skipMemoryCache(false))
                    .into(imageView);
        }


        if (position == 1) {
            tvMingci2.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no2_label));
        } else if (position == 2) {
            tvMingci2.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_no3_label));
        } else if (position >= 3) {
            tvMingci2.setBackground(mContext.getResources().getDrawable(R.drawable.ranked_default_label));
        }


    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

        Intent intent = new Intent(mContext, VideoDetailsActivity.class);
        intent.putExtra(GlobConstant.VIDEOID, getData().get(position).getId());
        ActivityUtils.startActivity(intent);
    }
}
