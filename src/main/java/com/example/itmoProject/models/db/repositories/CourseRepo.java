package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.entity.Group;
import com.example.itmoProject.models.db.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, Long> {


}
