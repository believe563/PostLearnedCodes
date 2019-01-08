package com.courseselecting.onlinesystem.ocourseselectings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import entity.Students;

/**
 * Created by Administrator on 2016/3/22.
 * 个人信息
 */
public class PersonalInfoActivity extends Activity {

    private Students student;
    private TextView tv_personalInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo_layout);
        tv_personalInfo = (TextView) findViewById(R.id.tv_personalInfo);
        getInfo();
        tv_personalInfo.setText("姓名:"+student.getName()+"\n性别:"+student.getGender()+"\n年龄:"+student.getAge()+"\n身份证号:"+student.getId()+"\n年级:"+student.getGrade()+"\n班级"+student.getClasses()+"\n备注:"+student.getNotes());
    }

    private void getInfo() {
        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        List studentList = (List<Students>) bundle.getSerializable("student");
        student = (Students) studentList.get(0);
    }
}
