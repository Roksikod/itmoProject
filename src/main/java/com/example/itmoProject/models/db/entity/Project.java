package com.example.itmoProject.models.db.entity;

import com.example.itmoProject.models.enums.Level;
import com.example.itmoProject.models.enums.ProjectStatus;
import com.example.itmoProject.models.enums.Topic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "projects")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title_project")
    String titleProject;

    @Column(name = "number_lesson")
    Integer numberLesson;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    Level level;

    @Column(name = "is_approved")
    Boolean isApproved;

    @Column(name = "topic")
    @Enumerated(EnumType.STRING)
    Topic topic;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ProjectStatus status;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToOne
    @JsonBackReference(value = "student_projects")
    Student student;
}
