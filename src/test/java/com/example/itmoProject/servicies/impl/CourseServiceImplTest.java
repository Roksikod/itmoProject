package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.repositories.CourseRepo;
import com.example.itmoProject.models.dto.request.CourseInfoRequest;
import com.example.itmoProject.models.dto.response.CourseInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {
    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseRepo courseRepo;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createCourse() {
        CourseInfoRequest request = new CourseInfoRequest();
        request.setTitleCourse("testTitleCourse");

        Course course = new Course();
        course.setId(1L);

        when(courseRepo.save(any(Course.class))).thenReturn(course);
        CourseInfoResponse result = courseService.createCourse(request);
        assertEquals(Long.valueOf(1L), result.getId());
    }
    @Test(expected = CustomException.class)
    public void createCourseInvalidTitle() {
        CourseInfoRequest request = new CourseInfoRequest();
        request.setTitleCourse("");
        courseService.createCourse(request);
    }

    @Test
    public void createCourseExists() {
        CourseInfoRequest request = new CourseInfoRequest();
        request.setTitleCourse("test");

        Course course = new Course();
        course.setId(1L);

        when(courseRepo.findByTitleCourse(anyString())).thenReturn(Optional.of(course));

        courseService.createCourse(request);
    }

    @Test
    public void getCourse() {
        CourseInfoRequest request = new CourseInfoRequest();
        request.setTitleCourse("test");

        Course course = new Course();
        course.setId(1L);

        when(courseRepo.findByTitleCourse(anyString())).thenReturn(Optional.of(course));

        CourseInfoResponse result =courseService.getCourse(course.getId());
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test
    public void getCourseDb() {
        CourseInfoRequest request = new CourseInfoRequest();
        request.setTitleCourse("test");

        Course course = new Course();
        course.setId(1L);

        courseService.getCourseDb(course.getId());

       // mapper.convertValue(getCourseDb(id), CourseInfoResponse.class);
    }

    @Test
    public void updateCourse() {
        CourseInfoRequest request = new CourseInfoRequest();
        request.setTitleCourse("TestTitleCourse");

        Course course = new Course();
        course.setId(1L);
        course.setTitleCourse("Test");

        when(courseRepo.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepo.save(any(Course.class))).thenReturn(course);

        CourseInfoResponse result = courseService.updateCourse(course.getId(), request);
        assertEquals(course.getTitleCourse(), result.getTitleCourse());
    }

    @Test
    public void deleteCourse() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepo.findById(course.getId())).thenReturn(Optional.of(course));
        courseService.deleteCourse(course.getId());
        verify(courseRepo, times(1)).save(any(Course.class));
        assertEquals(Status.DELETED, course.getStatus());
    }

    @Test
    public void getAllCourses() {
    }

    @Test
    public void updateGroupsList() {
    }
}