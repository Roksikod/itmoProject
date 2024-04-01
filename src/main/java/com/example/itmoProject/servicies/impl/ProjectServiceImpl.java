package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Project;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.repositories.ProjectRepo;
import com.example.itmoProject.models.dto.request.ProjectInfoRequest;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.ProjectService;
import com.example.itmoProject.servicies.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import utils.PaginationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepo projectRepo;
    private final StudentService studentService;
    private final ObjectMapper mapper;

    public static final String ERR_MSG = "Project not found";

    @Override
    public ProjectInfoResponse createProject(ProjectInfoRequest request) {

        Project project = mapper.convertValue(request, Project.class);
        project.setStatus(Status.CREATED);
        project.setCreatedAt(LocalDateTime.now());
        project = projectRepo.save(project);

        return mapper.convertValue(project, ProjectInfoResponse.class);
    }

    @Override
    public ProjectInfoResponse getProject(Long id) {
        return mapper.convertValue(getProjectDb(id), ProjectInfoResponse.class);
    }

    public Project getProjectDb(Long id) {
        return projectRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public ProjectInfoResponse updateProject(Long id, ProjectInfoResponse request) {
        Project project = getProjectDb(id);

        project.setTitleProject(request.getTitleProject() == null ? project.getTitleProject() : request.getTitleProject());
        project.setLevel(request.getLevel() == null ? project.getLevel() : request.getLevel());

        project.setIsApproved(request.getIsApproved() == null ? project.getIsApproved() : request.getIsApproved());
        project.setTopic(request.getTopic() == null ? project.getTopic() : request.getTopic());
        project.setStatus(request.getStatus() == null ? project.getStatus() : request.getStatus());

        project.setUpdatedAt(LocalDateTime.now());
        project = projectRepo.save(project);

        return mapper.convertValue(project, ProjectInfoResponse.class);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = getProjectDb(id);
        project.setStatus(Status.DELETED);
        project.setUpdatedAt(LocalDateTime.now());
        projectRepo.save(project);
    }

    @Override
    public Page<ProjectInfoResponse> getAllProjects(Integer page, Integer perPage, String sort, Sort.Direction order) {

        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);

        List<ProjectInfoResponse> projects = projectRepo.findAll(request)
                .getContent()
                .stream()
                .map(project -> mapper.convertValue(project, ProjectInfoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(projects);
    }

    @Override
    public ProjectInfoResponse linkProjectStudent(Long studentId, Long projectId) {

        Project project = getProjectDb(projectId);
        Student student = studentService.getStudentDb(studentId);

        student.getProjects().add(project);
        studentService.updateStudentProjectsList(student);

        project.setStudent(student);
        project = projectRepo.save(project);

        StudentInfoResponse studentInfoResponse = mapper.convertValue(student, StudentInfoResponse.class);
        ProjectInfoResponse projectInfoResponse = mapper.convertValue(project, ProjectInfoResponse.class);

        projectInfoResponse.setStudent(studentInfoResponse);
        return projectInfoResponse;
    }
    @Override
    public Page<ProjectInfoResponse> getStudentProjects(Long studentId, Integer page, Integer perPage, String sort, Sort.Direction order) {
        studentService.getStudentDb(studentId);
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Page<Project> allByStudentId = projectRepo.findAllByStudentId(request, studentId);

        return new PageImpl<>(allByStudentId.stream()
                .map(p -> mapper.convertValue(p, ProjectInfoResponse.class))
                .collect(Collectors.toList()));
    }
}