package com.video.yali.bean;

public class HomeAdBean {


    /**
     * data : {"image":"http://static.1995519.com/images/20191213/y5rOM1ufsxIo92LC6GJTQClCRqtg5awHNcF4zJ4P.jpeg","target_url":"https://www.tmall.com","id":21,"type":2}
     * widget_type : ad
     */

    private DataBean data;
    private String widget_type;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getWidget_type() {
        return widget_type;
    }

    public void setWidget_type(String widget_type) {
        this.widget_type = widget_type;
    }

    public static class DataBean {
        /**
         * image : http://static.1995519.com/images/20191213/y5rOM1ufsxIo92LC6GJTQClCRqtg5awHNcF4zJ4P.jpeg
         * target_url : https://www.tmall.com
         * id : 21
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
