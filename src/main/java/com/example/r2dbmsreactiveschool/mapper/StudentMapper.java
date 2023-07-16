package com.example.r2dbmsreactiveschool.mapper;

import com.example.r2dbmsreactiveschool.domain.Student;
import com.example.r2dbmsreactiveschool.model.StudentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    Student fromDto(StudentDTO studentDTO);

    StudentDTO toDto(Student student);
}
