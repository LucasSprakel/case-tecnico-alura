package com.lucassprakel.alura.repositories;

import com.lucassprakel.alura.models.Users;

import java.util.regex.Pattern;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    default boolean isValidUsername(String username) {

        return username != null && Pattern.matches("[a-z]+", username);
    }

    default boolean isValidEmail(String email) {
       
        return email != null && Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email);
    }

    Users findByUsername(String username);
}