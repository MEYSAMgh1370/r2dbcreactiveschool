package com.example.r2dbmsreactiveschool.repositories;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class CustomCourseRepository {
    private final DatabaseClient databaseClient;
    public Flux<Course> findByIds(List<Long> courseIds) {
        final String query =( """
                select course.id as courseId, course.name as courseName,
                    student.id as studentId, student.name as studentName
                from course join student_course on course.id = student_course.course_id
                join student on student.id = student_course.student_id
                WHERE course.id IN (:ids)""");
        return databaseClient.sql(query)
                .bind("ids", courseIds)
                .fetch()
                .all()
                .buffer()
                .single()
                .flatMap(result -> {
                    final Map<Long, Course> idToCourse = new HashMap<>();
                    final Map<Long, Student> idToStudent = new HashMap<>();

                    for (Map<String, Object> row : result) {
                        long courseId = ((Number) row.get("id")).longValue();
                        final Course course = idToCourse.computeIfAbsent(courseId, _id -> Course.builder()
                                .id(courseId)
                                .name(row.get("name").toString())
                                .build());

                        final Long studentId = Optional.ofNullable(row.get("student_id"))
                                .map(Number.class::cast)
                                .map(Number::longValue)
                                .orElse(null);
                        if (studentId != null) {
                            final Student student = idToStudent.computeIfAbsent(studentId, _id -> createStudent(row));
                            course.addStudent(student);
                            student.addCourse(course);
                        }
                    }
                    return Mono.just(new ArrayList<>(idToCourse.values()));
                })
                .flatMapIterable(item -> item);
    }

    public Mono<Course> findById(final Long courseId) {
        final String query =("""
                select course.id as courseId, course.name as courseName,
                student.id as studentId, student.name as studentName
                from course join student_course on course.id = student_course.course_id
                join student on student.id = student_course.student_id
                where course.id = (:courseId) """);
        return databaseClient.sql(query)
                .bind("courseID", courseId)
                .fetch()
                .all()
                .buffer()
                .single()
                .flatMap(result -> {
                    Course course = null;
                    for (Map<String, Object> row : result) {
                        if (course == null) {
                            course = Course.builder()
                                    .id(((Number) row.get("courseId")).longValue())
                                    .name(row.get("courseName").toString())
                                    .build();
                        }
                        final Student student = createStudent(row);
                        course.addStudent(student);
                        student.addCourse(course);
                    }

                    assert course != null;
                    return Mono.just(course);
                });
    }

    private Student createStudent(final Map<String, Object> row) {
        return Student.builder()
                .id(((Number) row.get("studentId")).longValue())
                .name(row.get("studentName").toString())
                .build();
    }

    public Mono<Void> updateCourseStudents(Long courseId, List<Long> studentIds) {
        String query = "MERGE INTO student_course KEY (student_id) VALUES (:studentId, :courseId)";
        return Flux.fromIterable(studentIds)
                .flatMap(studentId -> databaseClient.sql(query)
                        .bind("studentId", studentId)
                        .bind("courseId", courseId)
                        .fetch()
                        .rowsUpdated())
                .then();
    }
}
