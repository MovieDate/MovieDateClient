package com.example.zhuocong.comxzc9.entity;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class PersonList {
    private String name;
    private int byPersonId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getByPersonId() {
        return byPersonId;
    }

    public void setByPersonId(int byPersonId) {
        this.byPersonId = byPersonId;
    }

    @Override
    public String toString() {
        return "PersonList{" +
                "name='" + name + '\'' +
                ", byPersonId=" + byPersonId +
                '}';
    }
}
