package com.example.asm_firebase.Model;

public class Course {
    private String id, name, semester, status;
    private float scores;

    public Course() {
    }

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(String id, String name, String semester) {
        this.id = id;
        this.name = name;
        this.semester = semester;
    }

    public Course(String id, String name,  float scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStatus() {
        if (getScores() >= 5){
            status = "Passed";
        }else {
            status = "Learning";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getScores() {
        return scores;
    }

    public void setScores(float scores) {
        this.scores = scores;
    }
}
