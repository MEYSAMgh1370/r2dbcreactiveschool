package com.example.r2dbmsreactiveschool.repositories;

import com.example.r2dbmsreactiveschool.domian.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {


}
