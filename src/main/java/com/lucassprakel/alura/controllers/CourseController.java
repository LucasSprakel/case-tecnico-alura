package com.lucassprakel.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lucassprakel.alura.models.Course;
import com.lucassprakel.alura.models.Users;
import com.lucassprakel.alura.repositories.CourseRepository;
import com.lucassprakel.alura.repositories.UsersRepository;

import java.time.LocalDateTime;


@RestController
public class CourseController {
    private CourseRepository courseRepository;
    private final UsersRepository usersRepository;


    @Autowired
    public CourseController(UsersRepository usersRepository, CourseRepository courseRepository) {
        this.usersRepository = usersRepository;
        this.courseRepository = courseRepository;
    }


    @GetMapping("/courses")
    public Page<Course> listCourse(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(required = false) String status) {
        Pageable pageable = PageRequest.of(page, size);
        if (status != null) {
            return courseRepository.findByStatus(status, pageable);
        } else {
            return courseRepository.findAll(pageable);
        }
    }

   
    @PostMapping("/course")
    public ResponseEntity<String> registerCourse(@RequestBody Course course) {
        course.setCreationDate(LocalDateTime.now());
        course.setStatus("Active");


        // Checking whether the course code has only letters, no spaces, no numeric characters or no special characters, but can be separated by -
        if (!courseRepository.isValidCode(course.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code. Must contain only letters and -, and a maximum of 10 characters (Ex: alura-java).");
        }

        // Check if a course with this code already exists
        if (courseRepository.existsByCode(course.getCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is already a course with this code.");
        }

        String instructorUsername = course.getInstructor().toLowerCase();
        Users instructor = usersRepository.findByUsername(instructorUsername);

        // Check if instructor exists and has the role INSTRUCTOR
        if (instructor == null || !instructor.getRole().equals("INSTRUCTOR")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only instructor users can create courses.");
        }
        
        course.setInstructor(instructorUsername);

        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course registered successfully.");
    }   

    @PutMapping("/course/{code}/disable")
    public ResponseEntity<String> disableCourse(@PathVariable String code) {
        if (!courseRepository.existsByCode(code)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with code '" + code + "' not found.");
        }

        Course course = courseRepository.findByCode(code);
        // Desabilitar o curso aqui (por exemplo, atualizar o status)
        course.setStatus("Inactive");
        course.setInactivationDate(LocalDateTime.now());
        courseRepository.save(course);
        return ResponseEntity.ok("Course disabled successfully.");
    }

}
