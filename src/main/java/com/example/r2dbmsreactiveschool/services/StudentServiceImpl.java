package com.example.r2dbmsreactiveschool.services;

import com.example.r2dbmsreactiveschool.mapper.StudentMapper;
import com.example.r2dbmsreactiveschool.model.StudentDTO;
import com.example.r2dbmsreactiveschool.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;



    @Override
    public Mono<StudentDTO> addStudent(Mono<StudentDTO> studentDTO) {
        return studentDTO.map(studentMapper::fromDto)
                .flatMap(studentRepository::save)
                .map(studentMapper::toDto);
    }

    @Override
    public Mono<StudentDTO> getStudentById(final Long studentId) {
        return studentRepository.findById(studentId)
                .map(studentMapper::toDto);
    }

    @Override
    public Flux<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .map(studentMapper::toDto);
    }

    @Override
    public Mono<StudentDTO> updateStudent(final Long studentId, StudentDTO studentDTO) {
        return studentRepository.findById(studentId)
                .map(foundStudent ->
                {
                    foundStudent.setName(studentDTO.getName());
                    foundStudent.setFamilyName(studentDTO.getFamilyName());
                    foundStudent.setBirthDay(studentDTO.getBirthDay());
                    foundStudent.setFatherName(studentDTO.getFatherName());
                    foundStudent.setLastModifiedDate(LocalDateTime.now());
                    return foundStudent;
                }).flatMap(studentRepository::save)
                .map(studentMapper::toDto);
    }
}