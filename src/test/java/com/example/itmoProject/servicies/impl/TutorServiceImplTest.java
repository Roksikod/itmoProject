package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Tutor;
import com.example.itmoProject.models.db.repositories.TutorRepo;
import com.example.itmoProject.models.dto.request.TutorInfoRequest;
import com.example.itmoProject.models.dto.response.TutorInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorServiceImplTest {
    @InjectMocks
    private TutorServiceImpl tutorService;

    @Mock
    private TutorRepo tutorRepo;

    @Spy
    private ObjectMapper mapper;
    @Test
    public void createTutor() {
        TutorInfoRequest request = new TutorInfoRequest();
        request.setEmail("test@test.com");

        Tutor tutor = new Tutor();
        tutor.setId(1L);

        when(tutorRepo.save(any(Tutor.class))).thenReturn(tutor);
        TutorInfoResponse result = tutorService.createTutor(request);
        assertEquals(Long.valueOf(1L), result.getId());
    }
    @Test(expected = CustomException.class)
    public void createTutorInvalidEmail() {
        TutorInfoRequest request = new TutorInfoRequest();
        tutorService.createTutor(request);
    }

    @Test(expected = CustomException.class)
    public void createTutorExists() {
        TutorInfoRequest request = new TutorInfoRequest();
        request.setEmail("test@test.com");

        Tutor tutor = new Tutor();
        tutor.setId(1L);

        when(tutorRepo.findByEmail(anyString())).thenReturn(Optional.of(tutor));

        tutorService.createTutor(request);
    }

    @Test
    public void getTutor() {
    }

    @Test
    public void getTutorDb() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTutor() {
        TutorInfoRequest request = new TutorInfoRequest();
        request.setAge(30);

        Tutor tutor = new Tutor();
        tutor.setId(1L);
        tutor.setAge(35);
        tutor.setFirstName("Ivan");

        when(tutorRepo.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        when(tutorRepo.save(any(Tutor.class))).thenReturn(tutor);

        TutorInfoResponse result = tutorService.updateTutor(tutor.getId(), request);
        assertEquals(tutor.getFirstName(), result.getFirstName());
        assertEquals(request.getAge(), result.getAge());
    }

    @Test
    public void deleteTutor() {
        Tutor tutor = new Tutor();
        tutor.setId(1L);

        when(tutorRepo.findById(tutor.getId())).thenReturn(Optional.of(tutor));
        tutorService.deleteTutor(tutor.getId());
        verify(tutorRepo, times(1)).save(any(Tutor.class));
        assertEquals(Status.DELETED, tutor.getStatus());

    }

    @Test
    public void getAllTutors() {
    }

    @Test
    public void updateStudentsList() {
    }
}