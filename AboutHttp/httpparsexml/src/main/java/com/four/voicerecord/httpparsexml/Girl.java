package com.four.voicerecord.httpparsexml;

/**
 * Created by fengjianghui on 2015/11/30.
 */
public class Girl {
    private String name;
    private int age;
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public String toString(){
        return "name="+name+",age="+age+",school="+school;
    }

}
