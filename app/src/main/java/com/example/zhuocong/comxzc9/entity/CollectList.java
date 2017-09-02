package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/8/31.
 * 收藏列表页面显示的某部分数据的实体内容
 */

public class CollectList {
    private int postId;
    private int postPersonId;
    private String site;
    private String movieName;
    private String details;
    private String name;
    private String nickname;
    private String collectTime;

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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "CollectList{" +
                "postId=" + postId +
                ", postPersonId=" + postPersonId +
                ", site='" + site + '\'' +
                ", movieName='" + movieName + '\'' +
                ", details='" + details + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", collectTime='" + collectTime + '\'' +
                '}';
    }
}
