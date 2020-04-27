package com.video.yali.ui.activity.me;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.UserBean;
import com.video.yali.model.MyModel;
import com.video.yali.utils.GlideCircleTransform;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;
import com.yorhp.recordlibrary.OnScreenShotListener;
import com.yorhp.recordlibrary.ScreenRecordUtil;

import java.io.File;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class PromoteCodeActivity extends BaseActivity {

    @BindView(R.id.rl_promotecode_activity)
    RelativeLayout rlPromotecodeActivity;
    @BindView(R.id.ib_title_left)
    ImageButton ibTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.iv_promotecode_pic)
    ImageView ivPromotecodePic;
    @BindView(R.id.tv_promotecode_code)
    TextView tvPromotecodeCode;
    @BindView(R.id.sb_promotecode_save)
    TextView sbPromotecodeSave;
    @BindView(R.id.sb_promotecode_cope)
    Button sbPromotecodeCope;

    private MyModel model = new MyModel();
    private String imageUrl;
    private UserBean userInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_promote_code;
    }

    @Override
    public void initView() {
        tvTitleMiddle.setText(getString(R.string.my_pomote_code_title));
        tvTitleMiddle.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sp_params.width = ToolUtils.getScreenWidth(this) * 1 / 2;
        sp_params.height = ToolUtils.getScreenWidth(this) * 1 / 2;
        ivPromotecodePic.setLayoutParams(sp_params);


//        if (YlUtils.judgeUserExist()) {
        userInfo = YlUtils.getUserInfo();
        String code = userInfo.getPromotion_code();
        tvPromotecodeCode.setText(code);
//        }
        getPromoteCodeNet();
    }

    @OnClick({R.id.ib_title_left, R.id.sb_promotecode_save, R.id.sb_promotecode_cope})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                finish();
                break;
            case R.id.sb_promotecode_save:
                Bitmap bitmap = ScreenUtils.screenShot(this);
                showSnapshotWindow(bitmap);
//                downPromoteNet();
                break;
            case R.id.sb_promotecode_cope:
                shareText();
                break;
        }
    }


    private void getPromoteCodeNet() {
        model.getPromoteCodeData(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                if (!TextUtils.isEmpty(data)) {
                    imageUrl = data;
                    Glide.with(PromoteCodeActivity.this)
                            .load(data)
                            .apply(centerCropTransform()
                                    .placeholder(R.drawable.default_cover_xhsp)
                                    .error(R.drawable.default_cover_xhsp)
                                    .priority(Priority.HIGH)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)  //跳过磁盘缓存
                                    .skipMemoryCache(false))     //跳过内存缓存
                            .into(ivPromotecodePic);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }


    private void downPromoteNet() {
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }
        model.downPromoteData(this, imageUrl, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                ToastUtils.showShort(getString(R.string.my_pomote_code_save_tip));
                downPromoteSuccessBackNet();//通知服务器下载成功
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void downPromoteSuccessBackNet() {
        model.downPromoteSuccessBackData(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                sbPromotecodeSave.setBackgroundResource(R.drawable.icon_promote_button_active_bg);
                sbPromotecodeSave.setTextColor(Color.WHITE);
                sbPromotecodeSave.setText(R.string.cope_save_success_tip);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private void shareText() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        String appShareInfo = String.format(getString(R.string.app_share_tip), userInfo.getPromotion_code(), userInfo.getPromotion_code());
        cm.setText(appShareInfo);
        ToastUtils.showShort(getString(R.string.cope_success_tip));
        sbPromotecodeCope.setBackgroundResource(R.drawable.icon_copy_button_active_bg);
        sbPromotecodeCope.setTextColor(Color.WHITE);
        sbPromotecodeCope.setText(R.string.cope_success_tip);
    }

    /**
     * 显示截图窗口
     *
     * @param bmp
     */
    private void showSnapshotWindow(final Bitmap bmp) {
        downPromoteSuccessBackNet();//通知服务器下载成功
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(com.tencent.liteav.demo.play.R.layout.layout_new_vod_snap, null);
        ImageView imageView = (ImageView) view.findViewById(com.tencent.liteav.demo.play.R.id.iv_snap);
        imageView.setImageBitmap(bmp);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(rlPromotecodeActivity, Gravity.CENTER, 1800, 300);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                save2MediaStore(bmp);
            }
        });

        rlPromotecodeActivity.postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();

            }
        }, 3000);
    }

    @SuppressLint("WrongThread")
    private void save2MediaStore(Bitmap image) {

        try {
            File appDir = new File(Environment.getExternalStorageDirectory(), "superplayer");
            if (!appDir.exists()) {
                appDir.mkdir();
            }

            long dateSeconds = System.currentTimeMillis() / 1000;
            String fileName = System.currentTimeMillis() + ".jpg";
            File file = new File(appDir, fileName);

            String filePath = file.getAbsolutePath();
            // Save the screenshot to the MediaStore
            ContentValues values = new ContentValues();
            ContentResolver resolver = this.getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, filePath);
            values.put(MediaStore.Images.ImageColumns.TITLE, fileName);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.ImageColumns.DATE_ADDED, dateSeconds);
            values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, dateSeconds);
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
            values.put(MediaStore.Images.ImageColumns.WIDTH, image.getWidth());
            values.put(MediaStore.Images.ImageColumns.HEIGHT, image.getHeight());
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            OutputStream out = resolver.openOutputStream(uri);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            values.clear();
            values.put(MediaStore.Images.ImageColumns.SIZE, new File(filePath).length());
            resolver.update(uri, values, null, null);
            ToastUtils.showShort(getString(R.string.my_pomote_code_save_tip));
        } catch (Exception e) {

        }
    }


}
