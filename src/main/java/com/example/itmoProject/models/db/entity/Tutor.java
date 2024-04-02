package com.example.itmoProject.models.db.entity;

import com.example.itmoProject.models.enums.Gender;
import com.example.itmoProject.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany
    @JsonManagedReference(value = "tutor_students")
    List<Student> students;

    @OneToMany
    @JsonManagedReference(value = "tutor_groups")
    List<Group> groups;
}
