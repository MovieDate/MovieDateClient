package com.example.zhuocong.comxzc9.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/9.
 */
public class User {
    private int id;//自增
    private String phone;//联系方式（登陆账号）
    private String password;
    private String nickname;//昵称
    private String name;
    private int gender;//性别:男（0）女（1）
    private int age;
    private String habit;//爱好
    private String birthday;//生日
    private String job;//职业
    private String address;
    private String weight;//体重
    private String height;//身高
    private String xingZuo;//星座
    private String signature;//个性签名
    private List<User> list;


    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getXingZuo() {
        return xingZuo;
    }

    public void setXingZuo(String xingZuo) {
        this.xingZuo = xingZuo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", habit='" + habit + '\'' +
                ", birthday=" + birthday +
                ", job='" + job + '\'' +
                ", address='" + address + '\'' +
                ", weigh='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", xingZuo='" + xingZuo + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
