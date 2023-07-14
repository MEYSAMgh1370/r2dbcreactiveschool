package com.example.r2dbmsreactiveschool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by jt, Spring Framework Guru.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    private String name;

    private String familyName;

    private String fatherName;

    private LocalDate birthDay;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}