package entity;

import java.sql.Date;

public class SelectCourse {
private String courseid;
private String coursename;
private String stuid;
private Date seletime;
private String notes;
public String getCourseid() {
	return courseid;
}
public void setCourseid(String courseid) {
	this.courseid = courseid;
}
public String getCoursename() {
	return coursename;
}
public void setCoursename(String coursename) {
	this.coursename = coursename;
}
public String getStuid() {
	return stuid;
}
public void setStuid(String stuid) {
	this.stuid = stuid;
}
public Date getSeletime() {
	return seletime;
}
public void setSeletime(Date seletime) {
	this.seletime = seletime;
}
public String getNotes() {
	return notes;
}
public void setNotes(String notes) {
	this.notes = notes;
}
}
