package com.example.r2dbmsreactiveschool.bootstrap;

import com.example.r2dbmsreactiveschool.domain.Course;
import com.example.r2dbmsreactiveschool.domain.Student;
import com.example.r2dbmsreactiveschool.repositories.CourseRepository;
import com.example.r2dbmsreactiveschool.repositories.StudentRepository;
import com.example.r2dbmsreactiveschool.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
        courseRepository.deleteAll().block();
        studentRepository.deleteAll().block();

//        loadRandomData();
        loadRandomDataV2();
    }

    private void loadRandomDataV2() {
        List<Course> courses = createCourses();
        List<Student> students = createStudents();

//        final Random rand = new Random(2L);
//        for (final Course course : courses) {
//            for (final Student student : students) {
//                if (rand.nextBoolean()) {
//                    course.addStudent(student);
//                    student.addCourse(course);
//                }
//            }
//        }

        Course firstCourse = courses.get(0);
        Course secondCourse = courses.get(1);
        Student firstStudent = students.get(0);
        Student secondStudent = students.get(1);

        firstCourse.addStudentAndReversed(firstStudent);
        firstCourse.addStudentAndReversed(secondStudent);
        secondCourse.addStudentAndReversed(secondStudent);

        for (Course course : courses) {
            courseService.addOrUpdateV2(course).block();
        }

        final Course shimi = courseService.get(1L).block();
        final Course riazi = courseService.get(2L).block();
//        final Course honar = courseService.get(3L).block();

        shimi.setName("jingool");

        courseService.addOrUpdateV2(shimi).block();

        Course jingool = courseService.get(shimi.getId()).block();
        System.out.println(shimi);
    }

    private void loadRandomData() {
        final List<Course> courses = createCourses();

        final List<Student> students = createStudents();

        // randomly related each Course with a set of Students
//        final Random rand = new Random(2L);
//        for (final Course course : courses) {
//            for (final Student student : students) {
//                if (rand.nextBoolean()) {
//                    course.addStudent(student);
//                    student.addCourse(course);
//                }
//            }
//        }

        for(int i=0; i<3; i++) {
            Course course = courses.get(i);
            Student student = students.get(i);

            course.addStudent(student);
            student.addCourse(course);
        }

        for (Course course : courses) {
            courseService.addOrUpdate(course).block();
        }

        final Course shimi = courseService.get(1L).block();
        final Course riazi = courseService.get(2L).block();
        final Course honar = courseService.get(3L).block();

        honar.setName("jingool");

        courseService.addOrUpdate(honar).block();

        Course jingool = courseService.get(honar.getId()).block();
        System.out.println(shimi);

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
                        .build()
//                ,Course.builder()
//                        .name("honar")
//                        .build()
        );
    }
}