package com.example.itmoProject.controllers;

import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import com.example.itmoProject.models.dto.response.ProjectInfoResponse;
import com.example.itmoProject.models.dto.response.StudentInfoResponse;
import com.example.itmoProject.servicies.GroupService;
import com.example.itmoProject.servicies.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

        private final GroupService groupService;

        @PostMapping
        @Operation(summary = "Создание группы")
        public GroupInfoResponse createGroup(@RequestBody @Valid GroupInfoResponse request) {
            return groupService.createGroup(request);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Получение группы")
        public GroupInfoResponse getGroup(@PathVariable Long id) {
            return groupService.getGroup(id);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Редактирование группы")
        public GroupInfoResponse updateGroup(@PathVariable Long id, @RequestBody @Valid GroupInfoResponse request) {
            return groupService.updateGroup(id, request);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Удаление группы")
        public void deleteGroup(@PathVariable Long id) {
            groupService.deleteGroup(id);
        }

        @GetMapping("/all")
        @Operation(summary = "Получение всех групп")
        public List<GroupInfoResponse> getAllGroups() {
            return groupService.getAllGroups();
        }

        @PostMapping("/linkGroupCourse/{groupId}/{courseId}")
        @Operation(summary = "Назначение группе курс")
        public GroupInfoResponse linkGroupCourse(@PathVariable Long groupId, @PathVariable Long courseId) {
                return groupService.linkGroupCourse(courseId, groupId);
        }
}
