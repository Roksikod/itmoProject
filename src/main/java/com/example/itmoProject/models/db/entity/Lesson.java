package com.example.itmoProject.models.db.entity;

import com.example.itmoProject.models.enums.LessonStatus;
import com.example.itmoProject.models.enums.Level;
import com.example.itmoProject.models.enums.Status;
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
@Table(name = "lessons")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title_lesson")
    String titleLesson;

    @Column(name = "number_lesson")
    Integer numberLesson;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    Level level;

    @Column(name = "is_opened")
    Boolean isOpened;

    @Column(name = "topic")
    @Enumerated(EnumType.STRING)
    Topic topic;

    @Column(name = "lesson_status")
    @Enumerated(EnumType.STRING)
    LessonStatus lessonStatus;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @ManyToOne
    @JsonBackReference(value = "student_lessons")
    Student student;
}

