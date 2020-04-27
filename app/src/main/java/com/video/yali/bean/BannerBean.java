package com.video.yali.bean;

/**
 * Created buy 风凊扬 on 2019/7/4 0004
 */
public class BannerBean {

    private String corver;
 //   private String title;
    private String linkurl;

    private int type;
    private int id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BannerBean(String corver, String linkurl, int type, int id) {
        this.corver = corver;
        this.linkurl = linkurl;
        this.type = type;
        this.id = id;
    }

    public BannerBean(String corver, String linkurl) {
        this.corver = corver;

        this.linkurl = linkurl;
    }

    public String getCorver() {
        return corver;
    }

    public void setCorver(String corver) {
        this.corver = corver;
    }


    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
