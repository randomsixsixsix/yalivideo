package com.video.yali.bean;

public class HistoryBean {


    /**
     * poster_v : http://static.1995519.com/images/20191213/B9lqN2UAw9CbaE9FEzF7mjkY0nIFaKMl11ApbV9a.png
     * user_account_id : 0
     * viewed_at : 2019-12-30 08:34:02
     * play_url : https://txxs.mahua-yongjiu.com/20191210/7119_c03a3c4c/index.m3u8
     * user_id : 8
     * viewed_at_date : 1577666042000
     * name : 行骗天下JP：浪漫篇
     * id : 61468
     * movie_id : 1089
     * poster : http://static.1995519.com/images/20191213/ebz3lF37bLD2jp2F6aPoiGzeV1lt1vuXcOzuzptn.png
     */

    private String poster_v;
    private int user_account_id;
    private String viewed_at;
    private String play_url;
    private int user_id;
    private long viewed_at_date;
    private String name;
    private int id;
    private int movie_id;
    private String poster;
    private boolean chose;

    public String getPoster_v() {
        return poster_v;
    }

    public void setPoster_v(String poster_v) {
        this.poster_v = poster_v;
    }

    public int getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(int user_account_id) {
        this.user_account_id = user_account_id;
    }

    public String getViewed_at() {
        return viewed_at;
    }

    public void setViewed_at(String viewed_at) {
        this.viewed_at = viewed_at;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getViewed_at_date() {
        return viewed_at_date;
    }

    public void setViewed_at_date(long viewed_at_date) {
        this.viewed_at_date = viewed_at_date;
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

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }
}
