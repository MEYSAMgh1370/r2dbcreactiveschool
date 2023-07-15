package com.example.r2dbmsreactiveschool.repositories;

import com.example.r2dbmsreactiveschool.domain.StudentCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomStudentCourseRepository {

    private final DatabaseClient databaseClient;

    public Mono<Void> merge(final StudentCourse studentCourse) {
        StringBuilder sb = new StringBuilder("MERGE INTO STUDENT_COURSE (COURSE_ID, STUDENT_ID) VALUES ")
                .append("(")
                .append(studentCourse.getCourseId())
                .append(", ")
                .append(studentCourse.getStudentId())
                .append(")");

        return databaseClient.sql(sb.toString()).then();
    }
}
