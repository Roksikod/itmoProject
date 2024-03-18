package com.example.itmoProject.controllers;

import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.servicies.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Создание студента")
    public StudentInfoResponse createStudent(@RequestBody @Valid StudentInfoResponse request) {
        return studentService.createStudent(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение студента")
    public StudentInfoResponse getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование студента")
    public StudentInfoResponse updateStudent(@PathVariable Long id, @RequestBody @Valid StudentInfoResponse request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех студентов")
    public Page<StudentInfoResponse> getAllStudents(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer perPage,
                                              @RequestParam(defaultValue = "firstName") String sort,
                                              @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return studentService.getAllStudents(page, perPage, sort, order);
    }
    @GetMapping("/studentProjectsList/{id}")
    @Operation(summary = "Получение списка проектов студента")
    public List<ProjectInfoResponse> getStudentProjectsList(@PathVariable Long id) {
        return studentService.getStudentProjectsList(id);
    }
}