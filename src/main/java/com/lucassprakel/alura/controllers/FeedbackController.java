package com.lucassprakel.alura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lucassprakel.alura.feedback.EmailSender;
import com.lucassprakel.alura.models.Course;
import com.lucassprakel.alura.models.Feedback;
import com.lucassprakel.alura.models.Users;
import com.lucassprakel.alura.repositories.CourseRepository;
import com.lucassprakel.alura.repositories.FeedbackRepository;
import com.lucassprakel.alura.repositories.RegistrationRepository;
import com.lucassprakel.alura.repositories.UsersRepository;

import java.util.List;

@RestController
public class FeedbackController {
    private final UsersRepository usersRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackController(UsersRepository usersRepository, CourseRepository courseRepository, RegistrationRepository registrationRepository, FeedbackRepository feedbackRepository) {
        this.usersRepository = usersRepository;
        this.courseRepository = courseRepository;
        this.registrationRepository = registrationRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/feedback")
    public ResponseEntity<String> submitCourseFeedback(@RequestBody Feedback feedbackRequest) {
        String username = feedbackRequest.getUsername();
        String courseCode = feedbackRequest.getCourseCode();
        int rating = feedbackRequest.getRating();
        String feedback = feedbackRequest.getFeedback();

        // Check if the user is enrolled in the course
        if (!registrationRepository.existsByUsernameAndCourseCode(username, courseCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("User " + username + " is not registered for the course " + courseCode);
        }

        // Check if the user has already submitted feedback for this course
        if (feedbackRepository.existsByUsernameAndCourseCode(username, courseCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("User " + username + " has already submitted feedback for the course " + courseCode);
        }

        // Verifica se a nota está entre 0 e 10
        if (rating < 0 || rating > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Rating must be between 0 and 10.");
        }

        Course course = courseRepository.findByCode(courseCode);


        //  Gets the instructor's email
        String instructorUsername = course.getInstructor();
        Users instructor = usersRepository.findByUsername(instructorUsername);
        String instructorEmail = instructor.getEmail();


        // Send an email to the instructor if the rating is less than 6
        if (rating < 6) {
            String subject = "Course feedback " + course.getCode();
            String body = "Your course " + course.getName() + " received feedback with a rating of " + rating
                    + ". Reason: " + feedback;
            EmailSender.send(instructorEmail, subject, body);
        }

        feedbackRepository.save(feedbackRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Feedback submitted successfully.");
    }


    @PutMapping("/feedback")
    public ResponseEntity<String> updateFeedback(@RequestBody Feedback updatedFeedback) {
        String username = updatedFeedback.getUsername();
        String courseCode = updatedFeedback.getCourseCode();
        
        // Verifica se já existe um feedback para o mesmo usuário e curso
        if (feedbackRepository.existsByUsernameAndCourseCode(username, courseCode)) {
            // Obtém o feedback existente pelo username e courseCode
            Feedback existingFeedback = feedbackRepository.findByUsernameAndCourseCode(username, courseCode);
            
            // Atualiza os campos do feedback existente com os valores fornecidos no feedback atualizado
            existingFeedback.setRating(updatedFeedback.getRating());
            existingFeedback.setFeedback(updatedFeedback.getFeedback());

            Course course = courseRepository.findByCode(courseCode);

            //  Gets the instructor's email
            String instructorUsername = course.getInstructor();
            Users instructor = usersRepository.findByUsername(instructorUsername);
            String instructorEmail = instructor.getEmail();

            // Send an email to the instructor if the rating is less than 6
            if (existingFeedback.getRating() < 6) {
                String subject = "Course feedback " + course.getCode();
                String body = "Your course " + course.getName() + " received feedback with a rating of " + existingFeedback.getRating()
                        + ". Reason: " + existingFeedback.getFeedback();
                EmailSender.send(instructorEmail, subject, body);
            }

            // Salva o feedback atualizado no banco de dados
            feedbackRepository.save(existingFeedback);

            return ResponseEntity.status(HttpStatus.OK).body("Feedback for user " + username + " and course " + courseCode + " updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found for user " + username + " and course " + courseCode);
        }
    }


    @GetMapping("/report")
    public ResponseEntity<String> generateReport() {
        List<Course> courses = courseRepository.findAll();

        StringBuilder report = new StringBuilder();
        report.append("Net Promoter Score (NPS) Report:\n");

        for (Course course : courses) {
            if (registrationRepository.countByCourseCode(course.getCode()) >= 4) {
                int nps = calculateNPS(course.getCode());
                report.append("Course: ").append(course.getCode()).append(", Instructor: ").append(course.getInstructor()).append(", NPS: ").append(nps).append("\n");
            }
        }

        return ResponseEntity.ok(report.toString());
    }

    private int calculateNPS(String courseCode) {
        List<Feedback> feedbacks = feedbackRepository.findByCourseCode(courseCode);
        int promoters = 0;
        int detractors = 0;
        int neutrals = 0;

        for (Feedback feedback : feedbacks) {
            int rating = feedback.getRating();
            if (rating == 9 || rating == 10) {
                promoters++;
            } else if (rating >= 0 && rating <= 6) {
                detractors++;
            } else {
                neutrals++;
            }
        }

        int totalResponses = promoters + detractors + neutrals;
        if (totalResponses == 0) {
            return 0;
        }

        double nps = ((double) promoters / totalResponses) - ((double) detractors / totalResponses);
        return (int) Math.round(nps*100);
    }

    
}
