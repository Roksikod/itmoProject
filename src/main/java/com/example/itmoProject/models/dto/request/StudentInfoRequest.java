package com.example.itmoProject.models.dto.request;

import com.example.itmoProject.models.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentInfoRequest {
    String email;
    String nickTg;
    String firstName;
    String lastName;
    String city;
    Integer age;
    Gender gender;
}
