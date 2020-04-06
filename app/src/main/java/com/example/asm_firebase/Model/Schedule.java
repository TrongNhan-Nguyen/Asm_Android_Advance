package com.example.asm_firebase.Model;

public class Schedule {
    private String date, courseId, courseName, dayOfWeek;
    private int block, shift;
    private long longDate;

    public Schedule() {
    }

    public Schedule(String dayOfWeek, int block, int shift) {
        this.dayOfWeek = dayOfWeek;
        this.block = block;
        this.shift = shift;
    }

    public Schedule(String courseId, String courseName, String dayOfWeek, int block, int shift) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.dayOfWeek = dayOfWeek;
        this.block = block;
        this.shift = shift;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public long getLongDate() {
        return longDate;
    }

    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }
}
