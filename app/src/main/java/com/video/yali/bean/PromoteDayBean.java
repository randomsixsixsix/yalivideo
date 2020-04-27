package com.video.yali.bean;

public class PromoteDayBean {


    /**
     * description : 保存推广二维码，每日观影次数+1）, 次数不累计
     * id : 9
     * logo :
     * name : 保存二维码
     * rules : 保存推广二维码，每日观影次数+1, 次数不累计
     * task_status : 1
     * task_status_name : 继续任务
     * type : 3
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
