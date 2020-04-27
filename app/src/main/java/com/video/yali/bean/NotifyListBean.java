package com.video.yali.bean;

import java.util.ArrayList;

public class NotifyListBean {

    private int count;
    private ArrayList<MyNotifyBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<MyNotifyBean> getList() {
        return list;
    }

    public void setList(ArrayList<MyNotifyBean> list) {
        this.list = list;
    }
}
