package com.example.itmoProject.controllers;

import com.example.itmoProject.models.dto.response.CourseInfoResponse;
import com.example.itmoProject.servicies.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

        private final CourseService courseService;

        @PostMapping
        @Operation(summary = "Создание курса")
        public CourseInfoResponse createCourse(@RequestBody @Valid CourseInfoResponse request) {
            return courseService.createCourse(request);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Получение курса")
        public CourseInfoResponse getCourse(@PathVariable Long id) {
            return courseService.getCourse(id);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Редактирование курса")
        public CourseInfoResponse updateCourse(@PathVariable Long id, @RequestBody @Valid CourseInfoResponse request) {
            return courseService.updateCourse(id, request);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Удаление курса")
        public void deleteCourse(@PathVariable Long id) {
            courseService.deleteCourse(id);
        }

        @GetMapping("/all")
        @Operation(summary = "Получение всех курсов")
        public List<CourseInfoResponse> getAllCourses() {
            return courseService.getAllCourses();
        }
}
