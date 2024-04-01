package com.example.itmoProject.controllers;

import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.servicies.LessonService;
import com.example.itmoProject.servicies.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping
    @Operation(summary = "Создание урока")
    public LessonInfoResponse createLesson(@RequestBody @Valid LessonInfoResponse request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение урока")
    public LessonInfoResponse getLesson(@PathVariable Long id) {
        return lessonService.getLesson(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование урока")
    public LessonInfoResponse updateLesson(@PathVariable Long id, @RequestBody @Valid LessonInfoResponse request) {
        return lessonService.updateLesson(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление урока")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех уроков")
    public Page<LessonInfoResponse> getAllLessons(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer perPage,
                                                  @RequestParam(defaultValue = "titleLesson") String sort,
                                                  @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                                  @RequestParam(required = false) String filter) {

        return lessonService.getAllLessons(page, perPage, sort, order);
    }

    @PostMapping("/linkLessonStudent/{lessonId}/{studentId}")
    @Operation(summary = "Назначение урока студенту")
    public LessonInfoResponse linkLessonStudent(@PathVariable Long studentId, @PathVariable Long lessonId) {
        return lessonService.linkLessonStudent(studentId, lessonId);
    }

    @GetMapping("/studentLessons/{studentId}")
    @Operation(summary = "Получение списка уроков студента")
    public Page<LessonInfoResponse> getStudentLessons(@PathVariable Long studentId,
                                                      @RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer perPage,
                                                      @RequestParam(defaultValue = "titleLesson") String sort,
                                                      @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return lessonService.getStudentLessons(studentId, page, perPage, sort, order);
    }

}

