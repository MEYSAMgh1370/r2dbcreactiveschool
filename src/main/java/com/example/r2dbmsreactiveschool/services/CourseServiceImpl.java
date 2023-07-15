package com.example.r2dbmsreactiveschool.services;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.domain.StudentCourse;
import com.example.r2dbmsreactiveschool.domain.Student;
import com.example.r2dbmsreactiveschool.repositories.CourseRepository;
import com.example.r2dbmsreactiveschool.repositories.StudentCourseRepository;
import com.example.r2dbmsreactiveschool.repositories.CustomCourseRepository;
import com.example.r2dbmsreactiveschool.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CustomCourseRepository customCourseRepository;

    private final StudentRepository studentRepository;

    private final StudentCourseRepository studentCourseRepository;

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
                                savedStudents.forEach(course::addStudentAndReversed);

                                studentCourses.forEach(studentCourseRepository::save);
                                return studentCourseRepository.saveAll(studentCourses)
                                        .then(Mono.just(savedCourse));
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