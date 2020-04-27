package com.video.yali.param;

public class CommentPutParas {

    public String movie_id;     //影片ID
    public String content;     //评论内容
    public String first_comment_id;     //一级评论的id
    public String target_comment_id;     //二级多级评论时，填写上一级的评论id

    public CommentPutParas(String movie_id, String content, String first_comment_id, String target_comment_id) {
        this.movie_id = movie_id;
        this.content = content;
        this.first_comment_id = first_comment_id;
        this.target_comment_id = target_comment_id;
    }
}
