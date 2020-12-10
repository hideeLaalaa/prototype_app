package com.example.proto102;

public class Course {

    private int xId;
    private String xCourse;
    private int xCarry;
    private String xLecturer;
    private String xNote;

    public Course(int id,String course,int carry,String lecturer, String note){
        xId=id;
        xCarry=carry;
        xCourse=course;
        xLecturer=lecturer;
        xNote=note;
    }

    public int getxId() {
        return xId;
    }

    public void setxId(int xId) {
        this.xId = xId;
    }

    public String getxCourse() {
        return xCourse;
    }

    public void setxCourse(String xCourse) {
        this.xCourse = xCourse;
    }

    public int getxCarry() {
        return xCarry;
    }

    public void setxCarry(int xCarry) {
        this.xCarry = xCarry;
    }

    public String getxLecturer() {
        return xLecturer;
    }

    public void setxLecturer(String xLecturer) {
        this.xLecturer = xLecturer;
    }

    public String getxNote() {
        return xNote;
    }

    public void setxNote(String xNote) {
        this.xNote = xNote;
    }


}
