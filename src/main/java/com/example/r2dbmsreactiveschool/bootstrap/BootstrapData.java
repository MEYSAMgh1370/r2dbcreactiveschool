package com.example.r2dbmsreactiveschool.bootstrap;

import com.example.r2dbmsreactiveschool.domian.Course;
import com.example.r2dbmsreactiveschool.domian.Student;
import com.example.r2dbmsreactiveschool.repositories.CourseRepository;
import com.example.r2dbmsreactiveschool.repositories.StudentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by jt, Spring Framework Guru.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {
        courseRepository.deleteAll()
                .doOnSuccess(success -> loadCourseData())
                .subscribe();

        studentRepository.deleteAll()
                .doOnSuccess(success -> loadStudentData())
                .subscribe();
    }

    private void loadStudentData() {

        studentRepository.count().subscribe(count -> {
            if (count == 0) {
                Student student1 = Student.builder()
                        .sName("mohammad")
                        .sFamilyName("gholi")
                        .sFatherName("saman")
                        .birthDay()
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Student student2 = Student.builder()
                        .sName("mosatafa")
                        .sFamilyName("Poladi")
                        .sFatherName("heshmat")
                        .birthDay(LocalDate.of(1970,05,01))
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Student student3 = Student.builder()
                        .sName("asghar")
                        .sFamilyName("saboori")
                        .sFatherName("akbar")
                        .birthDay()
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                studentRepository.save(student1).subscribe();
                studentRepository.save(student2).subscribe();
                studentRepository.save(student3).subscribe();
            }
        });
    }

    private void loadCourseData() {
        courseRepository.count().subscribe(count -> {
            if(count == 0){
                courseRepository.save(Course.builder()
                        .name("shimi")
                                .build())
                        .subscribe();

                courseRepository.save(Course.builder()
                                .name("riazi")
                                .build())
                        .subscribe();

                courseRepository.save(Course.builder()
                                .name("honar")
                                .build())
                        .subscribe();
            }
        });
    }
}