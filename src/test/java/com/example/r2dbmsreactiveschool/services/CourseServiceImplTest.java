package com.example.r2dbmsreactiveschool.services;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.model.CourseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@SpringBootTest
class CourseServiceImplTest {

    @Autowired
    private CourseService courseService;

    @Test
    void add() {

    }
}