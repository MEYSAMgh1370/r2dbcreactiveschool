CREATE TABLE if NOT EXISTS student
(
    id                    integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`                varchar(255),
    family_name           varchar(255),
    father_name           varchar(255),
    birth_day             timestamp,
    created_date          timestamp,
    last_modified_date    timestamp
);

CREATE TABLE if NOT EXISTS course
(
    id                 integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`               varchar(255),
    `created_date`                timestamp,
    last_modified_date timestamp
);

CREATE TABLE if NOT EXISTS student_course
(
    student_id                   integer NOT NULL,
    course_id                    integer NOT NULL,
    PRIMARY KEY (student_id,course_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id)  REFERENCES course(id)
);