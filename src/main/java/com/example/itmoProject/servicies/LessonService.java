package com.example.itmoProject.servicies;

import com.example.itmoProject.models.dto.request.LessonInfoRequest;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface LessonService {
    LessonInfoResponse createLesson(LessonInfoRequest request);

    LessonInfoResponse getLesson(Long id);

    LessonInfoResponse updateLesson(Long id, LessonInfoResponse request);

    Page<LessonInfoResponse> getAllLessons(Integer page, Integer perPage, String sort, Sort.Direction order);

    LessonInfoResponse linkLessonStudent(Long studentId, Long lessonId);

    void deleteLesson(Long id);
}
