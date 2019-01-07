package com.four.voicerecord.jsondemo;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengjianghui on 2015/11/26.
 * //写在服务器端的onPost方法中
 */
public class JsonTest {
    public static void main(String[] args){
        Result result=new Result();
        result.setResult(1);
        List<Person> personList = new ArrayList<Person>();
        Person person=new Person();
        person.setName("zhangsan");
        person.setAge(12);
        person.setUrl("http://mg.soupingguo.com/bizhi/big/10/055/499/10055499.jpg");
        List<SchoolInfo> schoolInfos = new ArrayList<SchoolInfo>();
        SchoolInfo schoolInfo1 = new SchoolInfo();
        schoolInfo1.setSchoolName("连大");
        SchoolInfo schoolInfo2 = new SchoolInfo();
        schoolInfo2.setSchoolName("DalianUniversity");
        schoolInfos.add(schoolInfo1);
        schoolInfos.add(schoolInfo2);
        person.setSchoolInfo(schoolInfos);
        personList.add(person);

        Person person1=new Person();
        person.setName("zhangsan");
        person.setAge(12);
        person.setUrl("http://mg.soupingguo.com/bizhi/big/10/055/499/10055499.jpg");
        List<SchoolInfo> schoolInfos1 = new ArrayList<SchoolInfo>();
        SchoolInfo schoolInfo3 = new SchoolInfo();
        schoolInfo3.setSchoolName("连大");
        SchoolInfo schoolInfo4 = new SchoolInfo();
        schoolInfo4.setSchoolName("DalianUniversity");
        schoolInfos.add(schoolInfo3);
        schoolInfos.add(schoolInfo4);
        person.setSchoolInfo(schoolInfos);
        result.setPersonData(personList);

        Gson gson=new Gson();
        System.out.println(gson.toJson(result));
    }
}
