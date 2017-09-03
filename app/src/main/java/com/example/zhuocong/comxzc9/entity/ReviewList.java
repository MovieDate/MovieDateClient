package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class ReviewList {
    private int postId;  //帖子id
    private int postPersonId;  //发表人id
    private String reviewDetails;  //评论内容
    private String reviewTime;   //评论时间
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReviewList{" +
                "postId=" + postId +
                ", postPersonId=" + postPersonId +
                ", reviewDetails='" + reviewDetails + '\'' +
                ", reviewTime='" + reviewTime + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
