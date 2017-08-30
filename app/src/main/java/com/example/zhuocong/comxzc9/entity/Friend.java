package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/8/30.
 */

public class Friend {
    private int id;      //自动编号,自增长
    private int myId;   //我的ID
    private int friendId;  //好友id
    private String addTime;  //添加时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", myId=" + myId +
                ", friendId=" + friendId +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
