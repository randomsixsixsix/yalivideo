package com.video.yali.bean;

public class MyNotifyBean {

    /**
     * image : http://static.1995519.com/images/20191216/lbK4pAHhmFhXYSE0eJ739dD2dGJMFH5P2tPuztb0.png
     * description : 无法播放下载
     * id : 4
     * title : 无法播放下载
     * published_at : 1576485889000
     * content : <p>尊敬的用户您好：</p>
     <p>如果在使用的时候遇到无法播放、无法下载等情况，请按以下步骤操作：</p>
     <p>1.查看自己观影次数是否为0，如果为0请点击首页或者频道也轮播图，每天可以获得一次观影次数，或者点击推广，成功邀请用户，即会按照推广规则给您增加对应的观影次数</p>
     <p>2.点击我的-设置查看是否为最新版本，如果不是请前往http://www.baidu.com下载最新版本</p>
     <p>3.杀掉app进程，重新启动app进行观看</p>
     */

    private boolean is_read;
    private String image;
    private String description;
    private int id;
    private String title;
    private long published_at;
    private String content;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPublished_at() {
        return published_at;
    }

    public void setPublished_at(long published_at) {
        this.published_at = published_at;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
