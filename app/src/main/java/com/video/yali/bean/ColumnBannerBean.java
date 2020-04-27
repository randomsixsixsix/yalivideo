package com.video.yali.bean;

import java.util.List;

public class ColumnBannerBean {


    /**
     * count : 1
     * list : [{"image":"http://static.1995519.com/images/20191216/qiYJ1gdf79fPs6Xf2vXCmU7PiueXvA5ECxPT83By.jpeg","target_url":"http://www.baidu.com","id":28,"type":2}]
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
         * image : http://static.1995519.com/images/20191216/qiYJ1gdf79fPs6Xf2vXCmU7PiueXvA5ECxPT83By.jpeg
         * target_url : http://www.baidu.com
         * id : 28
         * type : 2
         */

        private String image;
        private String target_url;
        private int id;
        private int type;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTarget_url() {
            return target_url;
        }

        public void setTarget_url(String target_url) {
            this.target_url = target_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
