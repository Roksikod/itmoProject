package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, Long> {
}
