package com.example.r2dbmsreactiveschool.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    private Long id;

    private String name;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Transient
    private List<Student> students;

    public void addStudent(final Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
}
