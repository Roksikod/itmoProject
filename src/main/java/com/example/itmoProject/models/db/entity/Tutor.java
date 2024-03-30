package com.example.itmoProject.models.db.entity;

import com.example.itmoProject.models.enums.Gender;
import com.example.itmoProject.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tutors")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "nickTg")
    String nickTg;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "city")
    String city;

    @Column(name = "age")
    Integer age;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany
    @JsonManagedReference(value = "tutor_students")
    List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "tutors_groups",
            joinColumns = { @JoinColumn(name = "group_id") },
            inverseJoinColumns = { @JoinColumn(name = "tutor_id") }
    )
    private Set<Tutor> groups = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "tutors_groups",
            joinColumns = { @JoinColumn(name = "tutor_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<Tutor> tutors = new HashSet<>();

}
