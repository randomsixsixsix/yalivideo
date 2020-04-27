package com.video.yali.bean;

import java.util.ArrayList;

public class MyOpinionQuestionListBean {

    private int count;
    private ArrayList<MyOpinionQuestionBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<MyOpinionQuestionBean> getList() {
        return list;
    }

    public void setList(ArrayList<MyOpinionQuestionBean> list) {
        this.list = list;
    }
}
