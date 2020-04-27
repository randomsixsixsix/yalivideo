package com.video.yali.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.bean.EventBean;
import com.video.yali.bean.LabelBean;

import org.greenrobot.eventbus.EventBus;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class SearchLabelAdapter extends BGARecyclerViewAdapter<LabelBean> {
    private int flag;
    public SearchLabelAdapter(RecyclerView recyclerView,int flag) {
        super(recyclerView, R.layout.item_search_label);
        this.flag=flag;
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, LabelBean bean) {

        TextView tvLabel = helper.getTextView(R.id.tv_search_label);
        tvLabel.setText(bean.getContent());
        if (bean.getStatus() == 0) {
            tvLabel.setSelected(false);
        } else {
            tvLabel.setSelected(true);
        }
        tvLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (LabelBean datum : getData()) {
                    datum.setStatus(0);
                }
                bean.setStatus(1);
                int id = bean.getId();
                notifyDataSetChanged();


                if (flag<6) {
                    EventBus.getDefault().post(new EventBean(flag,id));
                }else {
                    EventBus.getDefault().post(new EventBean(flag+"",bean.getType()));
                }
            }
        });

    }


}
