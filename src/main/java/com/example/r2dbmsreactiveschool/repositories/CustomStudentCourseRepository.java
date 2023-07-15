package com.example.r2dbmsreactiveschool.repositories;

import com.example.r2dbmsreactiveschool.domain.StudentCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomStudentCourseRepository {

    private final DatabaseClient databaseClient;

    private final CustomCourseRepository customCourseRepository;

    public Mono<Void> merge(final StudentCourse studentCourse) {
        StringBuilder sb = new StringBuilder("MERGE INTO STUDENT_COURSE (COURSE_ID, STUDENT_ID) VALUES ")
                .append("(")
                .append(studentCourse.getCourseId())
                .append(", ")
                .append(studentCourse.getStudentId())
                .append(")");

        return databaseClient.sql(sb.toString()).then();
    }

    public Mono<Void> merge(final List<StudentCourse> studentCourses) {
        StringBuilder sb = new StringBuilder("MERGE INTO STUDENT_COURSE (COURSE_ID, STUDENT_ID) VALUES ");
        Iterator<StudentCourse> studentCoursesItr = studentCourses.iterator();
        while(studentCoursesItr.hasNext()) {
            StudentCourse studentCourse = studentCoursesItr.next();
            sb.append("(")
                    .append(studentCourse.getCourseId())
                    .append(", ")
                    .append(studentCourse.getStudentId())
                    .append(")");

            if(studentCoursesItr.hasNext()) {
                sb.append(", ");
            }
        }

        return databaseClient.sql(sb.toString()).then();
    }
}
