package com.example.itmoProject.servicies;

import com.example.itmoProject.models.dto.request.LessonInfoRequest;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface LessonService {
    LessonInfoResponse createLesson(LessonInfoRequest request);

    LessonInfoResponse getLesson(Long id);

    LessonInfoResponse updateLesson(Long id, LessonInfoRequest request);

    Page<LessonInfoResponse> getAllLessons(Integer page, Integer perPage, String sort, Sort.Direction order);

    LessonInfoResponse linkLessonStudent(Long studentId, Long lessonId);

    void deleteLesson(Long id);

    Page<LessonInfoResponse> getStudentLessons(Long studentId, Integer page, Integer perPage, String sort, Sort.Direction order);
}
