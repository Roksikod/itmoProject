package com.example.itmoProject.servicies;

import com.example.itmoProject.models.dto.request.ProjectInfoRequest;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ProjectService {
    ProjectInfoResponse createProject(ProjectInfoRequest request);

    ProjectInfoResponse getProject(Long id);

    ProjectInfoResponse updateProject(Long id, ProjectInfoResponse request);

    void deleteProject(Long id);

    Page<ProjectInfoResponse> getAllProjects(Integer page, Integer perPage, String sort, Sort.Direction order);
    ProjectInfoResponse linkProjectStudent(Long studentId, Long projectId);

}
