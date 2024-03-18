package com.example.itmoProject.models.dto.request;

import com.example.itmoProject.models.enums.Level;
import com.example.itmoProject.models.enums.ProjectStatus;
import com.example.itmoProject.models.enums.Topic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectInfoRequest {
    @NotEmpty(message = "Title of project must be set")
    String titleProject;
    Integer numberLesson;
    Level level;
    Boolean isApproved;
    Topic topic;
    ProjectStatus status;
}
