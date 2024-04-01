package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.db.entity.Tutor;
import com.example.itmoProject.models.db.repositories.StudentRepo;
import com.example.itmoProject.models.dto.request.StudentInfoRequest;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.TutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepo studentRepo;
    @Mock
    private TutorService tutorService;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createStudent() {
        StudentInfoRequest request = new StudentInfoRequest();
        request.setEmail("test@test.com");

        Student student = new Student();
        student.setId(1L);

        when(studentRepo.save(any(Student.class))).thenReturn(student);
        StudentInfoResponse result = studentService.createStudent(request);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test(expected = CustomException.class)
    public void createStudentInvalidEmail() {
        StudentInfoRequest request = new StudentInfoRequest();
        studentService.createStudent(request);
    }

    @Test(expected = CustomException.class)
    public void createStudentExists() {
        StudentInfoRequest request = new StudentInfoRequest();
        request.setEmail("test@test.com");

        Student student = new Student();
        student.setId(1L);

        when(studentRepo.findByEmail(anyString())).thenReturn(Optional.of(student));

        studentService.createStudent(request);
    }
    @Test
    public void getStudent() {
    }

    @Test
    public void getStudentDb() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStudent() {
        StudentInfoRequest request = new StudentInfoRequest();
        request.setAge(30);

        Student student = new Student();
        student.setId(1L);
        student.setAge(35);
        student.setFirstName("Ivan");

        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepo.save(any(Student.class))).thenReturn(student);

        StudentInfoResponse result = studentService.updateStudent(student.getId(), request);
        assertEquals(student.getFirstName(), result.getFirstName());
        assertEquals(request.getAge(), result.getAge());
    }

    @Test
    public void deleteStudent() {
        Student student = new Student();
        student.setId(1L);

        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        studentService.deleteStudent(student.getId());
        verify(studentRepo, times(1)).save(any(Student.class));
        assertEquals(Status.DELETED, student.getStatus());
    }

    @Test
    public void getAllStudents() {
    }

    @Test
    public void updateStudentProjectsList() {
    }

    @Test
    public void updateStudentLessonsList() {
    }

    @Test
    public void linkTutorStudent() {
    }

    @Test(expected = NullPointerException.class)
    public void getTutorStudents() {
        Tutor tutor = new Tutor();
        tutor.setId(1L);

        Pageable pageable = mock(Pageable.class);

        when(tutorService.getTutorDb(anyLong())).thenReturn(tutor);
        List<Student> students = new ArrayList<>();
        when(studentRepo.findAllByTutorId(pageable, tutor.getId())).thenReturn(new PageImpl<>(students));

        List<Long> ids = students.stream()
                .map(Student::getId)
                .collect(Collectors.toList());

        Page<StudentInfoResponse> name = studentService.getTutorStudents(1L, 1, 10, "firstName", Sort.Direction.ASC);
        List<Long> respIds = name.getContent().stream()
                .map(StudentInfoResponse::getId)
                .collect(Collectors.toList());

        assertEquals(ids, respIds);
    }
}