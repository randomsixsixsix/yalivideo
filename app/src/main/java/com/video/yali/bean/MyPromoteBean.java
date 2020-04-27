package com.video.yali.bean;

import java.util.ArrayList;

public class MyPromoteBean {

    private int count;
    private PromoteInfoBean list;


    public class PromoteInfoBean {
        private ArrayList<PromoteTaskBean> promoteTaskList;//推广任务
        private ArrayList<PromoteWelfareBean> welfareTaskList;//推广福利
        private ArrayList<PromoteDayBean> dailyTaskList;//每日任务

        public ArrayList<PromoteTaskBean> getPromoteTaskList() {
            return promoteTaskList;
        }

        public void setPromoteTaskList(ArrayList<PromoteTaskBean> promoteTaskList) {
            this.promoteTaskList = promoteTaskList;
        }

        public ArrayList<PromoteWelfareBean> getWelfareTaskList() {
            return welfareTaskList;
        }

        public void setWelfareTaskList(ArrayList<PromoteWelfareBean> welfareTaskList) {
            this.welfareTaskList = welfareTaskList;
        }

        public ArrayList<PromoteDayBean> getDailyTaskList() {
            return dailyTaskList;
        }

        public void setDailyTaskList(ArrayList<PromoteDayBean> dailyTaskList) {
            this.dailyTaskList = dailyTaskList;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PromoteInfoBean getList() {
        return list;
    }

    public void setList(PromoteInfoBean list) {
        this.list = list;
    }
}
