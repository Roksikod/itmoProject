package com.example.itmoProject.models.dto.request;

import com.example.itmoProject.models.enums.*;
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
public class LessonInfoRequest {
    @NotEmpty(message = "Title of lesson must be set")
    String titleLesson;
    @NotEmpty(message = "Number of lesson must be set")
    Integer numberLesson;
    Level level;
    Boolean isOpened;
    Topic topic;
    LessonStatus lessonStatus;
    Status status;
}
