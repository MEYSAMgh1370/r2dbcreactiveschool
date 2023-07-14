package com.example.r2dbmsreactiveschool.mapper;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.model.CourseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CourseMapper {

    public Course fromDto(final CourseDTO courseDTO) {
        final LocalDateTime now = LocalDateTime.now();
        final Course course = Course.builder()
                .name(courseDTO.getName())
                .createdDate(now)
                .lastModifiedDate(now)
                .build();
        return course;
    }

    public CourseDTO toDto(final Course course) {
        final CourseDTO courseDTO = CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .createdDate(course.getCreatedDate())
                .lastModifiedDate(course.getLastModifiedDate())
                .build();
        return courseDTO;
    }
}
