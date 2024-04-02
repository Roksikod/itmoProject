package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Lesson;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.repositories.LessonRepo;
import com.example.itmoProject.models.dto.request.LessonInfoRequest;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.LessonService;
import com.example.itmoProject.servicies.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import utils.PaginationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepo lessonRepo;
    private final StudentService studentService;
    private final ObjectMapper mapper;

    public static final String ERR_MSG = "Lesson not found";

    @Override
    public LessonInfoResponse createLesson(LessonInfoRequest request) {

        Lesson lesson = mapper.convertValue(request, Lesson.class);
        lesson.setStatus(Status.CREATED);
        lesson.setCreatedAt(LocalDateTime.now());
        lesson = lessonRepo.save(lesson);

        return mapper.convertValue(lesson, LessonInfoResponse.class);
    }

    @Override
    public LessonInfoResponse getLesson(Long id) {
        return mapper.convertValue(getLessonDb(id), LessonInfoResponse.class);
    }

    public Lesson getLessonDb(Long id) {
        return lessonRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public LessonInfoResponse updateLesson(Long id, LessonInfoRequest request) {
        Lesson lesson = getLessonDb(id);

        lesson.setTitleLesson(request.getTitleLesson() == null ? lesson.getTitleLesson() : request.getTitleLesson());
        lesson.setLevel(request.getLevel() == null ? lesson.getLevel() : request.getLevel());

        lesson.setIsOpened(request.getIsOpened() == null ? lesson.getIsOpened() : request.getIsOpened());
        lesson.setTopic(request.getTopic() == null ? lesson.getTopic() : request.getTopic());
        lesson.setLessonStatus(request.getLessonStatus() == null ? lesson.getLessonStatus() : request.getLessonStatus());
        lesson.setStatus(request.getStatus() == null ? lesson.getStatus() : request.getStatus());

        lesson.setUpdatedAt(LocalDateTime.now());
        lesson = lessonRepo.save(lesson);

        return mapper.convertValue(lesson, LessonInfoResponse.class);
    }

    @Override
    public void deleteLesson(Long id) {
        Lesson lesson = getLessonDb(id);
        lesson.setStatus(Status.DELETED);
        lesson.setUpdatedAt(LocalDateTime.now());
        lessonRepo.save(lesson);
    }

    @Override
    public Page<LessonInfoResponse> getAllLessons(Integer page, Integer perPage, String sort, Sort.Direction order) {

        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);

        List<LessonInfoResponse> lessons = lessonRepo.findAll(request)
                .getContent()
                .stream()
                .map(lesson -> mapper.convertValue(lesson, LessonInfoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(lessons);
    }

    @Override
    public LessonInfoResponse linkLessonStudent(Long studentId, Long lessonId) {

        Lesson lesson = getLessonDb(lessonId);
        Student student = studentService.getStudentDb(studentId);

        student.getLessons().add(lesson);
        studentService.updateStudentLessonsList(student);

        lesson.setStudent(student);
        lesson = lessonRepo.save(lesson);

        StudentInfoResponse studentInfoResponse = mapper.convertValue(student, StudentInfoResponse.class);
        LessonInfoResponse lessonInfoResponse = mapper.convertValue(lesson, LessonInfoResponse.class);

        lessonInfoResponse.setStudent(studentInfoResponse);
        return lessonInfoResponse;
    }

    @Override
    public Page<LessonInfoResponse> getStudentLessons(Long studentId, Integer page, Integer perPage, String sort, Sort.Direction order) {
        studentService.getStudentDb(studentId);
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Page<Lesson> allByStudentId = lessonRepo.findAllByStudentId(request, studentId);

        return new PageImpl<>(allByStudentId.stream()
                .map(l -> mapper.convertValue(l, LessonInfoResponse.class))
                .collect(Collectors.toList()));
    }

}

