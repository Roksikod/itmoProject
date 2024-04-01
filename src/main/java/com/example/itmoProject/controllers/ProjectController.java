package com.example.itmoProject.controllers;

import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.servicies.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Создание проекта")
    public ProjectInfoResponse createProject(@RequestBody @Valid ProjectInfoResponse request) {
        return projectService.createProject(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение проекта")
    public ProjectInfoResponse getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование проекта")
    public ProjectInfoResponse updateProject(@PathVariable Long id, @RequestBody @Valid ProjectInfoResponse request) {
        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление проекта")
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех проектов")
    public Page<ProjectInfoResponse> getAllProjects(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer perPage,
                                                    @RequestParam(defaultValue = "titleProject") String sort,
                                                    @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                                    @RequestParam(required = false) String filter) {

        return projectService.getAllProjects(page, perPage, sort, order);
    }

    @PostMapping("/linkProjectStudent/{studentId}/{projectId}")
    @Operation(summary = "Назначение проекта студенту")
    public ProjectInfoResponse linkProjectStudent(@PathVariable Long studentId, @PathVariable Long projectId) {
        return projectService.linkProjectStudent(studentId, projectId);
    }
    @GetMapping("/studentProjects/{studentId}")
    @Operation(summary = "Получение списка проектов студента")
    public Page<ProjectInfoResponse> getStudentProjects(@PathVariable Long studentId,
                                                        @RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer perPage,
                                                        @RequestParam(defaultValue = "titleProject") String sort,
                                                        @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return projectService.getStudentProjects(studentId, page, perPage, sort, order);
    }

}
