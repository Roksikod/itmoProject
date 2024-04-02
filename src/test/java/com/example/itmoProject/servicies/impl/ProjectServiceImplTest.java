package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.models.db.entity.Lesson;
import com.example.itmoProject.models.db.entity.Project;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.repositories.ProjectRepo;
import com.example.itmoProject.models.dto.request.ProjectInfoRequest;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.models.enums.Topic;
import com.example.itmoProject.servicies.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private StudentService studentService;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createProject() {
    }

    @Test
    public void getProject() {
    }

    @Test
    public void getProjectDb() {
    }

    @Test
    public void updateProject() {
        ProjectInfoRequest request = new ProjectInfoRequest();
        request.setTitleProject("test");

        Project project = new Project();
        project.setId(1L);
        project.setTitleProject("TestTest");

        when(projectRepo.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectRepo.save(any(Project.class))).thenReturn(project);

        ProjectInfoResponse result = projectService.updateProject(project.getId(), request);
        assertEquals(project.getTitleProject(), result.getTitleProject());
    }

    @Test
    public void deleteProject() {
        Project project = new Project();
        project.setId(1L);

        when(projectRepo.findById(project.getId())).thenReturn(Optional.of(project));
        projectService.deleteProject(project.getId());
        verify(projectRepo, times(1)).save(any(Project.class));
        assertEquals(Status.DELETED, project.getStatus());
    }


    @Test
    public void getAllProjects() {
    }

    @Test
    public void linkProjectStudent() {
    }

    @Test(expected = NullPointerException.class)
    public void getStudentProjects() {
        Student student = new Student();
        student.setId(1L);

        Pageable pageable = mock(Pageable.class);

        when(studentService.getStudentDb(anyLong())).thenReturn(student);
        List<Project> projects = new ArrayList<>();
//        when(projectRepo.findAllByStudentId(pageable, student.getId())).thenReturn(new PageImpl<>(projects));

        List<Long> ids = projects.stream()
                .map(Project::getId)
                .collect(Collectors.toList());

        Page<ProjectInfoResponse> title = projectService.getStudentProjects(1L, 1, 10, "titleProject", Sort.Direction.ASC);
        List<Long> respIds = title.getContent().stream()
                .map(ProjectInfoResponse::getId)
                .collect(Collectors.toList());

        assertEquals(ids, respIds);
    }
}