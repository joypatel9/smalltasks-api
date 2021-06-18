package com.joypatel.smalltasks.task.services;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private final UserResponse userResponse = UserResponse.builder().build();

    @Mock
    private UserService userService;

    @Mock
    private TaskRepository taskRepository;

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
    void should_findOpenTasks_When_Next() {

        // given
        User user = new User();
        user.setId(124);

        // when
        List<Task> tasks = taskService.findOpenTasks(721023, user, 5, true, 5);

        // then
        verify(taskRepository).findNextTasks(721023, user.getId(), 5, Task.Status.OPEN.name(), 5);
    }

    @Test
    void should_findOpenTasks_When_Previous() {

        // given
        User user = new User();
        user.setId(124);

        // when
        List<Task> tasks = taskService.findOpenTasks(721023, user, 5, false, 5);

        // then
        verify(taskRepository).findPreviousTasks(721023, user.getId(), 5, Task.Status.OPEN.name(), 5);
    }

}
