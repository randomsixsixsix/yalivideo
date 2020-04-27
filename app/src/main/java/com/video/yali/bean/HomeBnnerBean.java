package com.video.yali.bean;

import java.util.List;

public class HomeBnnerBean {


    /**
     * data : [{"id":20,"image":"http://static.1995519.com/images/20191213/rUaSK4l62NJDsgXl0Xye19M9WlBkkC4iSrN1o3uK.png","target_url":"http://www.baidu.com","type":2},{"id":21,"image":"http://static.1995519.com/images/20191213/y5rOM1ufsxIo92LC6GJTQClCRqtg5awHNcF4zJ4P.jpeg","target_url":"https://www.tmall.com","type":2},{"id":22,"image":"http://static.1995519.com/images/20191213/fW2SX1qEGyUNw2wZBuyi8786YhGSeRsabkwEZZsN.jpeg","target_url":"http://www.10086.cn/","type":2},{"id":23,"image":"http://static.1995519.com/images/20191213/z11u0ssWUNnMCNejw26usIgetF4o4AGtP5p3KENR.png","target_url":"https://jia.tmall.com/","type":2},{"id":24,"image":"http://static.1995519.com/images/20191213/bHBMz0XbxQMODLjuWhT7cHRHI47vwoAUpkpHsgou.jpeg","target_url":"https://ju.taobao.com","type":2}]
     * widget_type : swiper
     */

    private String widget_type;
    private List<DataBean> data;

    public String getWidget_type() {
        return widget_type;
    }

    public void setWidget_type(String widget_type) {
        this.widget_type = widget_type;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 20
         * image : http://static.1995519.com/images/20191213/rUaSK4l62NJDsgXl0Xye19M9WlBkkC4iSrN1o3uK.png
         * target_url : http://www.baidu.com
         * type : 2
         */

        private int id;
        private String image;
        private String target_url;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
