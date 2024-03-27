package com.lucassprakel.alura.models;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "course")
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String code;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "inactivation_date", nullable = true)
    private LocalDateTime inactivationDate;

    
}