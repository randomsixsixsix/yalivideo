package com.video.yali.bean;

public class UserBean {


    /**
     * downloaded_count : 0
     * promoter_count : 0
     * is_guest : false
     * current_level : 0
     * count_to_next_level : 1
     * next_level : 1
     * viewed_count : 0
     * next_level_name : 入门
     * favorite_count : 0
     * current_level_name : 小白
     * id : 8
     * phone_code : 86
     * is_vip : false
     * promotion_code : 62PDRW
     * sex : 2
     * avatar :     头像图片
     * download_count : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NzgxMjQ4MTYsInVzZXJuYW1lIjoiMTUxMzY4ODEzNzgifQ.rNwymIinGY9mCC2vVAM8JPNTDJEHqdjDW_v_8g58n0o
     * phone : 15136881378
     * partner_status : 0
     * name : 151****1378
     * job :
     * view_count : 11
     * age : 0
     * is_promoter : false
     */

    private int downloaded_count;
    private int promoter_count;
    private boolean is_guest;
    private int current_level;
    private int count_to_next_level;
    private int next_level;
    private int viewed_count;
    private String next_level_name;
    private int favorite_count;
    private String current_level_name;
    private int id;
    private String phone_code;
    private boolean is_vip;
    private String promotion_code;
    private int sex;
    private String avatar;
    private int download_count;
    private String token;
    private String phone;
    private int partner_status;
    private String name;
    private String job;
    private int view_count;
    private int age;
    private boolean is_promoter;

    public int getDownloaded_count() {
        return downloaded_count;
    }

    public void setDownloaded_count(int downloaded_count) {
        this.downloaded_count = downloaded_count;
    }

    public int getPromoter_count() {
        return promoter_count;
    }

    public void setPromoter_count(int promoter_count) {
        this.promoter_count = promoter_count;
    }

    public boolean isIs_guest() {
        return is_guest;
    }

    public void setIs_guest(boolean is_guest) {
        this.is_guest = is_guest;
    }

    public int getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(int current_level) {
        this.current_level = current_level;
    }

    public int getCount_to_next_level() {
        return count_to_next_level;
    }

    public void setCount_to_next_level(int count_to_next_level) {
        this.count_to_next_level = count_to_next_level;
    }

    public int getNext_level() {
        return next_level;
    }

    public void setNext_level(int next_level) {
        this.next_level = next_level;
    }

    public int getViewed_count() {
        return viewed_count;
    }

    public void setViewed_count(int viewed_count) {
        this.viewed_count = viewed_count;
    }

    public String getNext_level_name() {
        return next_level_name;
    }

    public void setNext_level_name(String next_level_name) {
        this.next_level_name = next_level_name;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public String getCurrent_level_name() {
        return current_level_name;
    }

    public void setCurrent_level_name(String current_level_name) {
        this.current_level_name = current_level_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public boolean isIs_vip() {
        return is_vip;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }

    public String getPromotion_code() {
        return promotion_code;
    }

    public void setPromotion_code(String promotion_code) {
        this.promotion_code = promotion_code;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPartner_status() {
        return partner_status;
    }

    public void setPartner_status(int partner_status) {
        this.partner_status = partner_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isIs_promoter() {
        return is_promoter;
    }

    public void setIs_promoter(boolean is_promoter) {
        this.is_promoter = is_promoter;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "downloaded_count=" + downloaded_count +
                ", promoter_count=" + promoter_count +
                ", is_guest=" + is_guest +
                ", current_level=" + current_level +
                ", count_to_next_level=" + count_to_next_level +
                ", next_level=" + next_level +
                ", viewed_count=" + viewed_count +
                ", next_level_name='" + next_level_name + '\'' +
                ", favorite_count=" + favorite_count +
                ", current_level_name='" + current_level_name + '\'' +
                ", id=" + id +
                ", phone_code='" + phone_code + '\'' +
                ", is_vip=" + is_vip +
                ", promotion_code='" + promotion_code + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", download_count=" + download_count +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", partner_status=" + partner_status +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", view_count=" + view_count +
                ", age=" + age +
                ", is_promoter=" + is_promoter +
                '}';
    }
}
