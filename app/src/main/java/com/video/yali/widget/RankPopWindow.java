package com.video.yali.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.video.yali.R;
import com.video.yali.adapter.PopWindowRankAdapter;
import com.video.yali.bean.AreaPhoneBean;
import com.video.yali.ui.activity.me.PersonalActivity;
import com.video.yali.ui.activity.me.PswdModifyActivity;
import com.video.yali.ui.activity.me.SettingActivity;
import com.video.yali.utils.ToolUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;


public class RankPopWindow extends PopupWindow {

    private Activity activity;
    private ArrayList<String> mData;
    public PopWindowRankAdapter mQuickAdapter;
    private int rankIndex=-1;

    public RankPopWindow(final Activity activity, ArrayList<String> mData) {
        this.activity = activity;
        this.mData = mData;
        initView();
    }

    public void initView() {
        View view = View.inflate(activity, R.layout.popupwindow_rank, null);
        RecyclerView mRecyclerView = view.findViewById(R.id.rv_rankpopwindow);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mQuickAdapter = new PopWindowRankAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setData(mData);
        mQuickAdapter.setSelectPosition(rankIndex); //初始化选择位置

        this.setContentView(view);
        if (mData.size()>5){
            this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            this.setHeight(ToolUtils.getScreenHeidth(activity)/3);
        }else{
            this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        this.setFocusable(true);
        this.setTouchable(true);
        this.update();

        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_poprank_item_name) {
                    if (onClickListener != null) {
                        onClickListener.onItemClick(position);
                    }
                }

            }
        });


    }

    public void showPopWindow(View parent, int x, int y) {
        if (!this.isShowing()) {
            //showAsDropDown()显示在一个参照物View的周围
            this.showAsDropDown(parent, x, y);
        } else {
            this.dismiss();
        }
    }


    private OnClickListener onClickListener;

    public void setOnItemClickListener(OnClickListener listener) {
        onClickListener = listener;
    }

    public interface OnClickListener {
        void onItemClick(int position);

    }

    @Override
    public void dismiss() {
        ToolUtils.backgroundAlpha(activity, 1.0f);
        super.dismiss();
    }

}
