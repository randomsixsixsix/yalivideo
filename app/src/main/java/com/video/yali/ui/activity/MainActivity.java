package com.video.yali.ui.activity;


import android.Manifest;
import android.text.TextUtils;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.video.yali.GlobConstant;
import com.video.yali.R;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.CheckVersionBean;
import com.video.yali.bean.MainNoticeBean;
import com.video.yali.bean.UserBean;
import com.video.yali.event.OrangeEvent;
import com.video.yali.model.MainModel;
import com.video.yali.model.RequestModel;
import com.video.yali.model.SettingModel;
import com.video.yali.ui.activity.me.SettingActivity;
import com.video.yali.ui.fragment.ColumFragment;
import com.video.yali.ui.fragment.HomeFragment;
import com.video.yali.ui.fragment.MyFragment;
import com.video.yali.ui.fragment.rank.RankFragment;
import com.video.yali.utils.ConstantUtils;
import com.video.yali.utils.DownLoadApkUtils;
import com.video.yali.utils.RequestCallback;
import com.video.yali.utils.ToolUtils;
import com.video.yali.utils.YlUtils;
import com.video.yali.widget.dialog.NotiDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindArray;
import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindArray(R.array.main_sorts)
            String[] mainSortsStrings;

    private List<Fragment> fragments;
    public int currIndex = 0;
    private int lastIndex = 0;
    private MainModel model = new MainModel();
    private RequestModel model1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        SPUtils.getInstance().put(GlobConstant.ISfIRST, false);
        //初始化页面
        currIndex = getIntent().getIntExtra("mainType", 0);
        initFragment();
        //初始状态时,默认选中首页页面
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, fragments.get(currIndex)).commit();
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.main_home_select, mainSortsStrings[0]).setInactiveIconResource(R.mipmap.main_home_nomal))
                .addItem(new BottomNavigationItem(R.mipmap.main_column_select, mainSortsStrings[1]).setInactiveIconResource(R.mipmap.main_column_nomal))
                .addItem(new BottomNavigationItem(R.mipmap.main_rank_select, mainSortsStrings[2]).setInactiveIconResource(R.mipmap.main_rank_nomal))
                .addItem(new BottomNavigationItem(R.mipmap.main_me_select, mainSortsStrings[3]).setInactiveIconResource(R.mipmap.main_me_nomal))
                .setFirstSelectedPosition(currIndex)
                .initialise();
        ToolUtils.setBottomNavigationItem(this, bottomNavigationBar, 10, 25, 13);

        checkVersion();
        getMainNoticeNet();

    }


    private void checkVersion() {

        SettingModel model = new SettingModel();
        model.checkVersion(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                LogUtils.i("data:" + data);
                if (TextUtils.isEmpty(data) || data.equals("null")) {
                    return;
                }
                CheckVersionBean checkVersionBean = GsonUtils.fromJson(data, CheckVersionBean.class);
                String currentVersion = ToolUtils.getVersion(MainActivity.this);
                String serverVersion = checkVersionBean.getVersion().replace("v", "");
                if (checkVersionBean == null || currentVersion.equals(serverVersion)) {
                    return;
                }
                int status = checkVersionBean.getStatus();
                if (status == 1 || status == 4) {
                    return;
                }
                String description = checkVersionBean.getDescription();
                String downloadUrl = checkVersionBean.getDownloadUrl();
                String version = checkVersionBean.getVersion();
                DownLoadApkUtils.downLoadApk(MainActivity.this, downloadUrl, description, status,version);

            }

            @Override
            public void onError(int code) {

            }
        });
    }

    @Override
    public void initListener() {
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (currIndex != position) {
                    currIndex = position;
                    changeFragment(currIndex);
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ColumFragment());
        fragments.add(new RankFragment());
        fragments.add(new MyFragment());
    }

    public void changeFragment(int currIndex) {
        this.currIndex = currIndex;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currfragment = fragments.get(currIndex);
        Fragment lastfragment = fragments.get(lastIndex);

        if (currfragment.isAdded()) { // 如果被添加过
            transaction.hide(lastfragment).show(currfragment); // 隐藏当前的fragment，显示下一个
        } else {
            transaction.hide(lastfragment).add(R.id.fl_container, currfragment);
        }
        transaction.commit();
        lastIndex = currIndex;
    }

    //获取主页面公告
    private void getMainNoticeNet() {
        model.getMainNoticeData(this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                MainNoticeBean mMainNoticeBean = gson.fromJson(data, MainNoticeBean.class);
                if (mMainNoticeBean != null) {
                    showNoticeDialog(mMainNoticeBean);
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    private NotiDialog myDialog;

    /**
     * 公告提示
     */
    private void showNoticeDialog(MainNoticeBean mMainNoticeBean) {
        String titleString = mMainNoticeBean.getTitle();
        String descString = mMainNoticeBean.getDescription();
        String okString = getString(R.string.text_konw);
        if (myDialog == null) {
            myDialog = new NotiDialog(this, R.style.MyDialog);
        }
        myDialog.show();
        myDialog.setDialogData(titleString, descString, okString);
        myDialog.setOnDialogClickListener(new NotiDialog.MyDialogClickListener() {
            @Override
            public void onOkClick() {
                myDialog.dismiss();
            }
        });
    }

    private void getUserInfo(boolean refreshUserInfo) {
        String token = null;
        if (YlUtils.judgeUserExist()) {
            token = YlUtils.getUserInfo().getToken();
        }
        String finalToken = token;
        model.getUserInfoData(MainActivity.this, new RequestCallback() {
            @Override
            public void onSuccess(String data) {
                Gson gson = new Gson();
                UserBean mUserBean = gson.fromJson(data, UserBean.class);
                if (mUserBean != null) {
                    mUserBean.setToken(finalToken);
                    YlUtils.saveUserInfo(mUserBean);
                    if (refreshUserInfo) {
                        ((MyFragment) fragments.get(3)).initUserData();
                    }
                }
            }

            @Override
            public void onError(int code) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
//        boolean isP = PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE);
//        if (!isP) {      //没有权限
//            requestPhoneState();
//        } else {
            getUserInfo(false);
//        }
    }

    private void requestPhoneState() {
        PermissionUtils.permission(PermissionConstants.PHONE, PermissionConstants.STORAGE)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {

                    }
                })
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
//                        LogUtils.e("用户信息---onGranted");
                        getUserInfo(false);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
//                        LogUtils.e("用户信息---拒绝--onDenied");
                    }
                })
                .request();
    }

    @Subscribe       //订阅事件FirstEvent
    public void onEventMainThread(OrangeEvent event) {
        if (ConstantUtils.EVENT_FRESHUSERINFO == event.msg) {
            getUserInfo(true);
        }
    }

}
