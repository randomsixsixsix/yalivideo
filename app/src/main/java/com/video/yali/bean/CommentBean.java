package com.video.yali.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CommentBean  implements  Parcelable{


    /**
     * is_vip : 0
     * sex : 2
     * created_at : 1577172693000
     * avatar : /storage/emulated/0/tencent/MicroMsg/WeiXin/mmexport1574659417995.jpg
     * movie_id : 1090
     * content : 你喜欢这个么
     * praise : 0
     * time_name : 1分钟前
     * secondLevelCommentsSize : 0
     * user_id : 533
     * name : 舒服
     * id : 103
     * target_comment_id : 0
     * real_praise : 0
     * job : 程序员
     * status : 1
     */

    private int is_vip;
    private int sex;
    private long created_at;
    private String avatar;
    private int movie_id;
    private String content;
    private int praise;
    private String time_name;
    private int secondLevelCommentsSize;
    private int user_id;
    private String name;
    private int id;
    private int target_comment_id;
    private int real_praise;
    private String job;
    private int status;
    private boolean is_praise;
    private ArrayList<CommentReplayBean> commentsLowerVOList;

    protected CommentBean(Parcel in) {
        is_vip = in.readInt();
        sex = in.readInt();
        created_at = in.readLong();
        avatar = in.readString();
        movie_id = in.readInt();
        content = in.readString();
        praise = in.readInt();
        time_name = in.readString();
        secondLevelCommentsSize = in.readInt();
        user_id = in.readInt();
        name = in.readString();
        id = in.readInt();
        target_comment_id = in.readInt();
        real_praise = in.readInt();
        job = in.readString();
        status = in.readInt();
        is_praise = in.readByte() != 0;
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel in) {
            return new CommentBean(in);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public String getTime_name() {
        return time_name;
    }

    public void setTime_name(String time_name) {
        this.time_name = time_name;
    }

    public int getSecondLevelCommentsSize() {
        return secondLevelCommentsSize;
    }

    public void setSecondLevelCommentsSize(int secondLevelCommentsSize) {
        this.secondLevelCommentsSize = secondLevelCommentsSize;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTarget_comment_id() {
        return target_comment_id;
    }

    public void setTarget_comment_id(int target_comment_id) {
        this.target_comment_id = target_comment_id;
    }

    public int getReal_praise() {
        return real_praise;
    }

    public void setReal_praise(int real_praise) {
        this.real_praise = real_praise;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isIs_praise() {
        return is_praise;
    }

    public void setIs_praise(boolean is_praise) {
        this.is_praise = is_praise;
    }

    public ArrayList<CommentReplayBean> getCommentsLowerVOList() {
        return commentsLowerVOList;
    }

    public void setCommentsLowerVOList(ArrayList<CommentReplayBean> commentsLowerVOList) {
        this.commentsLowerVOList = commentsLowerVOList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(is_vip);
        dest.writeInt(sex);
        dest.writeLong(created_at);
        dest.writeString(avatar);
        dest.writeInt(movie_id);
        dest.writeString(content);
        dest.writeInt(praise);
        dest.writeString(time_name);
        dest.writeInt(secondLevelCommentsSize);
        dest.writeInt(user_id);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(target_comment_id);
        dest.writeInt(real_praise);
        dest.writeString(job);
        dest.writeInt(status);
        dest.writeByte((byte) (is_praise ? 1 : 0));
    }
}
