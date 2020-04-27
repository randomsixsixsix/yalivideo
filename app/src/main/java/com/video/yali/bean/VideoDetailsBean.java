package com.video.yali.bean;

import java.util.List;

public class VideoDetailsBean {


    /**
     * poster_v : http://c1n9api.xhimgserve.com/images/20200113/Mq6U3fomuTm6xWUbD2tldElDuSHcj6OZZ8ym7MEi.png
     * play_url : http://x4h1api.qcems.com/20200113/ZTQvdhSA/index.m3u8
     * serial_number :
     * play_count : 1155
     * download_count : 0
     * tags : [{"name":"有码","id":2},{"name":"高画质","id":5},{"name":"口交","id":8},{"name":"少妇","id":10},{"name":"淫荡","id":16},{"name":"无套内射","id":32},{"name":"女女","id":49},{"name":"中出","id":65}]
     * has_praise : false
     * duration : 01:58:23
     * score : 8
     * name : 香港女星激似奖 姊姊这样诱惑我 好吗 -佐々木あき
     * download_url : http://x4h1api.qcems.com/20200113/ZTQvdhSA/mp4/ZTQvdhSA.mp4
     * favorite_count : 0
     * id : 1809
     * published_at : 2020-01-13 20:14:23
     * poster : http://c1n9api.xhimgserve.com/images/20200113/NChoWFltWbZjeFRkiR9M8It3vY9JHfAJCzO69kn1.png
     * desc : 香港女星激似奖 姊姊这样诱惑我 好吗
     */

    private String poster_v;
    private String play_url;
    private String serial_number;
    private int play_count;
    private int download_count;
    private boolean has_praise;
    private String duration;
    private float score;
    private String name;
    private String download_url;
    private int favorite_count;
    private int id;
    private String published_at;
    private String poster;
    private String desc;
    private List<TagsBean> tags;

    public String getPoster_v() {
        return poster_v;
    }

    public void setPoster_v(String poster_v) {
        this.poster_v = poster_v;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public boolean isHas_praise() {
        return has_praise;
    }

    public void setHas_praise(boolean has_praise) {
        this.has_praise = has_praise;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean {
        /**
         * name : 有码
         * id : 2
         */

        private String name;
        private int id;

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
