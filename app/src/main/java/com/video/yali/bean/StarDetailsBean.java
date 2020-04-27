package com.video.yali.bean;

public class StarDetailsBean {


    /**
     * score : 0
     * movie_count : 1
     * name : 丹尼尔·克雷格
     * id : 3
     * biography : 1968年丹尼尔·克雷格生在英格兰的柴郡切斯特，1972年父母离异后，丹尼尔和姐姐一起在利物浦被抚育成人。
     　　1985年进入英国青年剧院学习表演，1990年进入伦敦音乐与戏剧学院。除母语英语之外，他能流利地讲法语、德语，而且对其他语言也很有天份。年近四十的他之前多演小成本独立制作，2001年的两部影片《古墓丽影》（Lara Croft: Tomb Raider）和《毁灭之路》（Road to Perdition）让克雷格扬名国际。
     * avatar : http://static.1995519.com/images/20191216/RB6Rw2baeQ9BfOsB69m8RSNK117TvGB3cassbaFh.png
     * dimension : 22
     * height : 172
     * cup : D
     */

    private float score;
    private int movie_count;
    private String name;
    private int id;
    private String biography;
    private String avatar;
    private String dimension;
    private int height;
    private String cup;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getCup() {
        return cup;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }
}
