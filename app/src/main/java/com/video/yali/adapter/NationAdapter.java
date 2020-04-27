package com.video.yali.adapter;

import android.view.View;

import com.video.yali.R;
import com.video.yali.bean.AreaPhoneBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class NationAdapter extends BGARecyclerViewAdapter<AreaPhoneBean> {


    public NationAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_nation);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.tv_nation_iten_name);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, AreaPhoneBean model) {
        String firstletterLast = null;
        if (position > 0) {
            AreaPhoneBean lastAreaPhoneBean = mData.get(position - 1);
            firstletterLast = lastAreaPhoneBean.fisrtSpell;
        }
        String firstletter = model.fisrtSpell;
        helper.setText(R.id.tv_nation_iten_letter, firstletter);
        helper.setText(R.id.tv_nation_iten_name, model.name);
        if (position == 0 || !firstletter.equals(firstletterLast)) {
            helper.setVisibility(R.id.ll_nation_iten_letter, View.VISIBLE);
        } else {
            helper.setVisibility(R.id.ll_nation_iten_letter, View.GONE);
        }
    }


}
