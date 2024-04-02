package com.example.itmoProject.servicies.impl;

import com.example.itmoProject.models.db.entity.Course;
import com.example.itmoProject.models.db.entity.Group;
import com.example.itmoProject.models.db.repositories.GroupRepo;
import com.example.itmoProject.models.dto.request.GroupInfoRequest;
import com.example.itmoProject.models.dto.response.GroupInfoResponse;
import com.example.itmoProject.models.enums.Status;
import com.example.itmoProject.servicies.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceImplTest {
    @InjectMocks
    private GroupServiceImpl groupService;

    @Mock
    private GroupRepo groupRepo;

    @Mock
    private CourseService courseService;

    @Spy
    private ObjectMapper mapper;

    @Test(expected = NullPointerException.class)
    public void createGroup() {
        GroupInfoRequest request = new GroupInfoRequest();
        request.setTitleGroup("testTitleGroup");

        Group group = new Group();
        group.setId(1L);

//        when(groupRepo.save(any(Group.class))).thenReturn(group);
        GroupInfoResponse result = groupService.createGroup(request);
        assertEquals(Long.valueOf(1L), result.getId());
    }

    @Test
    public void getGroup() {
    }

    @Test
    public void getGroupDb() {
    }

    @Test
    public void updateGroup() {
        GroupInfoRequest request = new GroupInfoRequest();
        request.setTitleGroup("TestTitleGroup");

        Group group = new Group();
        group.setId(1L);
        group.setTitleGroup("Test");

        when(groupRepo.findById(group.getId())).thenReturn(Optional.of(group));
        when(groupRepo.save(any(Group.class))).thenReturn(group);

        GroupInfoResponse result = groupService.updateGroup(group.getId(), request);
        assertEquals(group.getTitleGroup(), result.getTitleGroup());

    }

    @Test
    public void deleteGroup() {
        Group group = new Group();
        group.setId(1L);

        when(groupRepo.findById(group.getId())).thenReturn(Optional.of(group));
        groupService.deleteGroup(group.getId());
        verify(groupRepo, times(1)).save(any(Group.class));
        assertEquals(Status.DELETED, group.getStatus());
    }

    @Test
    public void getAllGroups() {
    }

    @Test
    public void linkGroupCourse() {
    }

    @Test(expected = NullPointerException.class)
    public void getCourseGroups() {
        Course course = new Course();
        course.setId(1L);

        Pageable pageable = mock(Pageable.class);

        when(courseService.getCourseDb(anyLong())).thenReturn(course);
        List<Group> groups = new ArrayList<>();
//        when(groupRepo.findAllByCourseId(pageable, course.getId())).thenReturn(new PageImpl<>(groups));

        List<Long> ids = groups.stream()
                .map(Group::getId)
                .collect(Collectors.toList());

        Page<GroupInfoResponse> courseGroups = groupService.getCourseGroups(1L, 1, 10, "brand", Sort.Direction.ASC);
        List<Long> respIds = courseGroups.getContent().stream()
                .map(GroupInfoResponse::getId)
                .collect(Collectors.toList());

        assertEquals(ids, respIds);
    }
}