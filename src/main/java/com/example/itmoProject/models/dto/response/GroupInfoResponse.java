package com.example.itmoProject.models.dto.response;

import com.example.itmoProject.models.dto.request.GroupInfoRequest;
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
public class GroupInfoResponse extends GroupInfoRequest {
    Long id;
    CourseInfoResponse course;
}
