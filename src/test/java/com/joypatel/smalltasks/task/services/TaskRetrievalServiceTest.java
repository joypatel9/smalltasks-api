package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskRetrievalServiceTest {

    final int beyondId = 1;
    final boolean next = false;
    final int itemCount = 2;
    final Optional<Integer> optionalPincode = Optional.empty();
    final User user = new User();

    private final List<TaskResponse> expectedResponses = List.of(TaskResponse.builder().build());
    private final List<Task> tasks = List.of(new Task());

    @Mock
    private TaskService taskService;

    @Mock
    private UserCatalogService userCatalogService;

    @InjectMocks
    private TaskRetrievalService service;

    @BeforeEach
    void setUp() {

        user.setPincode(751024);

        when(userCatalogService.getCurrentUser()).thenReturn(user);
        when(taskService.toResponseList(tasks)).thenReturn(expectedResponses);
    }

    @Test
    void get_tasks_without_pincode() {

        // given
        when(taskService.findOpenTasks(user.getPincode(), user, beyondId, next, itemCount)).thenReturn(tasks);

        // when
        List<TaskResponse> actualResponses = service.getTasks(optionalPincode, Optional.empty(), Optional.empty(), Optional.empty(), beyondId, next, itemCount);

        // then
        assertEquals(expectedResponses, actualResponses);
    }

    @Test
    void get_tasks_with_pincode() {

        // given
        Integer taskOriginPincode = 751023;
        when(taskService.findOpenTasks(taskOriginPincode, user, beyondId, next, itemCount)).thenReturn(tasks);

        // when
        List<TaskResponse> actualResponses = service.getTasks(Optional.of(taskOriginPincode), Optional.empty(), Optional.empty(), Optional.empty(), beyondId, next, itemCount);

        // then
        assertEquals(expectedResponses, actualResponses);
    }
}
