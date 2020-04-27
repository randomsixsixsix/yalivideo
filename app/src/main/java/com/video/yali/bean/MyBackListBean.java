package com.video.yali.bean;

import java.util.ArrayList;

public class MyBackListBean {

    private int count;
    private ArrayList<MyBackBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<MyBackBean> getList() {
        return list;
    }

    public void setList(ArrayList<MyBackBean> list) {
        this.list = list;
    }
}
