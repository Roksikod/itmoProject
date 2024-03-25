package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.repositories.CourseRepo;
import com.example.itmoProject.models.dto.request.CourseInfoRequest;
import com.example.itmoProject.models.dto.response.CourseInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final ObjectMapper mapper;

    public static final String ERR_MSG = "Course not found";


    @Override
    public CourseInfoResponse createCourse(CourseInfoRequest request) {
        String titleCourse = request.getTitleCourse();

        if (titleCourse.isEmpty()) {
            throw new CustomException("Empty title of course", HttpStatus.BAD_REQUEST);
        }

        Course course = mapper.convertValue(request, Course.class);
        course.setStatus(Status.CREATED);
        course.setCreatedAt(LocalDateTime.now());
        course = courseRepo.save(course);

        return mapper.convertValue(course, CourseInfoResponse.class);
    }

    @Override
    public CourseInfoResponse getCourse(Long id) {
        return mapper.convertValue(getCourseDb(id), CourseInfoResponse.class);
    }


    @Override
    public Course getCourseDb(Long id) {
        return courseRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public CourseInfoResponse updateCourse(Long id, CourseInfoRequest request) {
        Course course = getCourseDb(id);
        course.setTitleCourse(request.getTitleCourse() == null ? course.getTitleCourse() : request.getTitleCourse());


        course.setUpdatedAt(LocalDateTime.now());
        course = courseRepo.save(course);

        return mapper.convertValue(course, CourseInfoResponse.class);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourseDb(id);
        course.setStatus(Status.DELETED);
        course.setUpdatedAt(LocalDateTime.now());
        courseRepo.save(course);
    }

    @Override
    public List<CourseInfoResponse> getAllCourses() {
        return courseRepo.findAll()
                .stream()
                .map(course -> mapper.convertValue(course, CourseInfoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Course updateGroupsList(Course course) {
        return courseRepo.save(course);
    }
}
