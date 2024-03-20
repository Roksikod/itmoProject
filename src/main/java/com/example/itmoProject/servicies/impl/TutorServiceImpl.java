package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Tutor;
import com.example.itmoProject.models.db.repositories.TutorRepo;
import com.example.itmoProject.models.dto.request.TutorInfoRequest;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.models.dto.response.TutorInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.TutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
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
public class TutorServiceImpl implements TutorService {

    private final TutorRepo tutorRepo;
    private final ObjectMapper mapper;

    public static final String ERR_MSG = "Tutor not found";


    @Override
    public TutorInfoResponse createTutor(TutorInfoRequest request) {
        String email = request.getEmail();

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new CustomException("Invalid email", HttpStatus.BAD_REQUEST);
        }

        tutorRepo.findByEmail(email)
                .ifPresent(tutor -> {
                    throw new CustomException("Email already exists", HttpStatus.CONFLICT);
                });

        Tutor tutor = mapper.convertValue(request, Tutor.class);
        tutor.setStatus(Status.CREATED);
        tutor.setCreatedAt(LocalDateTime.now());
        tutor = tutorRepo.save(tutor);

        return mapper.convertValue(tutor, TutorInfoResponse.class);
    }

    @Override
    public TutorInfoResponse getTutor(Long id) {
        return mapper.convertValue(getTutorDb(id), TutorInfoResponse.class);
    }


    @Override
    public Tutor getTutorDb(Long id) {
        return tutorRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public TutorInfoResponse updateTutor(Long id, TutorInfoRequest request) {
        Tutor tutor = getTutorDb(id);
        tutor.setEmail(request.getEmail() == null ? tutor.getEmail() : request.getEmail());
        tutor.setNickTg(request.getNickTg() == null ? tutor.getNickTg() : request.getNickTg());
        tutor.setFirstName(request.getFirstName() == null ? tutor.getFirstName() : request.getFirstName());
        tutor.setLastName(request.getLastName() == null ? tutor.getLastName() : request.getLastName());
        tutor.setCity(request.getCity() == null ? tutor.getCity() : request.getCity());
        tutor.setAge(request.getAge() == null ? tutor.getAge() : request.getAge());
        tutor.setGender(request.getGender() == null ? tutor.getGender() : request.getGender());


        tutor.setUpdatedAt(LocalDateTime.now());
        tutor = tutorRepo.save(tutor);

        return mapper.convertValue(tutor, TutorInfoResponse.class);
    }

    @Override
    public void deleteTutor(Long id) {
        Tutor tutor = getTutorDb(id);
        tutor.setStatus(Status.DELETED);
        tutor.setUpdatedAt(LocalDateTime.now());
        tutorRepo.save(tutor);
    }

    @Override
    public Page<TutorInfoResponse> getAllTutors(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);

        List<TutorInfoResponse> all = tutorRepo.findAll(request)
                .getContent()
                .stream()
                .map(tutor -> mapper.convertValue(tutor, TutorInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(all);
    }

    @Override
    public Tutor updateTutorStudentsList(Tutor tutor) {
        return tutorRepo.save(tutor);
    }


    @Override
    public List<StudentInfoResponse> getTutorStudentsList(Long tutorId) {
        Tutor tutor = getTutorDb(tutorId);
        List<StudentInfoResponse> students = tutor.getStudents()
                .stream()
                .map(student -> mapper.convertValue(student, StudentInfoResponse.class))
                .collect(Collectors.toList());

        return students;
    }
}

