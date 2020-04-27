package com.video.yali.ui.activity.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dlong.rep.dlsidebar.DLSideBar;
import com.video.yali.R;
import com.video.yali.adapter.NationAdapter;
import com.video.yali.base.BaseActivity;
import com.video.yali.bean.AreaPhoneBean;
import com.video.yali.utils.PinyinUtils;
import com.video.yali.utils.ReadAssetsJsonUtil;
import com.video.yali.utils.ToolUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;

public class NationActivity extends BaseActivity {

    @BindView(R.id.iv_nation_back)
    ImageView ivNationBack;
    @BindView(R.id.tv_nation_title)
    TextView tvNationTitle;
    @BindView(R.id.tv_nation_search)
    ImageView tvNationSearch;
    @BindView(R.id.et_nation_input)
    EditText etNationInput;
    @BindView(R.id.rv_nation)
    RecyclerView mRecyclerView;
    @BindView(R.id.sb_nation)
    DLSideBar mDLSideBar;
    private NationAdapter mQuickAdapter;
    private ArrayList<AreaPhoneBean> mData = new ArrayList<>();
    public static int resultCode = 2001;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nation;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        showProgress();

        JSONArray array = ReadAssetsJsonUtil.getJSONArray(getResources().getString(R.string.area_select_json_name), this);
        if (null == array) array = new JSONArray();
        for (int i = 0; i < array.length(); i++) {
            AreaPhoneBean bean = new AreaPhoneBean();
            bean.name = array.optJSONObject(i).optString("zh");
            bean.name_py = PinyinUtils.getPinYin(bean.name);
            bean.fisrtSpell = PinyinUtils.getFirstSpell(bean.name.substring(0, 1));
            bean.code = array.optJSONObject(i).optInt("code");
            bean.locale = array.optJSONObject(i).optString("locale");
            bean.en_name = array.optJSONObject(i).optString("en");
            mData.add(bean);
        }
        // 根据拼音为数组进行排序
        Collections.sort(mData, new AreaPhoneBean.ComparatorPY());
        mQuickAdapter = new NationAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setData(mData);
        dismissProgress();
    }

    private int scrollPosition = 0;

    @Override
    public void initListener() {
        // 配置右侧index
        mDLSideBar.setOnTouchingLetterChangedListener(new DLSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String str) {
                //触摸字母列时,将品牌列表更新到首字母出现的位置
                if (mData.size() > 0) {
                    for (int i = 0; i < mData.size(); i++) {
                        String firstletter = mData.get(i).fisrtSpell;
                        if (str.equals(firstletter.toUpperCase())) {
                            scrollPosition = i;
                            break;
                        }
                    }
                    mRecyclerView.scrollToPosition(scrollPosition);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(scrollPosition, 0);
                }
            }
        });

        // 配置搜索
        etNationInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    //根据编辑框值过滤联系人并更新联系列表
                    filterContacts(s.toString().trim());
                    mDLSideBar.setVisibility(View.GONE);
                } else {
                    mDLSideBar.setVisibility(View.VISIBLE);
                    mQuickAdapter.setData(mData);
                    mQuickAdapter.notifyDataSetChanged();
                }
            }
        });

        mQuickAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_nation_iten_name) {
                    AreaPhoneBean mAreaPhoneBean = mQuickAdapter.getData().get(position);
                    Intent mIntent = new Intent();
                    mIntent.putExtra("nationName", mAreaPhoneBean.name);
                    mIntent.putExtra("nationCode", mAreaPhoneBean.code);
                    setResult(resultCode, mIntent);
                    finish();
                }

            }
        });

    }

    @OnClick({R.id.iv_nation_back, R.id.tv_nation_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_nation_back:
                if (isSearch) {
                    changeMode(false);
                } else {
                    finish();
                }

                break;
            case R.id.tv_nation_search:
                changeMode(true);
                break;
        }
    }

    /**
     * 比对筛选
     */
    private void filterContacts(String filterStr) {
        ArrayList<AreaPhoneBean> areaList = new ArrayList<>();
        //遍历数组,筛选出包含关键字的item
        for (int i = 0; i < mData.size(); i++) {
            //过滤的条件
            if (ToolUtils.isStrInString(mData.get(i).name_py, filterStr)
                    || mData.get(i).name.contains(filterStr)
                    || ToolUtils.isStrInString(mData.get(i).fisrtSpell, filterStr)) {
                //将筛选出来的item重新添加到数组中
                AreaPhoneBean filter = mData.get(i);
                areaList.add(filter);
            }
        }
        //将列表更新为过滤的联系人
        if (areaList.size() > 0) {
            mQuickAdapter.setData(areaList);
            mQuickAdapter.notifyDataSetChanged();
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

    }


    private boolean isSearch;

    /**
     * 修改当前显示
     */
    private void changeMode(boolean isSearch) {
        this.isSearch = isSearch;
        if (isSearch) {
            etNationInput.setFocusable(true);
            tvNationTitle.setVisibility(View.GONE);
            tvNationSearch.setVisibility(View.GONE);
            etNationInput.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            etNationInput.setFocusable(false);
            tvNationTitle.setVisibility(View.VISIBLE);
            tvNationSearch.setVisibility(View.VISIBLE);
            etNationInput.setVisibility(View.GONE);
            etNationInput.setText("");
            mRecyclerView.setVisibility(View.VISIBLE);
            mQuickAdapter.setData(mData);
            mQuickAdapter.notifyDataSetChanged();
        }
    }
}
