package com.example.r2dbmsreactiveschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourse {

    private long courseId;

    private long studentId;
//
//    public StudentCourse(final long courseId, final long studentId) {
//        this.courseId = courseId;
//        this.studentId = studentId;
//    }
}
