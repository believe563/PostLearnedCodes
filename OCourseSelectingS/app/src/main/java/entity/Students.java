package entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Students implements Parcelable,Serializable {
    private String name;
    private String gender;
    private int age;
    private String id;
    private String grade;
    private String classes;
    private String pwd;
    private String notes;

    public Students(){

    }
    public Students(String name, String gender, int age, String id, String grade, String classes, String pwd, String notes) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.id = id;
        this.grade = grade;
        this.classes = classes;
        this.pwd = pwd;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    //实现Parcelable接口的public void writeToParcel(Parcel dest, int flags)方法,通常进行重写
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeInt(age);
        dest.writeString(id);
        dest.writeString(grade);
        dest.writeString(classes);
        dest.writeString(pwd);
        dest.writeString(notes);
    }

    //自定义类型中必须含有一个名称为CREATOR的静态成员，该成员对象要求实现Parcelable.Creator接口及其方法
    public static final Parcelable.Creator<Students> CREATOR = new Parcelable.Creator<Students>() {

        @Override
        public Students createFromParcel(Parcel source) {
            //从Parcel中读取数据
            //此处read顺序依据write顺序
            return new Students(source.readString(), source.readString(), source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString());
        }

        @Override
        public Students[] newArray(int size) {
            return new Students[size];
        }
    };
}
