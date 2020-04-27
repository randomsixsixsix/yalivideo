package com.video.yali.bean;

import java.util.ArrayList;

public class MyLikeListBean {

    private int count;
    private ArrayList<MyLikeBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<MyLikeBean> getList() {
        return list;
    }

    public void setList(ArrayList<MyLikeBean> list) {
        this.list = list;
    }
}
