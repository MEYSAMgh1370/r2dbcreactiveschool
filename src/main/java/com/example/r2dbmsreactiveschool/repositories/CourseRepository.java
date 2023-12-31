package com.example.r2dbmsreactiveschool.repositories;

import com.example.r2dbmsreactiveschool.domain.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ReactiveCrudRepository<Course, Long> {


}
