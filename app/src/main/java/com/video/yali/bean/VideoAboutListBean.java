package com.video.yali.bean;

import java.util.ArrayList;
import java.util.List;

public class VideoAboutListBean {

    private int count;
    private ArrayList<VideoDetailsBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<VideoDetailsBean> getList() {
        return list;
    }

    public void setList(ArrayList<VideoDetailsBean> list) {
        this.list = list;
    }

}
