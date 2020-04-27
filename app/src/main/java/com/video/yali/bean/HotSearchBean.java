package com.video.yali.bean;

import java.util.List;

public class HotSearchBean {


    /**
     * count : 8
     * list : [{"sort":3,"keyword":"江南","search_count":3},{"sort":3,"keyword":"爱情","search_count":1},{"sort":3,"keyword":"故事","search_count":1},{"sort":3,"keyword":"鼠","search_count":1},{"sort":3,"keyword":"河妖","search_count":41},{"sort":3,"keyword":"自杀","search_count":1},{"sort":3,"keyword":"黎巴嫩","search_count":1},{"sort":3,"keyword":"大地儿女","search_count":25}]
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
         * sort : 3
         * keyword : 江南
         * search_count : 3
         */

        private int sort;
        private String keyword;
        private int search_count;

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public int getSearch_count() {
            return search_count;
        }

        public void setSearch_count(int search_count) {
            this.search_count = search_count;
        }
    }
}
