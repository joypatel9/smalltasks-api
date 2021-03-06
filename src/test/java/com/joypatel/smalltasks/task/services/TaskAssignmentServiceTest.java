package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.BusinessException;
import com.joypatel.smalltasks.common.MyError;
import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TaskAssignmentServiceTest {

    private final Integer taskId = 130;
    private final Task task = new Task();
    private final User user = new User();

    @Mock
    private TaskService taskService;

    @Mock
    private UserCatalogService userCatalogService;

    @Mock
    private MyUtils utils;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    TaskAssignmentService service;

    @Test
    void assignOpenTask() {

        // given
        task.setStatus(Task.Status.OPEN);
        when(taskService.getTaskById(taskId)).thenReturn(task);
        when(userCatalogService.getCurrentUser()).thenReturn(user);

        // when
        service.assignTask(taskId);

        // then
        assertEquals(Task.Status.ASSIGNED, task.getStatus());
        assertEquals(user, task.getExecutor());
    }

    @Test
    void assignNonOpenTask_Should_ThrowBusinessException() {

        // given
        task.setStatus(Task.Status.ASSIGNED);

        when(taskService.getTaskById(taskId)).thenReturn(task);

        String errorField = "task";
        String errorCode = "taskNotOpen";
        String errorMessage = "Task is not open";

        MyError expectedError = MyError.of(errorField, errorCode, errorMessage);
        when(utils.getError(errorField, errorCode)).thenReturn(expectedError);

        try {

            // when
            service.assignTask(taskId);
            fail();

        } catch (BusinessException ex) {

            // then
            assertEquals(HttpStatus.BAD_REQUEST, ex.getResponseStatus());
            assertEquals(1, ex.getErrors().size());
            MyError actualError = ex.getErrors().iterator().next();
            assertEquals(expectedError, actualError);
        }
    }
}
