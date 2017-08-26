package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/8/25.
 */

public class Post {
    private int id;      //自动编号,自增长
    private int postPersonId;    //发帖人的id
    private String postTime;       //发帖时间
    private String site;        //地点
    private String movieName;     //约影的影片名字
    private String movieTime;     //约影时间
    private int sex;         //约影对象性别（0表示男，1表示女，2表示不限制）
    private String endTime;      //完成约影时间（不显示，在约影完成按键直接获取存储）
    private int movieType;     //约影类型（0为2人单独约影。1为多人或团体约影，PP端获取约影类型时，获取约影人表，设置好报名人数，如果超过报名人数，约影人可挑选）
    private String details;       //发帖内容

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostPersonId() {
        return postPersonId;
    }

    public void setPostPersonId(int postPersonId) {
        this.postPersonId = postPersonId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
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

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMovieType() {
        return movieType;
    }

    public void setMovieType(int movieType) {
        this.movieType = movieType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postPersonId=" + postPersonId +
                ", postTime='" + postTime + '\'' +
                ", site='" + site + '\'' +
                ", movieName='" + movieName + '\'' +
                ", movieTime='" + movieTime + '\'' +
                ", sex=" + sex +
                ", endTime='" + endTime + '\'' +
                ", movieType=" + movieType +
                ", details='" + details + '\'' +
                '}';
    }
}
