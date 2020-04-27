package com.video.yali.bean;

import java.util.List;

public class StarListBean {


    /**
     * count : 3
     * list : [{"avatar":"http://static.1995519.com/images/20191216/RB6Rw2baeQ9BfOsB69m8RSNK117TvGB3cassbaFh.png","biography":"1968年丹尼尔·克雷格生在英格兰的柴郡切斯特，1972年父母离异后，丹尼尔和姐姐一起在利物浦被抚育成人。 　　1985年进入英国青年剧院学习表演，1990年进入伦敦音乐与戏剧学院。除母语英语之外，他能流利地讲法语、德语，而且对其他语言也很有天份。年近四十的他之前多演小成本独立制作，2001年的两部影片《古墓丽影》（Lara Croft: Tomb Raider）和《毁灭之路》（Road to Perdition）让克雷格扬名国际。","cup":"D","dimension":"22","height":172,"id":3,"movie_count":1,"name":"丹尼尔·克雷格","score":0},{"avatar":"http://static.1995519.com/images/20191216/WhddML7gmEPFxhBWOJSJ2RHJXpwiSEUimmlGwkIK.png","biography":"安娜·西利亚·德哈玛斯·卡索或简称安娜·德哈玛斯，是一位古巴女演员。","cup":"C","dimension":"11,12,12","height":168,"id":4,"movie_count":1,"name":"安娜·德·阿玛斯","score":0},{"avatar":"http://static.1995519.com/images/20191216/ABNtTOvk4VNskMnnRgIRYBbeerin8Zx7xVDQgMj5.jpeg","biography":"��许冠杰（1948.9.6－），昵称\u201c阿Sam\u201d，在香港乐坛被尊称为\u201c歌神\u201d、\u201c香港歌王\u201d、\u201c香港流行音乐祖师\u201d和\u201c广东歌鼻祖\u201d。许冠杰是现代粤语流行歌曲的开山鼻祖，他创造的香港口语演绎法开创了香港本地歌曲的新纪元，对粤语歌的推行和发展起到了决定性作用，同时他也是香港乐坛自作自唱的先驱。许冠杰1974年推出的专辑《鬼马双星》揭开了香港当代流行乐坛的序幕，1976年的经典大碟《半斤八两》则标志香港粤语流行歌的市场正式形成。","cup":"A","dimension":"12,12,12","height":177,"id":5,"movie_count":1,"name":"许冠杰","score":0}]
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
         * avatar : http://static.1995519.com/images/20191216/RB6Rw2baeQ9BfOsB69m8RSNK117TvGB3cassbaFh.png
         * biography : 1968年丹尼尔·克雷格生在英格兰的柴郡切斯特，1972年父母离异后，丹尼尔和姐姐一起在利物浦被抚育成人。 　　1985年进入英国青年剧院学习表演，1990年进入伦敦音乐与戏剧学院。除母语英语之外，他能流利地讲法语、德语，而且对其他语言也很有天份。年近四十的他之前多演小成本独立制作，2001年的两部影片《古墓丽影》（Lara Croft: Tomb Raider）和《毁灭之路》（Road to Perdition）让克雷格扬名国际。
         * cup : D
         * dimension : 22
         * height : 172
         * id : 3
         * movie_count : 1
         * name : 丹尼尔·克雷格
         * score : 0
         */
        private int  star_id;
        private String avatar;
        private String biography;
        private String cup;
        private String dimension;
        private int height;
        private int id;
        private int movie_count;
        private String name;
        private float score;
        private int count;

        public int getStar_id() {
            return star_id;
        }

        public void setStar_id(int star_id) {
            this.star_id = star_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBiography() {
            return biography;
        }

        public void setBiography(String biography) {
            this.biography = biography;
        }

        public String getCup() {
            return cup;
        }

        public void setCup(String cup) {
            this.cup = cup;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMovie_count() {
            return movie_count;
        }

        public void setMovie_count(int movie_count) {
            this.movie_count = movie_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }
    }
}
