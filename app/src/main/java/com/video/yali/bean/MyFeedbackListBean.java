package com.video.yali.bean;

import java.util.ArrayList;

public class MyFeedbackListBean {

    private int count;
    private ArrayList<MyFeedbackBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<MyFeedbackBean> getList() {
        return list;
    }

    public void setList(ArrayList<MyFeedbackBean> list) {
        this.list = list;
    }
}
