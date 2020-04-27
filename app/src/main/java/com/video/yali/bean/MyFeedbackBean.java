package com.video.yali.bean;

public class MyFeedbackBean {


    /**
     * id : 33
     * type : 播放卡顿
     * feedback_time : 2020-01-03 15:51:35
     * content : 哈哈
     */

    private int id;
    private String type;
    private String feedback_time;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeedback_time() {
        return feedback_time;
    }

    public void setFeedback_time(String feedback_time) {
        this.feedback_time = feedback_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
