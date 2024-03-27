package com.lucassprakel.alura.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassprakel.alura.models.Course;


import java.util.regex.Pattern;
import org.springframework.data.domain.Page;


public interface CourseRepository extends JpaRepository<Course, Long>{
    boolean existsByCode(String code);

    default boolean isValidCode(String code) {

        return code != null && Pattern.matches("^[a-zA-Z]+(-[a-zA-Z]+)*$", code);
    }

    Page<Course> findByStatus(String status, Pageable pageable);
    Course findByCode(String code);
}
