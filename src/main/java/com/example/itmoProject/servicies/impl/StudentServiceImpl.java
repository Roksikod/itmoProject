package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.repositories.StudentRepo;
import com.example.itmoProject.models.dto.request.StudentInfoRequest;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import utils.PaginationUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final ObjectMapper mapper;

    public static final String ERR_MSG = "Student not found";


    @Override
    public StudentInfoResponse createStudent(StudentInfoRequest request) {
        String email = request.getEmail();

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new CustomException("Invalid email", HttpStatus.BAD_REQUEST);
        }

        studentRepo.findByEmail(email)
                .ifPresent(tutor -> {
                    throw new CustomException("Email already exists", HttpStatus.CONFLICT);
                });

        Student student = mapper.convertValue(request, Student.class);
        student.setStatus(Status.CREATED);
        student.setCreatedAt(LocalDateTime.now());
        student = studentRepo.save(student);

        return mapper.convertValue(student, StudentInfoResponse.class);
    }

    @Override
    public StudentInfoResponse getStudent(Long id) {
        return mapper.convertValue(getStudentDb(id), StudentInfoResponse.class);
    }


    @Override
    public Student getStudentDb(Long id) {
        return studentRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public StudentInfoResponse updateStudent(Long id, StudentInfoRequest request) {
        Student student = getStudentDb(id);
        student.setEmail(request.getEmail() == null ? student.getEmail() : request.getEmail());
        student.setNickTg(request.getNickTg() == null ? student.getNickTg() : request.getNickTg());
        student.setFirstName(request.getFirstName() == null ? student.getFirstName() : request.getFirstName());
        student.setLastName(request.getLastName() == null ? student.getLastName() : request.getLastName());
        student.setCity(request.getCity() == null ? student.getCity() : request.getCity());
        student.setAge(request.getAge() == null ? student.getAge() : request.getAge());
        student.setGender(request.getGender() == null ? student.getGender() : request.getGender());


        student.setUpdatedAt(LocalDateTime.now());
        student = studentRepo.save(student);

        return mapper.convertValue(student, StudentInfoResponse.class);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentDb(id);
        student.setStatus(Status.DELETED);
        student.setUpdatedAt(LocalDateTime.now());
        studentRepo.save(student);
    }

    @Override
    public Page<StudentInfoResponse> getAllStudents(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);

        List<StudentInfoResponse> all = studentRepo.findAll(request)
                .getContent()
                .stream()
                .map(student -> mapper.convertValue(student, StudentInfoResponse.class))
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
    @Override
    public Student updateStudentLessonsList(Student student) {
        return studentRepo.save(student);
    }


    @Override
    public List<LessonInfoResponse> getStudentLessonsList(Long studentId) {
        Student student = getStudentDb(studentId);
        List<LessonInfoResponse> lessons = student.getLessons()
                .stream()
                .map(lesson -> mapper.convertValue(lesson, LessonInfoResponse.class))
                .collect(Collectors.toList());

        return lessons;
    }

}
