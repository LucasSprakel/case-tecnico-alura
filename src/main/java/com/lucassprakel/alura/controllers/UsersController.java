package com.lucassprakel.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucassprakel.alura.models.Users;
import com.lucassprakel.alura.repositories.UsersRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;

    }


    @GetMapping("/users")
    public List<Users> listUsers() {
        return usersRepository.findAll();
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserDetailsByUsername(@PathVariable String username) {
        if (!usersRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + username);
        }

        // Search user by username
        Users user = usersRepository.findByUsername(username);

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("name", user.getName());
        userDetails.put("email", user.getEmail());
        userDetails.put("role", user.getRole());

        
        return ResponseEntity.ok(userDetails); 
    }
   
    
    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        user.setCreationDate(LocalDateTime.now());

        // Verifying that the username contains only lowercase letters and does not contain numbers and spaces
        if (!usersRepository.isValidUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username. It must contain only lowercase letters and no spaces.");
        }

        // Verifying that the email is in a valid format
        if (!usersRepository.isValidEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email address.");
        }

        // Check if this username already exists
        if (usersRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with the same username already exists.");
        }

        // Check if this email already exists
        if (usersRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with the same email already exists.");
        }

        // Verifying that the role is one of the valid options
        if (!("STUDENT".equals(user.getRole()) || "INSTRUCTOR".equals(user.getRole()) || "ADMIN".equals(user.getRole()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid role. Role must be one of: INSTRUCTOR, STUDENT, ADMIN.");
        }

        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }   

    
}