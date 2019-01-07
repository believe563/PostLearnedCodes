package com.feng.demo.bmobdemo;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/4/28 0028.
 * 这个类对应了数据库中的一张表，而每一个成员变量都对应一个字段名
 * new一个feedback对象，就相当于表中多了一行数据
 */
public class Feedback extends BmobObject {
    private String name;
    private String feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
