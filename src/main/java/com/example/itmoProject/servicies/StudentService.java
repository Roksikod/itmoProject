package com.example.itmoProject.servicies;

import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.dto.request.StudentInfoRequest;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StudentService {
    StudentInfoResponse createStudent(StudentInfoRequest request);

    StudentInfoResponse getStudent(Long id);

    Student getStudentDb(Long id);

    StudentInfoResponse updateStudent(Long id, StudentInfoRequest request);

    void deleteStudent(Long id);

    Page<StudentInfoResponse> getAllStudents (Integer page, Integer perPage, String sort, Sort.Direction order);

    Student updateStudentProjectsList(Student student);

    List<ProjectInfoResponse> getStudentProjectsList(Long studentId);

    Student updateStudentLessonsList(Student student);

    List<LessonInfoResponse> getStudentLessonsList(Long studentId);
}

