package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Student;
import com.example.itmoProject.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    // Student findStudentByEmailAndStatus(String email, StudentStatus status); способ запроса
    Optional<Student> findByEmail(String email);

    List<Student> findAllByStatus(Status status);

    //нативный SQL
    @Query(nativeQuery = true, value = "select * from students where first_name = :firstName order by first_name desc limit 1")
    Student findFirstName(@Param("firstName") String firstName);

    @Query("select s from Student s where s.firstName = :firstName")
    List<Student> findAllFirstName(@Param("firstName") String firstName);

    Page<Student> findAllByTutorId(Pageable request, Long tutorId);
}