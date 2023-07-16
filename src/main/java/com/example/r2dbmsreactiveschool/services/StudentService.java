package com.example.r2dbmsreactiveschool.services;

import com.example.r2dbmsreactiveschool.model.StudentDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

    Mono<StudentDTO> addStudent(Mono<StudentDTO> studentDTO);

    Mono<StudentDTO> getStudentById(Long studentId);

    Flux<StudentDTO> getStudents();

    Mono<StudentDTO> updateStudent(Long studentId, StudentDTO studentDTO);
}
