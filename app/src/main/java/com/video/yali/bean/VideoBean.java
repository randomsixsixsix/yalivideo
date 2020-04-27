package com.video.yali.bean;

public class VideoBean {


    private int id;
    private String durcation;
    private String corver;
    private String name;
    private String downloadurl;
    private String bofang;
    private String xiazai;
    private String shoucang;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBofang() {
        return bofang;
    }

    public void setBofang(String bofang) {
        this.bofang = bofang;
    }

    public String getXiazai() {
        return xiazai;
    }

    public void setXiazai(String xiazai) {
        this.xiazai = xiazai;
    }

    public String getShoucang() {
        return shoucang;
    }

    public void setShoucang(String shoucang) {
        this.shoucang = shoucang;
    }

    public VideoBean(int id, String durcation, String corver, String name, String downloadurl, String bofang, String xiazai, String shoucang,String time) {
        this.id = id;
        this.durcation = durcation;
        this.corver = corver;
        this.name = name;
        this.downloadurl = downloadurl;
        this.bofang = bofang;
        this.xiazai = xiazai;
        this.shoucang = shoucang;
        this.time=time;
    }

    public VideoBean(int id, String durcation, String corver, String name) {
        this.id = id;
        this.durcation = durcation;
        this.corver = corver;
        this.name = name;
    }

    public VideoBean(int id, String durcation, String corver, String name, String downloadurl) {
        this.id = id;
        this.durcation = durcation;
        this.corver = corver;
        this.name = name;
        this.downloadurl = downloadurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDurcation() {
        return durcation;
    }

    public void setDurcation(String durcation) {
        this.durcation = durcation;
    }

    public String getCorver() {
        return corver;
    }

    public void setCorver(String corver) {
        this.corver = corver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}
