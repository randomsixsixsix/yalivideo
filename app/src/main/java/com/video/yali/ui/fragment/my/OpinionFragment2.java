package com.video.yali.ui.fragment.my;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.video.yali.R;
import com.video.yali.adapter.MyOpinionAdapter2Photo;
import com.video.yali.adapter.MyOpinionAdapter2Problem;
import com.video.yali.base.BasePagerFragment;
import com.video.yali.bean.MyBackBean;
import com.video.yali.bean.MyBackListBean;
import com.video.yali.bean.MyFeedbackListBean;
import com.video.yali.bean.NotifyListBean;
import com.video.yali.bean.OpinionImageBean;
import com.video.yali.model.MyModel;
import com.video.yali.ui.activity.me.HeadImageActivity;
import com.video.yali.ui.activity.me.ModifyActivity;
import com.video.yali.ui.activity.me.MyOpinionActivity;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ScrollGridLayoutManager;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;
import com.video.yali.widget.dialog.MyDialog;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;

public class OpinionFragment2 extends BasePagerFragment {

    @BindView(R.id.rv_opinion2_photo)
    RecyclerView mRecyclerView1;
    @BindView(R.id.rv_opinion2_problem)
    RecyclerView mRecyclerView2;
    @BindView(R.id.bt_loginmobile_ok)
    Button btLoginmobileOk;
    @BindView(R.id.et_loginmobile_input)
    EditText etLoginmobileInput;

    private MyModel model = new MyModel();
    ArrayList<String> photomList = new ArrayList<>();
    public static int requestCode = 1006;
    private MyOpinionAdapter2Photo mQuickAdapter1;
    private MyOpinionAdapter2Problem mQuickAdapter2;
    private int selectProblemType = -1;

    @Override
    protected View getLayoutId() {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_opinion2, null);
        return mView;
    }

    @Override
    protected void initView() {
        photomList.add("+");
        mRecyclerView1.setLayoutManager(new ScrollGridLayoutManager(getActivity(), 3));
        mQuickAdapter1 = new MyOpinionAdapter2Photo(mRecyclerView1);
        mRecyclerView1.setAdapter(mQuickAdapter1);
        mQuickAdapter1.setData(photomList);

        mRecyclerView2.setLayoutManager(new ScrollGridLayoutManager(getActivity(), 3));
        mQuickAdapter2 = new MyOpinionAdapter2Problem(mRecyclerView2);
        mRecyclerView2.setAdapter(mQuickAdapter2);


        initListener();
        getBackTypeNet();
    }

    private void getBackTypeNet() {
        model.getBackTypeData((MyOpinionActivity) getActivity(), new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                MyBackListBean mMyBackListBean = gson.fromJson(data, MyBackListBean.class);
                mQuickAdapter2.setData(mMyBackListBean.getList());
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void initListener() {
        mQuickAdapter1.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                ArrayList<String> photoData = (ArrayList<String>) mQuickAdapter1.getData();
                if (position == photoData.size() - 1) {    //添加图片
                    selectPhoto();
                }
            }
        });

        mQuickAdapter2.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                MyBackBean mMyBackBean=mQuickAdapter2.getData().get(position);
                mQuickAdapter2.setSelelctType(position);
                selectProblemType = mMyBackBean.getId();
            }
        });
    }


    @Override
    protected void loadData() {

    }

    @OnClick(R.id.bt_loginmobile_ok)
    public void onClick() {
        String inputContent = etLoginmobileInput.getText().toString().trim();
        if (TextUtils.isEmpty(inputContent)) {
            ToastUtils.showShort(getString(R.string.opinion_input_empty_tip));
            return;
        }
        if (selectProblemType == -1) {
            ToastUtils.showShort(getString(R.string.opinion_select_empty_tip));
            return;
        }
        String imagesString = "";
        if (postImageList.size() > 0) {

            for (int i = 0; i < postImageList.size(); i++) {
                if (i == 0) {
                    imagesString = "[" + postImageList.get(i);
                } else {
                    imagesString = imagesString + "," + postImageList.get(i);
                }
            }
            imagesString = imagesString + "]";
        } else {
            imagesString = "[]";
        }
        model.getOpinionPutData((MyOpinionActivity) getActivity(), imagesString, inputContent, selectProblemType, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                showSuccessDialog();

            }

            @Override
            public void onError(int code) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver resolver = getActivity().getContentResolver();
            // ContentResolver对象的getType方法可返回形如content://的Uri的类型
            // 如果是一张图片，返回结果为image/jpeg或image/png等
            String fileType = resolver.getType(uri);
            if (fileType.startsWith("image")) {  // 判断用户选择的是否为图片
                // 根据返回的uri获取图片路径
                Cursor cursor = resolver.query(uri, new String[]{MediaStore.Images.Media.DATA},
                        null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                File imageFile = new File(imagePath);
                LogUtils.e("文件路径--" + imageFile.getPath() + "==" + imageFile.exists());
                photomList.add(photomList.size() - 1, imageFile.getPath());
                mQuickAdapter1.setData(photomList);
                postProblemImageNet(imageFile);
            }
        }
    }

    private ArrayList<String> postImageList = new ArrayList<String>();

    private void postProblemImageNet(File imageFile) {
        model.postOpinionImageData((MyOpinionActivity) getActivity(), imageFile, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                OpinionImageBean mOpinionImageBean = gson.fromJson(data, OpinionImageBean.class);
                postImageList.add(mOpinionImageBean.getUrl());
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void selectPhoto() {
        //调用系统单选图片
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        if (intent != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 成功提示
     */
    private void showSuccessDialog() {
        String titleString = getString(R.string.text_tip);
        String descString = getString(R.string.opinion_put_success_tip);
        String okString = getString(R.string.text_konw);
        MyDialog myDialog = new MyDialog(getActivity(), R.style.MyDialog);
        myDialog.setCancelable(false);
        myDialog.show();
        myDialog.setDialogData(titleString, descString, "", "", okString);
        myDialog.setOnDialogClickListener(new MyDialog.MyDialogClickListener() {
            @Override
            public void onCancelClick() {

            }

            @Override
            public void onConfirmClick() {
            }

            @Override
            public void onOkClick() {
                myDialog.dismiss();
                getActivity().finish();
            }
        });
    }
}
