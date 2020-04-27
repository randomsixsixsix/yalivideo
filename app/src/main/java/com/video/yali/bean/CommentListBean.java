package com.video.yali.bean;

import java.util.ArrayList;

public class CommentListBean {

    private int count;
    private ArrayList<CommentBean> list;

    public int getTotal() {
        return count;
    }

    public void setTotal(int count) {
        this.count = count;
    }

    public ArrayList<CommentBean> getList() {
        return list;
    }

    public void setList(ArrayList<CommentBean> list) {
        this.list = list;
    }
}
