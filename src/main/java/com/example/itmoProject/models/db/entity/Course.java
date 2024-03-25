package com.example.itmoProject.models.db.entity;

import com.example.itmoProject.models.enums.Gender;
import com.example.itmoProject.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "courses")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title_course")
    String titleCourse;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Status status;
}

