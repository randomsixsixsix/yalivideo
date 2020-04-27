package com.video.yali.bean;

public class PromoteWelfareBean {

    /**
     * description : 手机号注册，每日观影次数+4（为防止APP被封丢失您的推广信息，可通过注册账号绑定推广人数，强烈建议注册）
     * id : 5
     * logo :
     * name : 手机号注册
     * rules : 手机号注册，每日观影次数+4（为防止APP被封丢失您的推广信息，可通过注册账号绑定推广人数，强烈建议注册
     * task_status : 1
     * task_status_name : 继续任务
     * type : 2
     */

    private String description;
    private int id;
    private String logo;
    private String name;
    private String rules;
    private int task_status;
    private String task_status_name;
    private int type;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public int getTask_status() {
        return task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }

    public String getTask_status_name() {
        return task_status_name;
    }

    public void setTask_status_name(String task_status_name) {
        this.task_status_name = task_status_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
