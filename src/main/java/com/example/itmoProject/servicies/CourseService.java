package com.example.itmoProject.servicies;

import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.dto.request.CourseInfoRequest;
import com.example.itmoProject.models.dto.response.CourseInfoResponse;

import java.util.List;

public interface CourseService {
    CourseInfoResponse createCourse(CourseInfoRequest request);

    CourseInfoResponse getCourse(Long id);

    Course getCourseDb(Long id);

    CourseInfoResponse updateCourse(Long id, CourseInfoRequest request);

    void deleteCourse(Long id);

    List<CourseInfoResponse> getAllCourses();

    Course updateGroupsList(Course course);
}
