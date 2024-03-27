package com.lucassprakel.alura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassprakel.alura.models.Registration;



public interface RegistrationRepository extends JpaRepository<Registration, Long>{
    boolean existsByUsernameAndCourseCode(String username, String courseCode);
    int countByCourseCode(String courseCode);
}
