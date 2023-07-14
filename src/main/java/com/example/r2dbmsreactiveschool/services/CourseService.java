package com.example.r2dbmsreactiveschool.services;

import com.example.r2dbmsreactiveschool.domain.Course;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CourseService {

    Mono<Course> addOrUpdate(final Course course);

    Mono<Course> get(Long courseId);

    Flux<Course> getByIds(List<Long> courseIds);
}
