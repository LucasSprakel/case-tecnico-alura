package com.lucassprakel.alura.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity(name = "registration")
@Table(name = "registration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "course_code", nullable = false, length = 10)
    private String courseCode;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

}