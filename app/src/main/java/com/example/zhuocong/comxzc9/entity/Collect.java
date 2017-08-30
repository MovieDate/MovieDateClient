package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/8/29.
 */

public class Collect {

    private int id;      //自动编号,自增长
    private int postId;  //帖子id
    private int collecterId;   //收藏人id
    private String collectTime;//收藏时间

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

    public int getCollecterId() {
        return collecterId;
    }

    public void setCollecterId(int collecterId) {
        this.collecterId = collecterId;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "id=" + id +
                ", postId=" + postId +
                ", collecterId=" + collecterId +
                ", collectTime='" + collectTime + '\'' +
                '}';
    }
}
