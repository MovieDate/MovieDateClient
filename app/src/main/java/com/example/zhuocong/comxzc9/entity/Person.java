package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class Person {
    private int id;      //自动编号,自增长
    private int postId;   //帖子id
    private int startPersonId;   //发起约影人id
    private int byPersonId;    //被约人id
    private String personTime;  //时间（建立该条记录时的时间，后台处理就行）

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

    public int getStartPersonId() {
        return startPersonId;
    }

    public void setStartPersonId(int startPersonId) {
        this.startPersonId = startPersonId;
    }

    public int getByPersonId() {
        return byPersonId;
    }

    public void setByPersonId(int byPersonId) {
        this.byPersonId = byPersonId;
    }

    public String getPersonTime() {
        return personTime;
    }

    public void setPersonTime(String personTime) {
        this.personTime = personTime;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", postId=" + postId +
                ", startPersonId=" + startPersonId +
                ", byPersonId=" + byPersonId +
                ", personTime='" + personTime + '\'' +
                '}';
    }
}
