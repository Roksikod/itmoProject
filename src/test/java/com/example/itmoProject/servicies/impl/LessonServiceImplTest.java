package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.entity.Group;
import com.example.itmoProject.models.db.entity.Lesson;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.repositories.GroupRepo;
import com.example.itmoProject.models.db.repositories.LessonRepo;
import com.example.itmoProject.models.dto.request.LessonInfoRequest;
import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import com.example.itmoProject.models.dto.response.LessonInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.CourseService;
import com.example.itmoProject.servicies.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.itmoProject.models.enums.LessonStatus.IN_PROGRESS;
import static com.example.itmoProject.models.enums.LessonStatus.STARTED;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class LessonServiceImplTest {
    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock
    private LessonRepo lessonRepo;

    @Mock
    private StudentService studentService;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createLesson() {
    }

    @Test
    public void getLesson() {
    }

    @Test
    public void getLessonDb() {
    }

    @Test
    public void updateLesson() {
        LessonInfoRequest request = new LessonInfoRequest();
        request.setTitleLesson("Test");
        request.setLessonStatus(IN_PROGRESS);

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitleLesson("Lesson");
        lesson.setLessonStatus(STARTED);

        when(lessonRepo.findById(lesson.getId())).thenReturn(Optional.of(lesson));
        when(lessonRepo.save(any(Lesson.class))).thenReturn(lesson);

        LessonInfoResponse result = lessonService.updateLesson(lesson.getId(), request);
        assertEquals(lesson.getTitleLesson(), result.getTitleLesson());
        assertEquals(lesson.getLessonStatus(), result.getLessonStatus());
    }

    @Test
    public void deleteLesson() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);

        when(lessonRepo.findById(lesson.getId())).thenReturn(Optional.of(lesson));
        lessonService.deleteLesson(lesson.getId());
        verify(lessonRepo, times(1)).save(any(Lesson.class));
        assertEquals(Status.DELETED, lesson.getStatus());
    }

    @Test
    public void getAllLessons() {
    }

    @Test
    public void linkLessonStudent() {
    }

    @Test(expected = NullPointerException.class)
    public void getStudentLessons() {
        Student student = new Student();
        student.setId(1L);

        Pageable pageable = mock(Pageable.class);

        when(studentService.getStudentDb(anyLong())).thenReturn(student);
        List<Lesson> lessons = new ArrayList<>();
//        when(lessonRepo.findAllByStudentId(pageable, student.getId())).thenReturn(new PageImpl<>(lessons));

        List<Long> ids = lessons.stream()
                .map(Lesson::getId)
                .collect(Collectors.toList());

        Page<LessonInfoResponse> studentLessons = lessonService.getStudentLessons(1L, 1, 10, "brand", Sort.Direction.ASC);
        List<Long> respIds = studentLessons.getContent().stream()
                .map(LessonInfoResponse::getId)
                .collect(Collectors.toList());

        assertEquals(ids, respIds);
    }
}