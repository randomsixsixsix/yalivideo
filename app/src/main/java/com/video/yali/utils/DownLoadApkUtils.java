package com.video.yali.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadFailedListener;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.video.yali.R;


public class DownLoadApkUtils {

    public static void downLoadApk(Context context, String url, final String content,int status,String version) {
        DownloadBuilder downloadBuilder = AllenVersionChecker
                .getInstance()
                .downloadOnly(
                        UIData.create().setDownloadUrl(url)
                );

        downloadBuilder.setForceRedownload(true);
        downloadBuilder.setCustomVersionDialogListener(new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {

                View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
                final Dialog dialog = new Dialog(context, R.style.MyDialog);
                TextView tvContent = view1.findViewById(R.id.tv_versioncontent);
                TextView tvVersion = view1.findViewById(R.id.tv_version_name);
                TextView tvCancel = view1.findViewById(R.id.versionchecklib_version_dialog_cancel);
                if (status==3) {
                    tvCancel.setVisibility(View.GONE);
                }else{
                    tvCancel.setVisibility(View.VISIBLE);
                }
                tvVersion.setText(version+"");
                tvContent.setText(content.replace("\\n","\n"));
                dialog.setContentView(view1);
                dialog.setCancelable(false);
                return dialog;


            }
        });

        downloadBuilder.setForceUpdateListener(new ForceUpdateListener() {
            @Override
            public void onShouldForceUpdate() {


            }
        });


        downloadBuilder.setShowDownloadingDialog(true);//是否显示下载进度对话框
        downloadBuilder.setCustomDownloadingDialogListener(new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {


                Dialog baseDialog = new Dialog(context, R.style.MyDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_download, null);
                baseDialog.setContentView(view);
                baseDialog.setCancelable(false);
                return baseDialog;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                TextView tvProgress = dialog.findViewById(R.id.tv_download_progress);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
                progressBar.setProgress(progress);
                tvProgress.setText(context.getString(R.string.versionchecklib_progress, progress));
            }
        });

        downloadBuilder.setCustomDownloadFailedListener(new CustomDownloadFailedListener() {
            @Override
            public Dialog getCustomDownloadFailed(Context context, UIData versionBundle) {

                Dialog baseDialog = new Dialog(context, R.style.MyDialog);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_faild, null);
                baseDialog.setContentView(view);
                baseDialog.setCancelable(false);
                return baseDialog;

            }
        });
        downloadBuilder.executeMission(context);
    }
}
