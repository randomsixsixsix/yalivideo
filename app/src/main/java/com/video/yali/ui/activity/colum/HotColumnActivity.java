package com.video.yali.ui.activity.colum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.video.yali.R;
import com.video.yali.adapter.HotColumnAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.ColumnListBean;
import com.video.yali.model.ColumnModel;
import com.video.yali.utils.RequestCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class HotColumnActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.recycle_hot_column)
    RecyclerView recycleHotColumn;
    @BindView(R.id.refresh_hot_column)
    SmartRefreshLayout refreshHotColumn;


    private ColumnModel model;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotcolumn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ImmersionBar.with(this).navigationBarColor(R.color.white).navigationBarDarkIcon(true).init();
        recycleHotColumn.setLayoutManager(new GridLayoutManager(this, 4));
        model = new ColumnModel();
        initHotColumn();

    }

    private void initHotColumn() {

        model.columnHot(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                ColumnListBean columnListBean = GsonUtils.fromJson(data, ColumnListBean.class);
                List<ColumnListBean.ListBean> list = columnListBean.getList();
                HotColumnAdapter hotColumnAdapter = new HotColumnAdapter(recycleHotColumn);
                hotColumnAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                    @Override
                    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                        int id = list.get(position).getId();
                        Intent intent = new Intent(HotColumnActivity.this, ColumnListActivity.class);
                        intent.putExtra("id", id);
                        ActivityUtils.startActivity(intent);
                    }
                });
                hotColumnAdapter.setData(list);


                recycleHotColumn.setAdapter(hotColumnAdapter);
            }

            @Override
            public void onError(int code) {

            }
        });
//        List<String> hotData = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            hotData.add("");
//        }
//        HotStarAdapter hotStarAdapter = new HotStarAdapter(recycleHot, R.layout.item_zhuanlan_hotstar);
//        hotStarAdapter.setData(hotData);
//        recycleHot.setAdapter(hotStarAdapter);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        this.finish();
    }
}
