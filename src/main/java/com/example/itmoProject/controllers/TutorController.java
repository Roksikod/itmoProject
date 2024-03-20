package com.example.itmoProject.controllers;

import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.models.dto.response.TutorInfoResponse;
import com.example.itmoProject.servicies.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tutors")
@RequiredArgsConstructor
public class TutorController {


    private final TutorService tutorService;

    @PostMapping
    @Operation(summary = "Создание куратора")
    public TutorInfoResponse createTutor(@RequestBody @Valid TutorInfoResponse request) {
        return tutorService.createTutor(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение куратора")
    public TutorInfoResponse getTutor(@PathVariable Long id) {
        return tutorService.getTutor(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование куратора")
    public TutorInfoResponse updateTutor(@PathVariable Long id, @RequestBody @Valid TutorInfoResponse request) {
        return tutorService.updateTutor(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление куратора")
    public void deleteTutor(@PathVariable Long id) {
        tutorService.deleteTutor(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех кураторов")
    public Page<TutorInfoResponse> getAllTutors(@RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer perPage,
                                                @RequestParam(defaultValue = "firstName") String sort,
                                                @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return tutorService.getAllTutors(page, perPage, sort, order);
    }

    @GetMapping("/tutorStudentsList/{id}")
    @Operation(summary = "Получение списка студентов куратора")
    public List<StudentInfoResponse> getTutorStudentsList(@PathVariable Long tutorId) {
        return tutorService.getTutorStudentsList(tutorId);
    }
}

