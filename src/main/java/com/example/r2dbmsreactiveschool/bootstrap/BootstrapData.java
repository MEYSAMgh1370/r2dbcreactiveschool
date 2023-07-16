package com.example.r2dbmsreactiveschool.bootstrap;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.domain.Student;
import com.example.r2dbmsreactiveschool.repositories.CourseRepository;
import com.example.r2dbmsreactiveschool.repositories.StudentRepository;
import com.example.r2dbmsreactiveschool.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jt, Spring Framework Guru.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private final CourseService courseService;

    @Override
    public void run(String... args) {
;
        Mono.zip(studentRepository.deleteAll(),courseRepository.deleteAll())
                        .doOnSuccess(varr -> loadRandomData())
                .subscribe();

    }

    private void loadRandomData() {
        final List<Course> courses = createCourses();

        final List<Student> students = createStudents();


        for(int i=0; i<3; i++) {
            Course course = courses.get(i);
            Student student = students.get(i);

            course.addStudent(student);
            student.addCourse(course);
        }

        for (Course course : courses) {
            courseService.addOrUpdate(course).subscribe();
        }



    }

    private List<Student> createStudents() {
        return List.of(
                Student.builder()
                        .name("mohammad")
                        .familyName("gholi")
                        .fatherName("saman")
                        .birthDay(LocalDate.of(1992, 2, 2))
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build(),
                Student.builder()
                        .name("mosatafa")
                        .familyName("Poladi")
                        .fatherName("heshmat")
                        .birthDay(LocalDate.of(1970, 5, 1))
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build(),
                Student.builder()
                        .name("asghar")
                        .familyName("saboori")
                        .fatherName("akbar")
                        .birthDay(LocalDate.of(1992, 4, 10))
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build()
        );
    }

    private List<Course> createCourses() {
        return List.of(
                Course.builder()
                        .name("shimi")
                        .build()
                , Course.builder()
                        .name("riazi")
                        .build(),
                Course.builder()
                        .name("honar")
                        .build()
        );
    }
}