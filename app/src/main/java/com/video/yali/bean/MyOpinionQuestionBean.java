package com.video.yali.bean;

public class MyOpinionQuestionBean {


    /**
     * question : 视频播放不了
     * description : 检查网络环境是否正常
     * id : 3
     */

    private String question;
    private String description;
    private String answer;
    private int id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
