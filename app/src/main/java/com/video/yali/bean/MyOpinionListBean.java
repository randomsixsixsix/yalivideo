package com.video.yali.bean;

import java.util.ArrayList;

public class MyOpinionListBean {

    private int count;
    private ArrayList<MyOpinionBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<MyOpinionBean> getList() {
        return list;
    }

    public void setList(ArrayList<MyOpinionBean> list) {
        this.list = list;
    }
}
