package com.lucassprakel.alura.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassprakel.alura.models.Feedback;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCourseCode(String courseCode);
    
    boolean existsByUsernameAndCourseCode(String username, String courseCode);
    
    Feedback findByUsernameAndCourseCode(String username, String courseCode);
}
