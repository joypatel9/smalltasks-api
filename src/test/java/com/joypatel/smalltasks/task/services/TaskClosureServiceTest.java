package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.BusinessException;
import com.joypatel.smalltasks.common.MyError;
import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserCatalogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskClosureServiceTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserCatalogService userCatalogService;

    @Mock
    private MyUtils utils;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskClosureService service;

    @Test
    void closeTaskCreatedByCurrentUser_Should_CloseTask() {

        // given
        Integer creatorId = 1200;
        User creator = new User();
        creator.setId(creatorId);

        Integer taskId = 750;
        Task task = new Task();
        task.setId(taskId);
        task.setCreator(creator);

        User currentUser = new User();
        currentUser.setId(creatorId);

        when(taskService.getTaskById(taskId)).thenReturn(task);
        when(userCatalogService.getCurrentUser()).thenReturn(currentUser);

        // when
        service.closeTask(taskId);

        // then
        assertEquals(Task.Status.CLOSED, task.getStatus());
    }


    @Test
    void closeTaskNotCreatedByCurrentUser_Should_Throw_BusinessException() {

        // given
        String errorField = "task";
        String errorCode = "taskNotCreatedByCurrentUser";
        String errorMessage = "You are not the creator of this task";

        MyError expectedError = MyError.of(errorField, errorCode, errorMessage);
        when(utils.getError(errorField, errorCode)).thenReturn(expectedError);

        final Integer nonBelongingTaskId = 600;

        User creator = new User();
        creator.setId(1200);

        Task task = new Task();
        task.setId(nonBelongingTaskId);
        task.setCreator(creator);

        User currentUser = new User();
        currentUser.setId(2000);

        when(taskService.getTaskById(nonBelongingTaskId)).thenReturn(task);
        when(userCatalogService.getCurrentUser()).thenReturn(currentUser);

        try {
            // when
            service.closeTask(nonBelongingTaskId);
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
