package com.example.itmoProject.models.db.repositories;

import com.example.itmoProject.models.db.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepo extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findByEmail(String email);

    List<Tutor> findAllByCity(String city);

    //нативный SQL
    @Query(nativeQuery = true, value = "select * from tutors where first_name = :firstName order by first_name desc limit 1")
    Tutor findFirstName(@Param("firstName") String firstName);

    @Query("select t from Tutor t where t.firstName = :firstName")
    List<Tutor> findAllFirstName(@Param("firstName") String firstName);
}
