package com.video.yali.ui.activity.me;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.UserBean;
import com.video.yali.model.SettingModel;
import com.video.yali.ui.activity.login.NationActivity;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;
import com.video.yali.widget.RankPopWindow;
import com.video.yali.widget.dialog.NickDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class PersonalActivity extends BaseActivity {

    @BindView(R.id.ll_personal_all)
    LinearLayout llPersonalAll;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.iv_personal_headerpic)
    ImageView ivPersonalHeaderpic;
    @BindView(R.id.ll_personal_headerpic)
    LinearLayout llPersonalHeaderpic;
    @BindView(R.id.ll_personal_nick)
    LinearLayout llPersonalNick;
    @BindView(R.id.ll_personal_sex)
    LinearLayout llPersonalSex;
    @BindView(R.id.ll_personal_age)
    LinearLayout llPersonalAge;
    @BindView(R.id.ll_personal_work)
    LinearLayout llPersonalWork;
    @BindView(R.id.ll_personal_mate)
    LinearLayout llPersonalMate;
    @BindView(R.id.ll_personal_like)
    LinearLayout llPersonalLike;
    @BindView(R.id.tv_personal_nick)
    TextView tvPersonalNick;
    @BindView(R.id.tv_personal_sex)
    TextView tvPersonalSex;
    @BindView(R.id.tv_personal_age)
    TextView tvPersonalAge;
    @BindView(R.id.tv_personal_work)
    TextView tvPersonalWork;
    @BindView(R.id.tv_personal_mate)
    TextView tvPersonalMate;
    @BindView(R.id.tv_personal_like)
    TextView tvPersonalLike;

    public static int requestCode1 = 1003;
    public static int requestCode2 = 1004;
    public static int requestCode3 = 1005;
    private SettingModel model = new SettingModel();
    private UserBean mUserBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView() {
        mUserBean = YlUtils.getUserInfo();
        tvTitleMiddle.setText(getString(R.string.personal_info));
        tvTitleMiddle.setVisibility(View.VISIBLE);

//       boolean isP= PermissionUtils.isGranted(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (!isP){      //没有权限
//            requestCalendarAndRecordAudio();
//        }

    }

    @Override
    public void initData() {
        if (mUserBean != null) {
            LogUtils.e("用户基本信息--" + mUserBean.toString());
            if (!TextUtils.isEmpty(mUserBean.getAvatar())) {
                Glide.with(this)
                        .load(mUserBean.getAvatar())
                        .apply(centerCropTransform()
                                .placeholder(R.mipmap.default_header_logo)
                                .error(R.mipmap.default_header_logo)
                                .priority(Priority.HIGH)
                                .transform(new GlideCircleTransform())  //圆形头像,自定义类
                                .diskCacheStrategy(DiskCacheStrategy.NONE)  //跳过磁盘缓存
                                .skipMemoryCache(false))     //跳过内存缓存
                        .into(ivPersonalHeaderpic);
            }

            if (!TextUtils.isEmpty(mUserBean.getName())) {
                tvPersonalNick.setText(mUserBean.getName());
            }
            if (mUserBean.getSex() == 1) {
                tvPersonalSex.setText(getString(R.string.text_man));
            } else if (mUserBean.getSex() == 2) {
                tvPersonalSex.setText(getString(R.string.text_woman));
            }
            if (mUserBean.getAge() > 0) {
                tvPersonalAge.setText(mUserBean.getAge() + "");
            }
            if (!TextUtils.isEmpty(mUserBean.getJob())) {
                tvPersonalWork.setText(mUserBean.getJob());
            }
            if (mUserBean.getPartner_status()>0) {
                if (mUserBean.getPartner_status()==1){
                    tvPersonalMate.setText(getString(R.string.mate_unmarry));
                }else if (mUserBean.getPartner_status()==2){
                    tvPersonalMate.setText(getString(R.string.mate_married));
                }else if (mUserBean.getPartner_status()==3){
                    tvPersonalMate.setText(getString(R.string.mate_divorced));
                }

            }

        }

    }


    @OnClick({R.id.ib_title_left, R.id.ll_personal_headerpic, R.id.ll_personal_nick, R.id.ll_personal_sex, R.id.ll_personal_age, R.id.ll_personal_work, R.id.ll_personal_mate, R.id.ll_personal_like})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.ll_personal_headerpic:
                Intent mintent = new Intent(this, HeadImageActivity.class);
                startActivityForResult(mintent, requestCode3);
