package com.example.r2dbmsreactiveschool.services;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.domain.StudentCourse;
import com.example.r2dbmsreactiveschool.domain.Student;
import com.example.r2dbmsreactiveschool.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CustomCourseRepository customCourseRepository;

    private final StudentRepository studentRepository;

    private final StudentCourseRepository studentCourseRepository;

    private final CustomStudentCourseRepository customStudentCourseRepository;

    @Override
    public Mono<Course> addOrUpdateV2(final Course course) {
        return courseRepository.save(course)
                .flatMap(savedCourse -> {
                    return studentRepository.saveAll(course.getStudents())
                            .collectList()
                            .flatMap(savedStudents -> {
                                List<StudentCourse> studentCourses = savedStudents.stream()
                                        .map(savedStudent -> new StudentCourse(savedCourse.getId(), savedStudent.getId()))
                                        .toList();

                                savedStudents.forEach(savedCourse::addStudentAndReversed);

                                return customStudentCourseRepository.merge(studentCourses)
                                        .then(Mono.just(savedCourse));
//                                List<Mono<Void>> monos = studentCourses.stream()
//                                        .map(customStudentCourseRepository::merge)
//                                        .toList();
//
//                                return Mono.zip(monos, objects -> new Object())
//                                        .then(Mono.just(savedCourse));
                            });
                });
    }

    @Override
    public Mono<Course> addOrUpdate(final Course newCourse) {
        return courseRepository.save(newCourse)
                .flatMap(savedCourse -> {
                    return studentRepository.saveAll(newCourse.getStudents())
                            .collectList()
                            .flatMap(savedStudents -> {
                                final List<Long> studentIds = savedStudents.stream()
                                        .map(Student::getId)
                                        .toList();

                                return customCourseRepository.updateCourseStudents(savedCourse.getId(), studentIds)
                                        .then(Mono.just(savedCourse));
                            });

                });
    }

    @Override
    public Mono<Course> get(final Long courseId) {
//        return courseRepository.findById(courseId);
        return customCourseRepository.findById(courseId);
    }

    @Override
    public Flux<Course> getByIds(final List<Long> courseIds) {
        return customCourseRepository.findByIds(courseIds);
    }
}