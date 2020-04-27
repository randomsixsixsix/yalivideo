package com.video.yali.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.bean.CommentBean;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.ToolUtils;

import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by ZH on 2019/3/27.
 */
public class CommentAdapter extends BGARecyclerViewAdapter<CommentBean> {

    public CommentAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.recycle_comment);
    }

    @Override
    public void setItemChildListener(final BGAViewHolderHelper helper, int viewType) {
        helper.setItemChildClickListener(R.id.rl_comment_item_head_all);
        helper.setItemChildClickListener(R.id.tv_comment_item_zannumber);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, CommentBean item) {
        helper.setText(R.id.tv_comment_item_name, item.getName());
        helper.setText(R.id.tv_comment_item_content, item.getContent());
        helper.setText(R.id.tv_comment_item_date, item.getTime_name());
        helper.setText(R.id.tv_comment_item_zannumber, item.getPraise() + "");
        if (item.getSecondLevelCommentsSize() == 0) {
            helper.setVisibility(R.id.tv_comment_item_hfnumber, View.GONE);
        } else {
            helper.setVisibility(R.id.tv_comment_item_hfnumber, View.VISIBLE);
            helper.setText(R.id.tv_comment_item_hfnumber, String.format(mContext.getString(R.string.comment_replay_number), item.getSecondLevelCommentsSize()));
        }
        TextView tv_comment_item_zannumber = helper.getView(R.id.tv_comment_item_zannumber);
        if (item.isIs_praise()) {
            ToolUtils.setTextImage(mContext, tv_comment_item_zannumber, R.mipmap.icon_comment_praise_active, 3);
        } else {
            ToolUtils.setTextImage(mContext, tv_comment_item_zannumber, R.mipmap.icon_comment_praise, 3);
        }
        String headUrl = item.getAvatar();
        Glide.with(mContext)
                .load(headUrl)
                .apply(centerCropTransform()
                        .placeholder(R.mipmap.default_header_comment)
                        .error(R.mipmap.default_header_comment)
                        .priority(Priority.HIGH)
                        .transform(new GlideCircleTransform())  //圆形头像,自定义类
                        .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                        .skipMemoryCache(false))     //跳过内存缓存
                .into((ImageView) helper.getView(R.id.iv_comment_item_head));

    }


    public void freshPositionData(int position) {
        this.mData.get(position).setPraise(mData.get(position).getPraise() + 1);
        this.notifyDataSetChanged();
    }

    public void setZanStatue(int position) {
        this.mData.get(position).setIs_praise(true);
        this.mData.get(position).setPraise(mData.get(position).getPraise() + 1);
        this.notifyItemChanged(position);
    }
}
