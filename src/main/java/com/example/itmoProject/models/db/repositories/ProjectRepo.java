package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    Page<Project> findAllByStudentId(Pageable request, Long studentId);
}
