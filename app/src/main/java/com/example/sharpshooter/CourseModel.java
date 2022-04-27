package com.example.sharpshooter;

public class CourseModel {

    private String course_name;
    private int course_rating;


    // Constructor
    public CourseModel(String course_name, int course_rating) {
        this.course_name = course_name;
        this.course_rating = course_rating;

    }

    // Getter and Setter
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCourse_rating() {
        return course_rating;
    }

    public void setCourse_rating(int course_rating) {
        this.course_rating = course_rating;
    }
}