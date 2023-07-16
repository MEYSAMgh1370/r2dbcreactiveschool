package com.example.r2dbmsreactiveschool.mapper;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.model.CourseDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Mapper
public interface CourseMapper {

    Course fromDto(final CourseDTO courseDTO);

    CourseDTO toDto(final Course course);
}
