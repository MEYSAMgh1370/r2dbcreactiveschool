package com.example.r2dbmsreactiveschool.repositories;

import ch.qos.logback.core.pattern.util.IEscapeUtil;
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

    public Flux<Course> findByIds(final List<Long> courseIds) {
        final StringBuilder sb = new StringBuilder("""
                select course.id as courseId, course.name as courseName,
                    student.id as studentId, student.name as studentName
                from course join student_course on course.id = student_course.course_id
                join student on student.id = student_course.student_id
                """)
                .append("where course.id in (");
        Iterator<Long> coursesItr = courseIds.iterator();
        while (coursesItr.hasNext()) {
            Long courseId = coursesItr.next();
            sb.append(courseId);
            if (coursesItr.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(")");

        return databaseClient.sql(sb.toString()).fetch()
                .all()
                .buffer()
                .single()
                .flatMap(result -> {
                    final Map<Long, Course> idToCourse = new HashMap<>();
                    final Map<Long, Student> idToStudent = new HashMap<>();

                    for (Map<String, Object> row : result) {
                        long courseId = ((Number) row.get("courseId")).longValue();
                        final Course course = idToCourse.computeIfAbsent(courseId, _id -> Course.builder()
                                .id(courseId)
                                .name(row.get("courseName").toString())
                                .build());

                        final Long studentId = Optional.ofNullable(row.get("studentId"))
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
        final StringBuilder sb = new StringBuilder("""
                select course.id as courseId, course.name as courseName,
                    student.id as studentId, student.name as studentName
                from course join student_course on course.id = student_course.course_id
                join student on student.id = student_course.student_id
                """)
                .append("where course.id = ").append(courseId);
        return databaseClient.sql(sb.toString()).fetch()
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

                    return Mono.just(course);
                });
    }

    private Student createStudent(final Map<String, Object> row) {
        return Student.builder()
                .id(((Number) row.get("studentId")).longValue())
                .name(row.get("studentName").toString())
                .build();
    }

    public Mono<Void> updateCourseStudents(final Long courseId, List<Long> studentIds) {
//        """
//                if not exist (select 1 from courseStudent where courceId = 10 and studentId = 100)
//                insert into courseStudent (a,b,c) values (a,2,3)
//                """
//        """
//                MERGE INTO CUSTOMER KEY (ID) VALUES (8, 'Lokesh', 32, 'Hyderabad', 2500);
//                """

        final StringBuilder sb = new StringBuilder("merge into student_course key (student_id) values ");
        Iterator<Long> studentsItr = studentIds.iterator();
        while (studentsItr.hasNext()) {
            final Long studentId = studentsItr.next();
            sb.append("(").append(studentId).append(", ").append(courseId).append(")");
            if (studentsItr.hasNext()) {
                sb.append(", ");
            }
        }

        return databaseClient.sql(sb.toString()).then();
    }
}
