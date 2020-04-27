package com.video.yali.bean;

import java.util.ArrayList;

public class HistoryListBean {

    private int count;
    private ArrayList<HistoryBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<HistoryBean> getList() {
        return list;
    }

    public void setList(ArrayList<HistoryBean> list) {
        this.list = list;
    }
}
