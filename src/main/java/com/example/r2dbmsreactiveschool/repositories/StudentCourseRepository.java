package com.example.r2dbmsreactiveschool.repositories;

import com.example.r2dbmsreactiveschool.domain.StudentCourse;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends ReactiveCrudRepository<StudentCourse, Long> {


}
