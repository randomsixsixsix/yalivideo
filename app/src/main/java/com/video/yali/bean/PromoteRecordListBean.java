package com.video.yali.bean;

import java.util.ArrayList;

public class PromoteRecordListBean {

    private int count;
    private ArrayList<PromoteRecordBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<PromoteRecordBean> getList() {
        return list;
    }

    public void setList(ArrayList<PromoteRecordBean> list) {
        this.list = list;
    }
}
