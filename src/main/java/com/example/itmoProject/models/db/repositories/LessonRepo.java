package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Lesson;
import com.example.itmoProject.models.db.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepo extends JpaRepository<Lesson, Long> {
    Page<Lesson> findAllByStudentId(Pageable request, Long studentId);
}
