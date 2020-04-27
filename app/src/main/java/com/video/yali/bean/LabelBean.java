package com.video.yali.bean;

public class LabelBean {

    private int status;
    private String content;
    private String type;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LabelBean(int status, String content, int id) {
        this.status = status;
        this.content = content;
        this.id=id;
    }

    public LabelBean(int status, String content, String type) {
        this.status = status;
        this.content = content;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
