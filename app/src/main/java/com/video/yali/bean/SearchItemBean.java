package com.video.yali.bean;

import java.util.List;

public class SearchItemBean {


    /**
     * count : 4
     * list : [{"name":"0-30分钟","id":1},{"name":"30-60分钟","id":2},{"name":"60-120分钟","id":3},{"name":"120分钟以上","id":4}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 0-30分钟
         * id : 1
         */

        private String name;
        private int id;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
