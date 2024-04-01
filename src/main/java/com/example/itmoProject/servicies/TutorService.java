package com.example.itmoProject.servicies;

import com.example.itmoProject.models.db.entity.Tutor;
import com.example.itmoProject.models.dto.request.TutorInfoRequest;
import com.example.itmoProject.models.dto.response.CourseInfoResponse;
import com.example.itmoProject.models.dto.response.TutorInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TutorService {
    TutorInfoResponse createTutor(TutorInfoRequest request);

    TutorInfoResponse getTutor(Long id);

    Tutor getTutorDb(Long id);

    TutorInfoResponse updateTutor(Long id, TutorInfoRequest request);

    void deleteTutor(Long id);

    Page<TutorInfoResponse> getAllTutors(Integer page, Integer perPage, String sort, Sort.Direction order);

    Tutor updateStudentsList(Tutor tutor);

}
