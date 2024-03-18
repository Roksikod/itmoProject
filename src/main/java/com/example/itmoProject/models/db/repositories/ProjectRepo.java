package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {
}
