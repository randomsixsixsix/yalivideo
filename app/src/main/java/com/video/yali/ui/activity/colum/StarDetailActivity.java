package com.video.yali.ui.activity.colum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.adapter.StarMoviesAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.bean.StarDetailsBean;
import com.video.yali.model.ColumnModel;
import com.video.yali.ui.activity.home.VideoDetailsActivity;
import com.video.yali.utils.RequestCallback;
import com.video.yali.widget.BGAImageView;
import com.video.yali.widget.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

/**
 * 明星详情
 */
public class StarDetailActivity extends BaseActivity {
    @BindView(R.id.img_star)
    ImageView imgStar;
    @BindView(R.id.bga_star)
    BGAImageView bgaStar;
    @BindView(R.id.tv_video_counts)
    TextView tvVideoCounts;
    @BindView(R.id.tv_star_info)
    TextView tvStarInfo;
    @BindView(R.id.tv_star_name)
    TextView tvStarName;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_play_most)
    TextView tvPlayMost;
    @BindView(R.id.tv_refresh_current)
    TextView tvRefreshCurrent;
    @BindView(R.id.recycle_start_video)
    RecyclerView recycleStartVideo;
    @BindView(R.id.tv_star_desc)
    ExpandableTextView tvStarDesc;
    private int starid;

    private ColumnModel model;
    private int sort = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_star_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
        recycleStartVideo.setLayoutManager(new LinearLayoutManager(this));
        starid = getIntent().getIntExtra("starid", 1);
        tvRefreshCurrent.setSelected(true);
        model = new ColumnModel();
        starDetails();
        starVideos();
    }

    private void starVideos() {
        model.starVideos(this, starid, sort, new RequestCallback() {
            @Override
            public void onSuccess(String data) {

                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();
                StarMoviesAdapter mAdapter = new StarMoviesAdapter(recycleStartVideo);
                mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                    @Override
                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                        Intent intent = new Intent(StarDetailActivity.this, VideoDetailsActivity.class);
                        intent.putExtra(GlobConstant.VIDEOID, list.get(position).getId());
                        ActivityUtils.startActivity(intent);
                    }
                });
                mAdapter.setData(list);
                recycleStartVideo.setAdapter(mAdapter);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void starDetails() {
        model.starDetails(this, starid, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                StarDetailsBean starDetailsBean = GsonUtils.fromJson(data, StarDetailsBean.class);
                String avatar = starDetailsBean.getAvatar();
                String name = starDetailsBean.getName();
                int height = starDetailsBean.getHeight();
                String cup = starDetailsBean.getCup();
                String dimension = starDetailsBean.getDimension();
                String biography = starDetailsBean.getBiography();
                int movie_count = starDetailsBean.getMovie_count();

                Glide.with(StarDetailActivity.this).load(avatar).into(bgaStar);
                Glide.with(StarDetailActivity.this).load(avatar).into(imgStar);

                tvStarName.setText(name);
                tvVideoCounts.setText(movie_count + "部影片");
                tvStarInfo.setText("身高:" + height + "cm\t罩杯:" + cup + "杯");
                tvStarDesc.setText(biography);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.tv_play_most, R.id.tv_refresh_current})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_play_most:
                sort = 0;
                tvPlayMost.setSelected(true);
                tvRefreshCurrent.setSelected(false);
                starVideos();
                break;
            case R.id.tv_refresh_current:
                sort = 1;
                tvPlayMost.setSelected(false);
                tvRefreshCurrent.setSelected(true);
                starVideos();
                break;
        }
    }
}
