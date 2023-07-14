package com.example.r2dbmsreactiveschool.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    private Long id;

    private String name;

    private String familyName;

    private String fatherName;

    private LocalDate birthDay;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Transient
    @ToString.Exclude
    private List<Course> courses;

    public void addCourse(final Course course) {
        if(courses == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
    }
}