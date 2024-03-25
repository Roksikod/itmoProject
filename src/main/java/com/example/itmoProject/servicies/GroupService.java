package com.example.itmoProject.servicies;

import com.example.itmoProject.models.db.entity.Group;
import com.example.itmoProject.models.dto.request.GroupInfoRequest;
import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GroupService {
    GroupInfoResponse createGroup(GroupInfoRequest request);

    GroupInfoResponse getGroup(Long id);

    Group getGroupDb(Long id);

    GroupInfoResponse updateGroup(Long id, GroupInfoRequest request);

    void deleteGroup(Long id);

    List<GroupInfoResponse> getAllGroups();

    GroupInfoResponse linkGroupCourse(Long courseId, Long groupId);

    Page<GroupInfoResponse> getCourseGroups(Long courseId, Integer page, Integer perPage, String sort, Sort.Direction order);
}
