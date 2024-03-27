package com.lucassprakel.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucassprakel.alura.models.Course;
import com.lucassprakel.alura.models.Registration;
import com.lucassprakel.alura.repositories.CourseRepository;
import com.lucassprakel.alura.repositories.RegistrationRepository;
import com.lucassprakel.alura.repositories.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RegistrationController {
    private final UsersRepository usersRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationController(UsersRepository usersRepository, CourseRepository courseRepository, RegistrationRepository registrationRepository) {
        this.usersRepository = usersRepository;
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
    }

    @GetMapping("/registrations")
    public List<Registration> listRegistration() {
        return registrationRepository.findAll();
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerRegistration(@RequestBody Registration registration) {
        String username = registration.getUsername();
        String courseCode = registration.getCourseCode();

        if (!usersRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + username);
        }

        if (!courseRepository.existsByCode(courseCode)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course not found: " + courseCode);
        }


        Course course = courseRepository.findByCode(courseCode);
        if (!course.getStatus().equalsIgnoreCase("Active")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Course " + courseCode + " is not Active.");
        }

        if (registrationRepository.existsByUsernameAndCourseCode(username, courseCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User " + username + " already enrolled in the course " + courseCode);
        }

        registration.setRegistrationDate(LocalDateTime.now());

        registrationRepository.save(registration);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successful registration.");
    }


}
