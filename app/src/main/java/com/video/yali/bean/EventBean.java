package com.video.yali.bean;

public class EventBean {

    private String code;
    private String msg;

    private int flag;
    private int id;

    public EventBean(int flag, int id) {
        this.flag = flag;
        this.id = id;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EventBean(String code) {
        this.code = code;
    }

    public EventBean(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
