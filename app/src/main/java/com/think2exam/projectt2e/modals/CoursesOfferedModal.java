package com.think2exam.projectt2e.modals;

public class CoursesOfferedModal{
    public String courseType;
    public String courseName;

    public CoursesOfferedModal(String courseType, String courseName) {
        this.courseType = courseType;
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public String getCourseName() {
        return courseName;
    }
}