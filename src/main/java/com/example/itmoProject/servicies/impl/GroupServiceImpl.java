package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.exceptions.CustomException;
import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.entity.Group;
import com.example.itmoProject.models.db.repositories.GroupRepo;
import com.example.itmoProject.models.dto.request.GroupInfoRequest;
import com.example.itmoProject.models.dto.response.CourseInfoResponse;
import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.CourseService;
import com.example.itmoProject.servicies.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import utils.PaginationUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final ObjectMapper mapper;
    private final CourseService courseService;

    public static final String ERR_MSG = "Group not found";


    @Override
    public GroupInfoResponse createGroup(GroupInfoRequest request) {
        String titleGroup = request.getTitleGroup();

        if (titleGroup.isEmpty()) {
            throw new CustomException("Empty title of group", HttpStatus.BAD_REQUEST);
        }
        String studyDate = request.getStudyDate();
        if (studyDate.isEmpty()) {
            throw new CustomException("Empty date of study", HttpStatus.BAD_REQUEST);
        }

        Group group = mapper.convertValue(request, Group.class);
        group.setStatus(Status.CREATED);
        group.setCreatedAt(LocalDateTime.now());
        group = groupRepo.save(group);

        return mapper.convertValue(group, GroupInfoResponse.class);
    }

    @Override
    public GroupInfoResponse getGroup(Long id) {
        return mapper.convertValue(getGroupDb(id), GroupInfoResponse.class);
    }


    @Override
    public Group getGroupDb(Long id) {
        return groupRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }

    @Override
    public GroupInfoResponse updateGroup(Long id, GroupInfoRequest request) {
        Group group = getGroupDb(id);
        group.setTitleGroup(request.getTitleGroup() == null ? group.getTitleGroup() : request.getTitleGroup());
        group.setStudyDate(request.getStudyDate() == null ? group.getStudyDate() : request.getStudyDate());
        group.setUpdatedAt(LocalDateTime.now());
        group = groupRepo.save(group);

        return mapper.convertValue(group, GroupInfoResponse.class);
    }

    @Override
    public void deleteGroup(Long id) {
        Group group = getGroupDb(id);
        group.setStatus(Status.DELETED);
        group.setUpdatedAt(LocalDateTime.now());
        groupRepo.save(group);
    }

    @Override
    public List<GroupInfoResponse> getAllGroups() {
        return groupRepo.findAll()
                .stream()
                .map(group -> mapper.convertValue(group, GroupInfoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroupInfoResponse linkGroupCourse(Long courseId, Long groupId) {
        Group group = getGroupDb(groupId);
        Course course = courseService.getCourseDb(courseId);
        course.getGroups().add(group);
        courseService.updateGroupsList(course);

        group.setCourse(course);
        group = groupRepo.save(group);

        CourseInfoResponse courseInfoResponse = mapper.convertValue(course, CourseInfoResponse.class);
        GroupInfoResponse groupInfoResponse = mapper.convertValue(group, GroupInfoResponse.class);

        groupInfoResponse.setCourse(courseInfoResponse);
        return groupInfoResponse;
    }

    @Override
    public Page<GroupInfoResponse> getCourseGroups(Long courseId, Integer page, Integer perPage, String sort, Sort.Direction order) {
        courseService.getCourseDb(courseId);
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        Page<Group> allByCourseId = groupRepo.findAllByCourseId(request, courseId);

        return new PageImpl<>(allByCourseId.stream()
                .map(c -> mapper.convertValue(c, GroupInfoResponse.class))
                .collect(Collectors.toList()));
    }

}