//                selectPhoto();
                break;
            case R.id.ll_personal_nick:
                showNickDialog();
                break;
            case R.id.ll_personal_sex:
                selectSex();
                break;
            case R.id.ll_personal_age:
                if (mUserBean.getAge() > 0) {
                    ToastUtils.showShort(getString(R.string.age_nomodify_tip));
                }else{
                    selectYear();
                }
                break;
            case R.id.ll_personal_work:
                startModify(1);
                break;
            case R.id.ll_personal_mate:
                selectMate();
                break;
            case R.id.ll_personal_like:
                startModify(2);
                break;
        }
    }

    private void selectMate() {
        ArrayList<String> mateList = new ArrayList<>();
        mateList.add(getString(R.string.mate_unmarry));
        mateList.add(getString(R.string.mate_married));
        mateList.add(getString(R.string.mate_divorced));
        ToolUtils.backgroundAlpha(this, 0.5f);
        RankPopWindow mRankPopWindow = new RankPopWindow(this, mateList);
        mRankPopWindow.setAnimationStyle(R.style.DialogBottomAnim);
        //显示窗口
        mRankPopWindow.showAtLocation(llPersonalAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        mRankPopWindow.setOnItemClickListener(new RankPopWindow.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                personalMateNet(mRankPopWindow,mateList.get(position),position+1);

            }
        });
    }


    private void selectYear() {
        ArrayList<String> yearList = new ArrayList<>();
        int currYear = ToolUtils.getSysYear();
        for (int i = currYear; i > 1969; i--) {
            yearList.add(i + "");
        }
        ToolUtils.backgroundAlpha(this, 0.5f);
        RankPopWindow mRankPopWindow = new RankPopWindow(this, yearList);
        mRankPopWindow.setAnimationStyle(R.style.DialogBottomAnim);
        //显示窗口
        mRankPopWindow.showAtLocation(llPersonalAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        mRankPopWindow.setOnItemClickListener(new RankPopWindow.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                int age = currYear - Integer.parseInt(yearList.get(position));
                personalAgeNet(mRankPopWindow, age);


            }
        });
    }

    private void selectSex() {
        ArrayList<String> sexList = new ArrayList<>();
        sexList.add(getString(R.string.text_man));
        sexList.add(getString(R.string.text_woman));
        ToolUtils.backgroundAlpha(this, 0.5f);
        RankPopWindow mRankPopWindow = new RankPopWindow(this, sexList);
        mRankPopWindow.setAnimationStyle(R.style.DialogBottomAnim);
        //显示窗口
        mRankPopWindow.showAtLocation(llPersonalAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        mRankPopWindow.setOnItemClickListener(new RankPopWindow.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                personalSexNet(mRankPopWindow, sexList.get(position), position + 1);


            }
        });
    }


    private void showNickDialog() {
        final NickDialog myDialog = new NickDialog(this, R.style.MyDialog);
        myDialog.show();
        myDialog.setCancelable(true);
        myDialog.setOnDialogClickListener(new NickDialog.MyDialogClickListener() {

            @Override
            public void onConfirmClick(String inputNick) {
                personalNickNet(myDialog, inputNick);


            }
        });
    }

    private void startModify(int type) {
        Intent mIntent = new Intent(PersonalActivity.this, ModifyActivity.class);
        mIntent.putExtra(ModifyActivity.modifyTypeName, type);
        startActivityForResult(mIntent, requestCode1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode1 && resultCode == ModifyActivity.resultCode) {
            String modifyName = data.getStringExtra("modifyName");
            int modifyType = data.getIntExtra("modifyType", 1);
            if (modifyType == 1) {
                mUserBean.setJob(modifyName);
                YlUtils.saveUserInfo(mUserBean);
                tvPersonalWork.setText(modifyName);
            } else if (modifyType == 2) {
                tvPersonalLike.setText(modifyName);
            }
        }else if (requestCode == this.requestCode3 && resultCode == HeadImageActivity.resultCode) {
            String headImage = data.getStringExtra("headImage");
            personalHeadLogoNet(headImage);
            Glide.with(this)
                    .load(headImage)
                    .placeholder(R.mipmap.default_header_logo)
                    .transform(new GlideCircleTransform())
                    .into(ivPersonalHeaderpic);
        }
        else if (requestCode == this.requestCode2 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver resolver = this.getContentResolver();
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
                LogUtils.e("文件路径--" + imageFile.getPath()+"=="+imageFile.exists());
                Bitmap bitmap = ToolUtils.getLoacalBitmap(imageFile.getPath()); //从本地取图片(在cdcard中获取)  //
//                ivPersonalHeaderpic .setImageBitmap(bitmap); //设置Bitmap
//                personalHeadLogoNet(imageFile);
                Glide.with(this)
                        .load(imageFile.getPath())
                        .placeholder(R.mipmap.default_header_logo)
                        .transform(new GlideCircleTransform())
                        .into(ivPersonalHeaderpic);
            }
        }
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
            startActivityForResult(intent, requestCode2);
        }
    }

    private void personalNickNet(NickDialog myDialog, String inputNick) {
        model.personalNickData(this, inputNick, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                mUserBean.setName(data);
                YlUtils.saveUserInfo(mUserBean);
                tvPersonalNick.setText(inputNick);
                myDialog.dismiss();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void personalSexNet(RankPopWindow popWindow, String sexName, int sexIndex) {
        model.personalSexData(this, sexIndex, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                mUserBean.setSex(sexIndex);
                YlUtils.saveUserInfo(mUserBean);
                tvPersonalSex.setText(sexName);
                popWindow.dismiss();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void personalAgeNet(RankPopWindow popWindow, int age) {
        model.personalAgeData(this, age, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                mUserBean.setAge(age);
                YlUtils.saveUserInfo(mUserBean);
                tvPersonalAge.setText(age + "");
                popWindow.dismiss();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void personalMateNet(RankPopWindow popWindow, String mate,int mateIndex) {
        model.personalMateData(this, mateIndex, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                mUserBean.setPartner_status(mateIndex);
                YlUtils.saveUserInfo(mUserBean);
                tvPersonalMate.setText(mate);
                popWindow.dismiss();
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void personalHeadLogoNet(String headImage ) {
        model.personalHeadLogoData(this, headImage, new RequestCallback() {

            @Override
            public void onSuccess(String data) {
                mUserBean.setAvatar(headImage);
                YlUtils.saveUserInfo(mUserBean);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void requestCalendarAndRecordAudio() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {

                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {

                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

                    }
                })
                .request();
    }


}
