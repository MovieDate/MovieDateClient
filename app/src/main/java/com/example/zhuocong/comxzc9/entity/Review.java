package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class Review {
    private int id;      //自动编号,自增长
    private int postId;  //帖子id
    private int postPersonId;  //发表人id
    private String reviewDetails;  //评论内容
    private String reviewTime;   //评论时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getPostPersonId() {
        return postPersonId;
    }

    public void setPostPersonId(int postPersonId) {
        this.postPersonId = postPersonId;
    }

    public String getReviewDetails() {
        return reviewDetails;
    }

    public void setReviewDetails(String reviewDetails) {
        this.reviewDetails = reviewDetails;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", postId=" + postId +
                ", postPersonId=" + postPersonId +
                ", reviewDetails='" + reviewDetails + '\'' +
                ", reviewTime='" + reviewTime + '\'' +
                '}';
    }
}
