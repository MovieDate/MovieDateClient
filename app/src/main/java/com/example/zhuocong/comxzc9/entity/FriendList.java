package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/9/1.
 */

public class FriendList {
    private String name;
    private int friendId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "FriendList{" +
                "name='" + name + '\'' +
                ", friendId=" + friendId +
                '}';
    }
}
