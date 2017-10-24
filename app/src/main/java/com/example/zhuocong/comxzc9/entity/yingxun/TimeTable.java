package com.example.zhuocong.comxzc9.entity.yingxun;

/**
 * Created by wunaifu on 2017/9/19.
 */
public class TimeTable {

    String time;
    String date;
    String lan;
    String type;
    String price;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TimeTable{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", lan='" + lan + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
