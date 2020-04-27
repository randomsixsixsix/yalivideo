package com.video.yali.download;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;
import com.video.yali.R;
import com.video.yali.base.BasePagerFragment;

import butterknife.BindView;

public class DownloadingFragment extends BasePagerFragment {
    @BindView(R.id.recycle_download)
    RecyclerView recycleDownload;
    private OkDownload okDownload;
    private DownloadAdapter adapter;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_mydownload, null);
        return mView;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

        LogUtils.i("downloadok------loaddata");
        okDownload = OkDownload.getInstance();
        adapter = new DownloadAdapter(context);
        adapter.updateData(DownloadAdapter.TYPE_ING);
        recycleDownload.setLayoutManager(new LinearLayoutManager(context));
        recycleDownload.setAdapter(adapter);
      //  adapter.notifyDataSetChanged();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i("isVisibleToUser:" + isVisibleToUser);
        if (isVisibleToUser) {
            if (adapter!=null) {
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i("DownloadingFragment------onDestroyView");
      //  okDownload.removeOnAllTaskEndListener(this);
      //  adapter.unRegister();
    }
}
