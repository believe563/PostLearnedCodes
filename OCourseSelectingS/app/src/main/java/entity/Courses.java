package entity;

import java.io.Serializable;

public class Courses implements Serializable{
private String id;
private String name;
private String type;
private String grade;
private String tname;
private String contents;
private String time;
private String place;
private int number;
private int remainnum;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getGrade() {
	return grade;
}
public void setGrade(String grade) {
	this.grade = grade;
}
public String getTname() {
	return tname;
}
public void setTname(String tname) {
	this.tname = tname;
}
public String getContents() {
	return contents;
}
public void setContents(String contents) {
	this.contents = contents;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getPlace() {
	return place;
}
public void setPlace(String place) {
	this.place = place;
}
public int getNumber() {
	return number;
}
public void setNumber(int number) {
	this.number = number;
}
public int getRemainnum() {
	return remainnum;
}
public void setRemainnum(int remainnum) {
	this.remainnum = remainnum;
}

}
