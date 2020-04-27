package com.video.yali.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.StarListBean;
import com.video.yali.bean.VideoBean;
import com.video.yali.model.ColumnModel;
import com.video.yali.ui.activity.colum.StarDetailActivity;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.utils.RequestCallback;
import com.video.yali.widget.BGAImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class RenqiStarAdapter extends BGARecyclerViewAdapter<StarListBean.ListBean> {

    private ColumnModel model;

    public RenqiStarAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_zhuanlan_renqistar);
        model = new ColumnModel();
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, StarListBean.ListBean model) {

        BGAImageView imageView = (BGAImageView) helper.getImageView(R.id.item_bga_renqistar);
        Glide.with(mContext).load(model.getAvatar()).placeholder(R.mipmap.girl_default).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, StarDetailActivity.class);
                intent.putExtra("starid", getData().get(position).getId());
                ActivityUtils.startActivity(intent);
            }
        });

        helper.getTextView(R.id.tv_video_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, StarDetailActivity.class);
                intent.putExtra("starid", getData().get(position).getId());
                ActivityUtils.startActivity(intent);
            }
        });
        helper.setText(R.id.tv_star_info, model.getBiography());
        helper.setText(R.id.tv_star_name, model.getName());
        helper.setText(R.id.tv_video_count, model.getMovie_count() + "部影片");
        RecyclerView mrecycle = helper.getView(R.id.recycle_item_renqi);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mrecycle.setLayoutManager(layoutManager);
        getStartVideos(model.getId(), mrecycle);

    }

    public void getStartVideos(int starid,RecyclerView mRecycle) {

        model.starVideos(mContext, starid, 0, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();
                List<VideoBean> videolist = new ArrayList<>();
                for (ColumnListBean.ListBean listBean : list) {
                    videolist.add(new VideoBean(listBean.getId(), listBean.getDuration(), listBean.getPoster_v(), listBean.getName()));
                }

                RenqiStarFilmAdapter filmAdapter = new RenqiStarFilmAdapter(mRecycle);
                filmAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                    @Override
                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                        Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                        intent.putExtra(GlobConstant.VIDEOID, videolist.get(position).getId());
                        ActivityUtils.startActivity(intent);

                    }
                });
                filmAdapter.setData(videolist);
                mRecycle.setAdapter(filmAdapter);
            }

            @Override
            public void onError(int code) {

            }
        });

    }
}
