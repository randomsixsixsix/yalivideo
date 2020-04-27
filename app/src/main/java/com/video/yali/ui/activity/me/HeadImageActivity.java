package com.video.yali.ui.activity.me;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.video.yali.R;
import com.video.yali.adapter.HeadImageAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.AreaPhoneBean;
import com.video.yali.utils.ConstantUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class HeadImageActivity extends BaseActivity {

    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.rv_headimage)
    RecyclerView mRecyclerView;

    private HeadImageAdapter mQuickAdapter;
    public static int resultCode = 2005;

    @Override
    public int getLayoutId() {
        return R.layout.activity_head_image;
    }

    @Override
    public void initView() {
        ArrayList<String> mData= ConstantUtils.getHeadImages();
        tvTitleMiddle.setText(getString(R.string.select_head_image));
        tvTitleMiddle.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mQuickAdapter = new HeadImageAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setData(mData);
    }

    @Override
    public void initListener() {
        mQuickAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                String headImage = mQuickAdapter.getData().get(position);
                Intent mIntent = new Intent();
                mIntent.putExtra("headImage", headImage);
                setResult(resultCode, mIntent);
                finish();
            }
        });

    }

    @OnClick(R.id.ib_title_left)
    public void onClick() {
        finish();
    }
}
