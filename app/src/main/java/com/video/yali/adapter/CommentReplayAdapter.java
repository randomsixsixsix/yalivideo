package com.video.yali.adapter;

import android.text.TextUtils;

import com.video.yali.R;
import com.video.yali.bean.CommentReplayBean;
import com.video.yali.bean.MySortBean;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

public class CommentReplayAdapter extends BGARecyclerViewAdapter<CommentReplayBean> {


    public CommentReplayAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_comment_replay);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {

    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, CommentReplayBean item) {
        String totalContent = null;
        String name = item.getName();
        String content = item.getContent();
        if (TextUtils.isEmpty(item.getTarget_user_name())) {
            totalContent = name + "：" + content;
        } else {
            String targetName = item.getTarget_user_name();
            totalContent = targetName + "回复" + name + "：" + content;
        }
        helper.setText(R.id.tv_commentreplay_item_content, totalContent);

    }


}
