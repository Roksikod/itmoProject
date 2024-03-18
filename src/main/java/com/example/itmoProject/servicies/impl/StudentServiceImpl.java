package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.repositories.StudentRepo;
import com.example.itmoProject.models.dto.request.StudentInfoRequest;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.servicies.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import utils.PaginationUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final ObjectMapper mapper;

    public static final String ERR_MSG = "Student not found";


    private List<StudentInfoResponse> students = new ArrayList<>();
    private long id = 1;

    @Override
    public StudentInfoResponse createStudent(StudentInfoRequest request) {
        StudentInfoResponse student = mapper.convertValue(request, StudentInfoResponse.class);
        student.setId(id++);
        students.add(student);
        return student;
    }

    @Override
    public StudentInfoResponse getStudent(Long id) {
        List<StudentInfoResponse> all = this.students.stream()
                .filter(s -> s.getId().equals(id))
                .collect(Collectors.toList());

        StudentInfoResponse student = null;
        if (CollectionUtils.isEmpty(all)) {
            log.error(String.format("Student with id:%s not found", id));
            return student;
        }
        student = all.get(0);
        return student;
    }

    @Override
    public Student getStudentDb(Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public StudentInfoResponse updateStudent(Long id, StudentInfoRequest request) {
        StudentInfoResponse student = getStudent(id);
        if (Objects.isNull(student)) {
            log.error("Students not exists");
            return null;
        }
        StudentInfoResponse response = mapper.convertValue(request, StudentInfoResponse.class);
        response.setId(student.getId());
        return response;
    }


    @Override
    public void deleteStudent(Long id) {
        StudentInfoResponse student = getStudent(id);

        if (Objects.isNull(student)) {
            log.error("Student not deleted");
            return;
        }
        this.students.remove(student);
    }

    @Override
    public Page<StudentInfoResponse> getAllStudents (Integer page, Integer perPage, String sort, Sort.Direction order){
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);

        List<StudentInfoResponse> all = studentRepo.findAll(request)
                .getContent()
                .stream()
                .map(user -> mapper.convertValue(user, StudentInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(all);
    }

    @Override
    public Student updateStudentProjectsList(Student student) {
        return studentRepo.save(student);
    }


    @Override
    public List<ProjectInfoResponse> getStudentProjectsList(Long studentId) {
        Student student = getStudentDb(studentId);
        List<ProjectInfoResponse> projects = student.getProjects()
                .stream()
                .map(project -> mapper.convertValue(project, ProjectInfoResponse.class))
                .collect(Collectors.toList());

        return projects;
    }

}
