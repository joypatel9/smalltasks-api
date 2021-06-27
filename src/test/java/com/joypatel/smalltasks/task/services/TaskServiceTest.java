package com.joypatel.smalltasks.task.services;

import com.joypatel.smalltasks.common.BusinessException;
import com.joypatel.smalltasks.common.MyError;
import com.joypatel.smalltasks.common.MyUtils;
import com.joypatel.smalltasks.task.dtos.TaskResponse;
import com.joypatel.smalltasks.task.entities.Task;
import com.joypatel.smalltasks.user.dtos.UserResponse;
import com.joypatel.smalltasks.user.entities.User;
import com.joypatel.smalltasks.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private final UserResponse userResponse = UserResponse.builder().build();

    @Mock
    private UserService userService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MyUtils utils;

    @InjectMocks
    private TaskService taskService;

    @Test
    void TaskService_toResponse() {

        when(userService.toResponse(any(User.class))).thenReturn(userResponse);

        // given
        User creator = new User();
        User executor = new User();

        Task task = new Task();
        task.setRef("some-ref");
        task.setSubject("This is the task's subject");
        task.setDescription("This is the task's description");
        task.setOriginPincode(723093);
        task.setCreator(creator);
        task.setExecutor(executor);
        task.setStatus(Task.Status.OPEN);

        // when
        TaskResponse taskResponse = taskService.toResponse(task);

        // then
        assertEquals(task.getRef(), taskResponse.getRef());
        assertEquals(task.getSubject(), taskResponse.getSubject());
        assertEquals(task.getDescription(), taskResponse.getDescription());
        assertEquals(task.getOriginPincode(), taskResponse.getOriginPincode());
        assertEquals(userService.toResponse(task.getCreator()), taskResponse.getCreator());
        assertEquals(userService.toResponse(task.getExecutor()), taskResponse.getExecutor());
        assertEquals(task.getStatus(), taskResponse.getStatus());
    }

    @Test
    void TaskService_toResponseList() {

        // given
        List<Task> tasks = List.of(new Task());

        // when
        List<TaskResponse> taskResponses = taskService.toResponseList(tasks);

        // then
        assertEquals(tasks.size(), taskResponses.size());

    }

    @Test
    void TaskService_getTaskById_Should_return_task() {

        // given
        Integer taskId = 50;
        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // when
        Task actualTask = taskService.getTaskById(taskId);

        // then
        assertEquals(task, actualTask);
    }

    @Test
    void TaskService_getTaskById_NonExistingTask_Should_throw_BusinessException() {

        // given
        Integer nonExistingTaskId = 90;
        when(taskRepository.findById(nonExistingTaskId)).thenReturn(Optional.empty());

        MyError expectedError = MyError.of("task", "notFound", "not found");
        when(utils.getError("task", "notFound")).thenReturn(expectedError);

        try {

            // when
            taskService.getTaskById(nonExistingTaskId);
            fail();

        } catch (BusinessException ex) {

            // then
            assertEquals(HttpStatus.NOT_FOUND, ex.getResponseStatus());
            assertEquals(1, ex.getErrors().size());
            MyError actualError = ex.getErrors().iterator().next();
            assertEquals(expectedError, actualError);
        }
    }

}
