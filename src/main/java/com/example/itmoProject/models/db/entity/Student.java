package com.example.itmoProject.models.db.entity;

import com.example.itmoProject.models.enums.Gender;
import com.example.itmoProject.models.enums.StudentStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "students")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "nickTg")
    String nickTg;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "city")
    String city;

    @Column(name = "age")
    Integer age;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    StudentStatus status;


    @OneToMany
    @JsonManagedReference(value ="student_projects")
    List<Project> projects;
}